f = open("results.txt", 'r', encoding="UTF-8")
w = open("newlist.txt", 'w', encoding="UTF-8")
x = ""

# random number

count=30

# 2773 possible solutions in total

for i in range (2773):
    y = ""
    f.readline()
    for k in range (5):

         # 5 words in each solution
         
         y+=f.readline().strip('\n')
    county=0;
    for letter in y:
        if (letter == 'נ' or letter == 'צ' or letter == 'כ' or letter == 'מ' or letter == 'פ'):

            #amount of letters that appear more than once

            county += 1
    if (county<=count):

        # replace x with the best solution possible

        x=y
        count=county
    f.readline()
w.write(" השילוב המנצח הוא: " + '\n' + x[0:5] + '\n' + x[5:10] + '\n' + x[10:15] + '\n' + x[15:20] + '\n' + x[20:25])
f.close()
