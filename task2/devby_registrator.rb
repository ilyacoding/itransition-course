require 'rubygems'
require 'capybara'
require 'capybara/dsl'
require 'json'
require 'net/http'
require 'uri'
require 'securerandom'

class DevbyRegistrator
  include Capybara::DSL

  def initialize
    Capybara.current_driver = :selenium
  end

  def create_email
    uri = URI.parse("https://post-shift.ru/api.php?action=new&type=json")
    response = Net::HTTP.get_response(uri)
    JSON.parse(response.body)
  end

  def delete_email(key)
    uri = URI.parse("https://post-shift.ru/api.php?action=delete&key=#{key}")
    response = Net::HTTP.get_response(uri)
    response.body == 'OK'
  end

  def wait_email(key)
    loop do
      uri = URI.parse("https://post-shift.ru/api.php?action=getlist&key=#{key}&type=json")
      response = Net::HTTP.get_response(uri)
      list = JSON.parse(response.body)
      break if list.is_a?(Array)
    end
  end

  def confirm(key)
    uri = URI.parse("https://post-shift.ru/api.php?action=getmail&key=#{key}&id=1")
    response = Net::HTTP.get_response(uri)
    link = get_link(response.body.to_s)
    visit(link.to_s)
  end

  def get_link(str)
    str.match(/< ?([^>]+) ?>\Z/)[1].sub('=3D', '=')
  end

  def register
    loop do
      login = SecureRandom.hex(8)
      password = SecureRandom.hex(8)
      visit('http://dev.by/registration')
      email_credentials = create_email()
      fill_in('user[username]', with: login)
      fill_in('user[email]', with: email_credentials['email'])
      fill_in('user[password]', with: password)
      fill_in('user[password_confirmation]', with: password)
      check('user_agreement')
      find('.blue.btn.submit').click
      break if page.html.include? 'icon-dev-a-success'
      delete_email(email_credentials['key'])
    end

    wait_email(email_credentials['key'])
    confirm(email_credentials['key'])
    delete_email(email_credentials['key'])
    { login: login, password: password }
  end
end
