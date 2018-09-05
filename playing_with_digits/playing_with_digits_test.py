import unittest
from playing_with_digits import dig_pow


class TestSums(unittest.TestCase):
    def test01(self):
        self.assertEquals(dig_pow(89, 1), 1)

    def test02(self):
        self.assertEquals(dig_pow(92, 1), -1)

    def test03(self):
        self.assertEquals(dig_pow(46288, 3), 51)
