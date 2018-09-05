import math

BASE = 10

def dig_pow(n, p):
    return dig_pow_math(n, p)


def dig_pow_math(n, p):
    N, m, k = 0, n, int(math.log(n, BASE))
    while m > 0:
        m, a = divmod(m, BASE)
        N += a ** (p + k)
        k -= 1
    return N // n if N % n == 0 else -1


def dig_pow_nomath(n, p):
    if p < 0:
        return -1
    N, m, digits = 0, n, []
    while m > 0:
        m, a = divmod(m, BASE)
        digits.insert(0, a)
    for i, d in enumerate(digits):
        N += d ** (p + i)
    return N // n if N % n == 0 else -1

