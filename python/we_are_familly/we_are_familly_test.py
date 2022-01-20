import unittest
from we_are_family import family


class TestBasic(unittest.TestCase):

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
        self.assertTrue(fam.set_parent_of("B", "A"))
        self.assertTrue(fam.set_parent_of("B", "C"))
        self.assertTrue(fam.set_parent_of("D", "E"))
        self.assertTrue(fam.set_parent_of("F", "E"))
        self.assertTrue(fam.set_parent_of("H", "G"))
        self.assertTrue(fam.set_parent_of("H", "I"))
        self.assertTrue(fam.set_parent_of("F", "G"))
        self.assertTrue(fam.set_parent_of("D", "C"))
        self.assertTrue(fam.male("E"))
        self.assertEquals(fam.is_male("A"), True, "DUPA")

    def test_41(self):
        fam = family()
        self.assertTrue(fam.set_parent_of("B", "A"))
        self.assertTrue(fam.set_parent_of("B", "C"))
        self.assertTrue(fam.set_parent_of("D", "E"))
        self.assertTrue(fam.set_parent_of("F", "E"))
        self.assertTrue(fam.set_parent_of("H", "G"))
        self.assertTrue(fam.set_parent_of("H", "A"))
        self.assertTrue(fam.set_parent_of("F", "G"))
        self.assertTrue(fam.set_parent_of("D", "C"))
        self.assertTrue(fam.male("E"))
        self.assertTrue(fam.is_male("A"))

    def test_42(self):
        fam = family()
        self.assertTrue(fam.set_parent_of("B", "A"))
        self.assertTrue(fam.set_parent_of("B", "C"))
        self.assertTrue(fam.set_parent_of("D", "E"))
        self.assertTrue(fam.set_parent_of("F", "E"))
        self.assertTrue(fam.set_parent_of("H", "G"))
        self.assertTrue(fam.set_parent_of("H", "I"))
        self.assertTrue(fam.set_parent_of("F", "G"))
        self.assertTrue(fam.set_parent_of("D", "C"))
        self.assertTrue(fam.male("E"))
        self.assertTrue(fam.set_parent_of("K", "L"))
        self.assertTrue(fam.set_parent_of("K", "I"))
        self.assertTrue(fam.is_male("I"))
        self.assertTrue(fam.is_female("L"))
        self.assertTrue(fam.set_parent_of("M", "L"))
        self.assertTrue(fam.set_parent_of("M", "N"))
        self.assertTrue(fam.is_male("N"))

    def test_43_impossible_tree_structure(self):
        fam = family()
        self.assertTrue(fam.set_parent_of("B", "A"))
        self.assertTrue(fam.set_parent_of("B", "C"))
        self.assertTrue(fam.set_parent_of("D", "C"))
        self.assertTrue(fam.set_parent_of("D", "E"))
        self.assertTrue(fam.set_parent_of("F", "E"))
        self.assertFalse(fam.set_parent_of("F", "A"))

    def test_44_impossible_tree_structure(self):
        fam = family()
        self.assertTrue(fam.set_parent_of("B", "A"))
        self.assertTrue(fam.set_parent_of("B", "C"))
        self.assertTrue(fam.set_parent_of("D", "C"))
        self.assertTrue(fam.set_parent_of("D", "E"))
        self.assertTrue(fam.set_parent_of("F", "E"))
        self.assertTrue(fam.set_parent_of("F", "G"))
        self.assertTrue(fam.set_parent_of("H", "G"))
        self.assertTrue(fam.set_parent_of("H", "I"))
        self.assertTrue(fam.set_parent_of("J", "I"))
        self.assertFalse(fam.set_parent_of("J", "A"))

    def test_44_so_much_incest_it_is_impossible(self):
        fam = family()
        self.assertTrue(fam.set_parent_of("B", "A"))
        self.assertTrue(fam.set_parent_of("C", "A"))
        self.assertTrue(fam.set_parent_of("C", "B"))
        self.assertTrue(fam.set_parent_of("D", "A"))
        self.assertTrue(fam.set_parent_of("D", "C"))
        self.assertTrue(fam.set_parent_of("E", "B"))
        self.assertFalse(fam.set_parent_of("E", "C"))

    def test_45_no_homo(self):
        fam = family()
        self.assertTrue(fam.male("A"))
        self.assertTrue(fam.male("B"))
        self.assertTrue(fam.set_parent_of("C", "A"))
        self.assertFalse(fam.set_parent_of("C", "B"))

    def test_46_infer_gender_from_spouse(self):
        fam = family()
        self.assertTrue(fam.male("A"))
        self.assertTrue(fam.set_parent_of("C", "A"))
        self.assertTrue(fam.set_parent_of("C", "B"))
        self.assertTrue(fam.is_female("B"))



