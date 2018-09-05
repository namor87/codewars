import random
import time
import math

BASE = 10

def dig_sum_math(n):
    N, k = 0, int(math.log(n, BASE))
    while n > 0:
        n, a = divmod(n, BASE)
        N += a
        k -= 1
    return N

def dig_sum_array(n):
    N, digits = 0, []
    while n > 0:
        n, a = divmod(n, BASE)
        digits.append(a)
    size = len(digits)
    for j in range(1, size + 1):
        N += digits[size - j]
    return N

def dig_sum_str(n):
    s = 0
    for j, c in enumerate(str(n)):
        s += int(c)
    return s


def time_it(f, x):
    tstart = time.time()
    f(x)
    return time.time() - tstart

time1, time2, time3 = 0, 0, 0
for i in range(1000000):
    testN = random.randint(1, 1000000)
    time1 += time_it(dig_sum_math, testN)
    time2 += time_it(dig_sum_array, testN)
    time3 += time_it(dig_sum_str, testN)

print time1
print time2
print time3
