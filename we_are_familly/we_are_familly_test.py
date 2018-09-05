import unittest
from we_are_family import family


class TestChecks(unittest.TestCase):

    def test_it00(self):
        fam = family()
        for method in ["male", "is_male", "female", "is_female", "set_parent_of", "get_parents_of", "get_children_of"]:
            self.assertEquals(hasattr(fam, method) and callable(getattr(fam, method)), True,
                              "The family class does not define the {} method".format(method))

    def test_it01(self):
        msg = "Should return True for a valid parent relationship"
        fam = family()
        self.assertEquals(fam.set_parent_of("Susan", "Otilia"), True, msg)

    def test_it02(self):
        msg = "Should return True for a valid father relationship"
        fam = family()
        self.assertEquals(fam.set_parent_of("Morgan", "Frank")
                          and fam.male("Frank"), True, msg)

    def test_it03(self):
        msg = "Should return True for a valid mother relationship"
        fam = family()
        self.assertEquals(fam.set_parent_of("Megan", "Helen")
                          and fam.female("Helen"), True, msg)

    def test_it04(self):
        msg = "Should not accept that child and parent have the same name"
        fam = family()
        self.assertEquals(fam.set_parent_of("John", "John"), False, msg)

    def test_it05(self):
        msg = "Should echo back with get_parents_of what was affirmed before"
        fam = family()
        fam.set_parent_of("Megan", "Helen")
        self.assertEquals(fam.get_parents_of("Megan"), ["Helen"], msg)

    def test_it06(self):
        msg = "Should echo back with get_children_of what was affirmed before"
        fam = family()
        fam.set_parent_of("Morgan", "Frank")
        self.assertEquals(fam.get_children_of("Frank"), ["Morgan"], msg)

    def test_it07(self):
        msg = "Should not include a relationship that was rejected before"
        fam = family()
        fam.set_parent_of("John", "John")
        self.assertEquals(fam.get_parents_of("John"), [], msg)

    def test_it08(self):
        msg = "Should allow to add a second parent"
        fam = family()
        self.assertEquals(fam.set_parent_of("Susan", "Otilia")
                          and fam.set_parent_of("Susan", "Jeremy"), True, msg)

    def test_it09(self):
        msg = "Should list parents in alphabetical order"
        fam = family()
        fam.set_parent_of("Susan", "Otilia")
        fam.set_parent_of("Susan", "Jeremy")
        self.assertEquals(fam.get_parents_of("Susan"), ["Jeremy", "Otilia"], msg)

    def test_it10(self):
        msg = "Should allow to add a second and third child"
        fam = family()
        self.assertEquals(fam.set_parent_of("Susan", "Otilia")
                          and fam.set_parent_of("Penelope", "Jeremy")
                          and fam.set_parent_of("Hank", "Jeremy"), True, msg)

    def test_it11(self):
        msg = "Should list children in alphabetical order"
        fam = family()
        fam.set_parent_of("Susan", "Jeremy")
        fam.set_parent_of("Penelope", "Jeremy")
        fam.set_parent_of("Hank", "Jeremy")
        self.assertEquals(fam.get_children_of("Jeremy"), ["Hank", "Penelope", "Susan"], msg)

    def test_it12(self):
        msg = "Should not complain when the same info is provided twice, but not store it twice either"
        fam = family()
        self.assertEquals(fam.set_parent_of("Megan", "Helen")
                          and fam.set_parent_of("Megan", "Helen"), True, msg)
        self.assertEquals(fam.get_parents_of("Megan"), ["Helen"], msg)

    def test_it13(self):
        msg = "Should reject a gender assignment that does not match with previous information"
        fam = family()
        self.assertEquals(fam.male("Frank"), True, msg)
        self.assertEquals(fam.female("Frank"), False, msg)

    def test_it14(self):
        msg = "Should support grandparents"
        fam = family()
        self.assertEquals(fam.set_parent_of("Susan", "Otilia")
                          and fam.set_parent_of("Susan", "Jeremy")
                          and fam.set_parent_of("Otilia", "Peter"), True, msg)
        self.assertEquals(fam.get_parents_of("Susan"), ["Jeremy", "Otilia"], msg)
        self.assertEquals(fam.get_parents_of("Otilia"), ["Peter"], msg)

    def test_it15(self):
        msg = "Should keep two family instances separate"
        fam = family()
        fam2 = family()
        self.assertEquals(fam.set_parent_of("Otilia", "Peter"), True, msg)
        self.assertEquals(fam2.set_parent_of("Otilia", "Jeff"), True, msg)
        self.assertEquals(fam2.get_parents_of("Otilia"), ["Jeff"], msg)
        self.assertEquals(fam.get_parents_of("Otilia"), ["Peter"], msg)