# class TestRandomCases(unittest.TestCase):
#
#     def test_90(self):
#         fam = family()
#         self.assertTrue(fam.set_parent_of("Bernard", "Danny"))
#         self.assertTrue(fam.male("Danny"))
#         self.assertTrue(fam.set_parent_of("Andrea", "Danny"))
#         self.assertTrue(fam.set_parent_of("Eve", "Frank"))
#         self.assertTrue(fam.set_parent_of("Danny", "Frank"))
#         self.assertTrue(fam.male("Andrea"))
#         self.assertTrue(fam.male("Christine"))
#         self.assertTrue(fam.female("Christine"))
#         self.assertTrue(fam.set_parent_of("Bernard", "Christine"))
#         self.assertTrue(fam.set_parent_of("Christine", "Eve"))
#         self.assertTrue(fam.male("Danny"))
#         self.assertTrue(fam.male("Eve"))
#         self.assertTrue(fam.set_parent_of("Danny", "Frank"))
#         self.assertTrue(fam.set_parent_of("Danny", "Eve"))
#         self.assertTrue(fam.male("Danny"))
#         self.assertTrue(fam.is_female("Frank"))
#
#     def test_91(self):
#         fam = family()
#         self.assertTrue(fam.set_parent_of("Helen", "Andrea"))
#         self.assertTrue(fam.male("Kim"))
#         self.assertTrue(fam.female("Irvin"))
#         self.assertTrue(fam.male("Kim"))
#         self.assertTrue(fam.set_parent_of("Eve", "Helen"))
#         self.assertTrue(fam.female("Lee"))
#         self.assertTrue(fam.female("Frank"))
#         ## This is F'd up
#         self.assertTrue(fam.set_parent_of("Bernard", "Bernard"))
#         self.assertTrue(fam.female("Christine"))
#         self.assertTrue(fam.female("Georgia"))
#         self.assertTrue(fam.set_parent_of("Christine", "Kim"))
#         self.assertTrue(fam.female("Andrea"))
#         self.assertTrue(fam.set_parent_of("Eve", "Irvin"))
#         self.assertTrue(fam.male("Kim"))
#         self.assertTrue(fam.male("Andrea"))
#         self.assertTrue(fam.female("Christine"))
#         self.assertTrue(fam.male("Lee"))
#         self.assertTrue(fam.female("Kim"))
#         self.assertTrue(fam.set_parent_of("Bernard", "Helen"))
#         self.assertTrue(fam.female("Frank"))
#         self.assertTrue(fam.set_parent_of("Christine", "Eve"))
#         self.assertTrue(fam.set_parent_of("Lee", "Frank"))
#         self.assertTrue(fam.set_parent_of("Eve", "Helen"))
#         self.assertTrue(fam.set_parent_of("Irvin", "Andrea"))
#         self.assertTrue(fam.get_parents_of("Irvin"))
#         self.assertTrue(fam.is_male("Kim"))
#         self.assertTrue(fam.set_parent_of("Kim", "Bernard"))
#         self.assertTrue(fam.is_male("Frank"))
#         self.assertTrue(fam.is_female("Jack"))
#         self.assertTrue(fam.is_female("Eve"))
#
#     def test_92(self):
#         fam = family()
#         self.assertTrue(fam.set_parent_of("Patrick", "Patrick"))
#         self.assertTrue(fam.female("Quinn"))
#         self.assertTrue(fam.male("Helen"))
#         self.assertTrue(fam.set_parent_of("Eve", "Georgia"))
#         self.assertTrue(fam.female("Christine"))
#         self.assertTrue(fam.male("Ursula"))
#         self.assertTrue(fam.set_parent_of("Nora", "Quinn"))
#         self.assertTrue(fam.set_parent_of("Frank", "Irvin"))
#         self.assertTrue(fam.set_parent_of("Zora", "Ron"))
#         self.assertTrue(fam.set_parent_of("Lee", "Georgia"))
#         self.assertTrue(fam.male("Andrea"))
#         self.assertTrue(fam.male("Helen"))
#         self.assertTrue(fam.female("Nora"))
#         self.assertTrue(fam.set_parent_of("Ophra", "Helen"))
#         self.assertTrue(fam.female("Irvin"))
#         self.assertTrue(fam.male("Zora"))
#         self.assertTrue(fam.male("Eve"))
#         self.assertTrue(fam.female("Helen"))
#         self.assertTrue(fam.male("Zora"))
#         self.assertTrue(fam.set_parent_of("Irvin", "Patrick"))
#         self.assertTrue(fam.female("Tanya"))
#         self.assertTrue(fam.male("Andrea"))
#         self.assertTrue(fam.female("Ron"))
#         self.assertTrue(fam.set_parent_of("Ursula", "Ophra"))
#         self.assertTrue(fam.set_parent_of("Irvin", "Ophra"))
#         self.assertTrue(fam.female("Vincent"))
#         self.assertTrue(fam.male("Nora"))
#         self.assertTrue(fam.set_parent_of("Zora", "Kim"))
#         self.assertTrue(fam.female("Xander"))
#         self.assertTrue(fam.male("Walter"))
#         self.assertTrue(fam.male("Georgia"))
#         self.assertTrue(fam.male("Danny"))
#         self.assertTrue(fam.male("Christine"))
#         self.assertTrue(fam.female("Quinn"))
#         self.assertTrue(fam.female("Yvan"))
#         self.assertTrue(fam.female("Suzy"))
#         self.assertTrue(fam.male("Kim"))
#         self.assertTrue(fam.set_parent_of("Christine", "Maria"))
#         self.assertTrue(fam.set_parent_of("Kim", "Bernard"))
#         self.assertTrue(fam.male("Bernard"))
#         self.assertTrue(fam.male("Walter"))
#         self.assertTrue(fam.male("Walter"))
#         self.assertTrue(fam.male("Zora"))
#         self.assertTrue(fam.male("Tanya"))
#         self.assertTrue(fam.male("Kim"))
#         self.assertTrue(fam.female("Xander"))
#         self.assertTrue(fam.male("Lee"))
#         self.assertTrue(fam.male("Ursula"))
#         self.assertTrue(fam.female("Georgia"))
#         self.assertTrue(fam.male("Nora"))
#         self.assertTrue(fam.female("Nora"))
#         self.assertTrue(fam.set_parent_of("Christine", "Quinn"))
#         self.assertTrue(fam.male("Ron"))
#         self.assertTrue(fam.get_children_of("Ursula"))
#         self.assertTrue(fam.set_parent_of("Georgia", "Frank"))
#         self.assertTrue(fam.get_parents_of("Ursula"))
#         self.assertTrue(fam.female("Irvin"))
#         self.assertTrue(fam.is_female("Eve"))
#         self.assertTrue(fam.set_parent_of("Frank", "Bernard"))
#         self.assertTrue(fam.female("Helen"))
#         self.assertTrue(fam.male("Zora"))
#         self.assertTrue(fam.female("Zora"))
#         self.assertTrue(fam.set_parent_of("Tanya", "Bernard"))
#         self.assertTrue(fam.is_female("Irvin"))
#         self.assertTrue(fam.get_parents_of("Bernard"))
#         self.assertTrue(fam.is_male("Maria"))


if __name__ == '__main__':
    unittest.main()
