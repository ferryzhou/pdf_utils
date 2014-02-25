out_dir = File.join(out_root, exhibit_ind); mkdir_if_not_exist(out_dir)
letter_p1_dir = File.join(out_dir, 'letter_p1'); mkdir_if_not_exist(letter_p1_dir)
stamp_dir = File.join(out_dir, 'stamp_dir'); mkdir_if_not_exist(stamp_dir)

def changeSizeToLetter(from, to)
        str = "\"C:/Program Files (x86)/Java/jdk1.6.0_20/bin/java\" -cp \"iText-5.0.5.jar;.\" ChangeSizeToLetter "
        str = str + "\"#{from}\" \"#{to}\" "
    puts str
    system(str) 
end

def addStamp(from, to, index)
    str = "\"C:/Program Files (x86)/Java/jdk1.6.0_20/bin/java\" -cp \"iText-5.0.5.jar;.\" StampText "
        str = str + "\"#{from}\" \"#{to}\" "
        str = str + "\"#{index}\" #{$exhibit_len} #{$left_shift}"
    puts str
    system(str) 
end

def concatenate(dir, out)
        str = "pdftk.exe \"#{File.join(dir, '*.pdf')}\" cat output \"#{out}\""
    puts str
    system(str)         
end

files = Dir.glob(File.join(src_dir, "*.pdf"))
i = 1
files.each { |file| 
        basename = File.basename(file)
        letter_p1_file = File.join(letter_p1_dir, basename)
        changeSizeToLetter(file, letter_p1_file)
        ind = exhibit_ind
        if ind == 'Appendix'; ind = ind + " #{i}"
        else ind = ind + ".#{i}" if files.size > 1; end
        addStamp(letter_p1_file, File.join(stamp_dir, basename), ind)
        i = i + 1
}
concatenate(stamp_dir, File.join(out_root, exhibit_ind + '.pdf'))
