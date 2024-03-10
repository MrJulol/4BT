#!/usr/bin/env python3
# NOTE: it is recommended to use this even if you don't understand the following code.

import sys

# uncomment the two following lines if you want to read/write from files
sys.stdin = open('input.txt')
#sys.stdout = open('output.txt', 'w')

T = int(input().strip())
for test in range(1, T+1):
    N = input().strip()
    
    ans = 0

    if int(N) < 25:
        ans = -1
        print("<25")
    elif int(N)%25==0:
        ans = int(int(N)/25)
        print("%25")
    else:
        print("INTHERE")
        tests = []
        for i in N:
            if int(i) == 5:
                tests.append(i)
            elif int(i) == 0:
                tests.append(i)
            elif int(i) == 2:
                tests.append(i)
            elif int(i) == 7:
                tests.append(i)
        print(tests)
            


        

    
    
    # INSERT YOUR CODE HERE
    
    
    # print(ans)

sys.stdout.close()
