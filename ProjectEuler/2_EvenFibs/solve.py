#!/bin/python

import sys

even_fibs = [0 , 2, 8, 34, 144, 610, 2584, 10946, 46368, 196418, 832040, 3524578, 14930352, 63245986, 267914296, 1134903170, 4807526976, 20365011074, 86267571272, 365435296162, 1548008755920, 6557470319842, 27777890035288, 117669030460994, 498454011879264, 2111485077978050, 8944394323791464, 37889062373143906, 160500643816367088]

t = int(raw_input().strip())
for a0 in xrange(t):
    n = long(raw_input().strip())
    
    last_index = 0
    for i, e in enumerate(even_fibs):
        if e > n:
            last_index = i
            break
    print sum(even_fibs[:last_index])