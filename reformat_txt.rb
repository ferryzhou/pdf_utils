
File.open('test_out.htm', 'w') do |f2|  
  f2.puts '<html><meta http-equiv="Content-Type" content="text/html; charset=utf-8"/><body><p>'
File.open('test1.txt', 'r') do |f1|  
  while line = f1.gets  
    if not line.index('Powered').nil?; next; end
    if not line.index('Page').nil?; next; end
    if line.length > 4 && line[0...4] == '    '
	  f2.puts '</p><p>' + line
	  p 'added </p><p> at ' + line
	else
      f2.puts line  
	end
  end  
end  
  f2.puts '</p></body></html>'
end  
