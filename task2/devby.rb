require_relative 'devby_registrator.rb'

if ARGV.length.zero?
  puts 'Too few arguments'
  exit
end
puts 'Login Password'
object = DevbyRegistrator.new

ARGV[0].to_i.times do
  user = object.register
  puts "#{user[:login]} #{user[:password]}"
end
