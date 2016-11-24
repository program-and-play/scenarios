require 'rake/packagetask'

task :default => :zip

task :zip do
  Rake::PackageTask.new("files", "0.0.1") do |p|
    p.need_zip = true
    p.package_files.include("**/*.java")
  end
end