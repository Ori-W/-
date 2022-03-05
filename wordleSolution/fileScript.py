try:
    f = open("wordList.txt", 'r', encoding="UTF-8")
    w = open("Source.txt", 'w', encoding="UTF-8")

    # 100,000 diiferent words

    for i in range (100000):

        #deletes commas and quotation marks. puts each word in a different line
        
        x = f.read(7)[1:6]
        w.write(x+"\n")
        f.read(2)
finally:
    f.close()