class TestDescribe(unittest.TestCase):
    def test_it21(self):
        msg = "Should confirm the introduction of the family members"
        fam = family()
        self.assertEquals(fam.set_parent_of("Frank", "Morgan")
                          and fam.set_parent_of("Frank", "Dylan")
                          and fam.male("Dylan")
                          and fam.set_parent_of("Joy", "Frank")
                          and fam.male("Frank"), True, msg)

    def test_it22(self):
        msg = "Should deduce that Morgan is a woman"
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        self.assertEquals(fam.male("Morgan"), False, msg)

    def test_it23(self):
        msg = "Should be able to continue after a rejected assertion"
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        fam.male("Morgan")
        self.assertEquals(fam.set_parent_of("July", "Morgan"), True, msg)

    def test_it24(self):
        msg = "Should not know Joy's gender"
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        fam.set_parent_of("Joy", "Frank")
        fam.male("Frank")
        self.assertEquals(fam.is_male("Joy") or fam.is_female("Joy"), False, msg)

    def test_it25(self):
        msg = "Should know Morgan's children"
        fam = family()
        self.assertEquals(fam.set_parent_of("Frank", "Morgan")
                          and fam.set_parent_of("July", "Morgan"), True, msg)
        self.assertEquals(fam.get_children_of("Morgan"), ["Frank", "July"], msg)

    def test_it26(self):
        msg = "Should know Morgan's children after a new child was declared"
        fam = family()
        self.assertEquals(fam.set_parent_of("Frank", "Morgan")
                          and fam.set_parent_of("July", "Morgan")
                          and fam.set_parent_of("Jennifer", "Morgan"), True, msg)
        self.assertEquals(fam.get_children_of("Morgan"), ["Frank", "Jennifer", "July"], msg)

    def test_it27(self):
        msg = "Should only consider Frank as Dylan's child"
        fam = family()
        self.assertEquals(fam.set_parent_of("Frank", "Dylan")
                          and fam.set_parent_of("Frank", "Morgan")
                          and fam.set_parent_of("July", "Morgan")
                          and fam.set_parent_of("Jennifer", "Morgan"), True, msg)
        self.assertEquals(fam.get_children_of("Dylan"), ["Frank"], msg)

    def test_it28(self):
        msg = "Should confirm the obvious"
        fam = family()
        self.assertEquals(fam.set_parent_of("Frank", "Dylan")
                          and fam.set_parent_of("Frank", "Morgan"), True, msg)
        self.assertEquals(fam.get_parents_of("Frank"), ["Dylan", "Morgan"], msg)

    def test_it29(self):
        msg = "Should know someone's parent cannot be their child"
        fam = family()
        self.assertEquals(fam.set_parent_of("Frank", "Morgan"), True, msg)
        self.assertEquals(fam.set_parent_of("Morgan", "Frank"), False, msg)

    def test_it30(self):
        msg = "Should deduce that Morgan is a woman"
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        self.assertEquals(fam.is_female("Morgan"), True, msg)

class TestExtra(unittest.TestCase):

    def test_40(self):
        fam = family()
        fam.set_parent_of("B", "A")
        fam.set_parent_of("B", "C")
        fam.set_parent_of("D", "E")
        fam.set_parent_of("F", "E")
        fam.set_parent_of("H", "G")
        fam.set_parent_of("H", "I")
        fam.set_parent_of("F", "G")
        fam.set_parent_of("D", "C")
        fam.male("E")
        self.assertEquals(fam.is_male("A"), True, "DUPA")

    def test_41(self):
        fam = family()
        fam.set_parent_of("B", "A")
        fam.set_parent_of("B", "C")
        fam.set_parent_of("D", "E")
        fam.set_parent_of("F", "E")
        fam.set_parent_of("H", "G")
        fam.set_parent_of("H", "A")
        fam.set_parent_of("F", "G")
        fam.set_parent_of("D", "C")
        fam.male("E")
        self.assertEquals(fam.is_male("A"), True, "DUPA")

    def test_42(self):
        fam = family()
        fam.set_parent_of("B","A")
        fam.set_parent_of("B","C")
        fam.set_parent_of("D","E")
        fam.set_parent_of("F","E")
        fam.set_parent_of("H","G")
        fam.set_parent_of("H","I")
        fam.set_parent_of("F","G")
        fam.set_parent_of("D","C")
        fam.male("E")
        fam.set_parent_of("K","L")
        fam.set_parent_of("K","I")
        fam.is_male("I")
        fam.set_parent_of("M","L")
        fam.set_parent_of("M","N")
        fam.is_male("N")

    def test_43(self):
        fam = family()
        fam.set_parent_of("B","A")
        fam.set_parent_of("B","C")
        fam.set_parent_of("D","C")
        fam.set_parent_of("D","E")
        fam.set_parent_of("F","E")
        fam.set_parent_of("F","A")

if __name__ == '__main__':
    unittest.main()

