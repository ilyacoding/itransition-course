require_relative "devby_registrator.rb"

if ARGV.length.zero?
  puts "Too few arguments"
  exit
end

object = DevbyRegistrator.new

ARGV[0].to_i.times do |i|
  user = object.register
  puts "#{i + 1}) #{user[:login]} => #{user[:password]}"
end