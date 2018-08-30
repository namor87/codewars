from py import test
import we_are_family


@test.describe('The basics')
def the_basics():
    @test.it('Should be possible to create a family instance having the expected methods')
    def test_it():
        fam = family()
        for method in ["male", "is_male", "female", "is_female", "set_parent_of", "get_parents_of", "get_children_of"]:
            test.assert_equals(hasattr(fam, method) and callable(getattr(fam, method)), True, "The family class does not define the {} method".format(method))
    @test.it("Should return True for a valid parent relationship")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Susan", "Otilia"), True)
    @test.it("Should return True for a valid father relationship")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Morgan", "Frank")
                       and fam.male("Frank"), True)
    @test.it("Should return True for a valid mother relationship")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Megan", "Helen")
                       and fam.female("Helen"), True)
    @test.it("Should not accept that child and parent have the same name")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("John", "John"), False)
    @test.it("Should echo back with get_parents_of what was affirmed before")
    def test_it():
        fam = family()
        fam.set_parent_of("Megan", "Helen")
        test.assert_equals(fam.get_parents_of("Megan"), ["Helen"])
    @test.it("Should echo back with get_children_of what was affirmed before")
    def test_it():
        fam = family()
        fam.set_parent_of("Morgan", "Frank")
        test.assert_equals(fam.get_children_of("Frank"), ["Morgan"])
    @test.it("Should not include a relationship that was rejected before")
    def test_it():
        fam = family()
        fam.set_parent_of("John", "John")
        test.assert_equals(fam.get_parents_of("John"), [])
    @test.it("Should allow to add a second parent")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Susan", "Otilia")
                       and fam.set_parent_of("Susan", "Jeremy"), True)
    @test.it("Should list parents in alphabetical order")
    def test_it():
        fam = family()
        fam.set_parent_of("Susan", "Otilia")
        fam.set_parent_of("Susan", "Jeremy")
        test.assert_equals(fam.get_parents_of("Susan"), ["Jeremy", "Otilia"])
    @test.it("Should allow to add a second and third child")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Susan", "Otilia")
                       and fam.set_parent_of("Penelope", "Jeremy")
                       and fam.set_parent_of("Hank", "Jeremy"), True)
    @test.it("Should list children in alphabetical order")
    def test_it():
        fam = family()
        fam.set_parent_of("Susan", "Jeremy")
        fam.set_parent_of("Penelope", "Jeremy")
        fam.set_parent_of("Hank", "Jeremy")
        test.assert_equals(fam.get_children_of("Jeremy"), ["Hank", "Penelope", "Susan"])
    @test.it("Should not complain when the same info is provided twice, but not store it twice either")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Megan", "Helen")
                       and fam.set_parent_of("Megan", "Helen"), True)
        test.assert_equals(fam.get_parents_of("Megan"), ["Helen"])
    @test.it("Should reject a gender assignment that does not match with previous information")
    def test_it():
        fam = family()
        test.assert_equals(fam.male("Frank"), True)
        test.assert_equals(fam.female("Frank"), False)
    @test.it("Should support grandparents")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Susan", "Otilia")
                       and fam.set_parent_of("Susan", "Jeremy")
                       and fam.set_parent_of("Otilia", "Peter"), True)
        test.assert_equals(fam.get_parents_of("Susan"), ["Jeremy", "Otilia"])
        test.assert_equals(fam.get_parents_of("Otilia"), ["Peter"])
    @test.it("Should keep two family instances separate")
    def test_it():
        fam = family()
        fam2 = family()
        test.assert_equals(fam.set_parent_of("Otilia", "Peter"), True)
        test.assert_equals(fam2.set_parent_of("Otilia", "Jeff"), True)
        test.assert_equals(fam2.get_parents_of("Otilia"), ["Jeff"])
        test.assert_equals(fam.get_parents_of("Otilia"), ["Peter"])

@test.describe('The example in the description')
def describe():
    @test.it("Should confirm the introduction of the family members")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Frank", "Morgan")
                       and fam.set_parent_of("Frank", "Dylan")
                       and fam.male("Dylan")
                       and fam.set_parent_of("Joy", "Frank")
                       and fam.male("Frank"), True)
    @test.it("Should deduce that Morgan is a woman")
    def test_it():
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        test.assert_equals(fam.male("Morgan"), False)
    @test.it("Should be able to continue after a rejected assertion")
    def test_it():
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        fam.male("Morgan")
        test.assert_equals(fam.set_parent_of("July", "Morgan"), True)
    @test.it("Should not know Joy's gender")
    def test_it():
        fam = family()
        fam.set_parent_of("Frank", "Morgan")
        fam.set_parent_of("Frank", "Dylan")
        fam.male("Dylan")
        fam.set_parent_of("Joy", "Frank")
        fam.male("Frank")
        test.assert_equals(fam.is_male("Joy") or fam.is_female("Joy"), False)
    @test.it("Should know Morgan's children")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Frank", "Morgan")
                       and fam.set_parent_of("July", "Morgan"), True)
        test.assert_equals(fam.get_children_of("Morgan"), ["Frank", "July"])
    @test.it("Should know Morgan's children after a new child was declared")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Frank", "Morgan")
                       and fam.set_parent_of("July", "Morgan")
                       and fam.set_parent_of("Jennifer", "Morgan"), True)
        test.assert_equals(fam.get_children_of("Morgan"), ["Frank", "Jennifer", "July"])
    @test.it("Should only consider Frank as Dylan's child")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Frank", "Dylan")
                       and fam.set_parent_of("Frank", "Morgan")
                       and fam.set_parent_of("July", "Morgan")
                       and fam.set_parent_of("Jennifer", "Morgan"), True)
        test.assert_equals(fam.get_children_of("Dylan"), ["Frank"])
    @test.it("Should confirm the obvious")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Frank", "Dylan")
                       and fam.set_parent_of("Frank", "Morgan"), True)
        test.assert_equals(fam.get_parents_of("Frank"), ["Dylan", "Morgan"])
    @test.it("Should know someone's parent cannot be their child")
    def test_it():
        fam = family()
        test.assert_equals(fam.set_parent_of("Frank", "Morgan"), True)
        test.assert_equals(fam.set_parent_of("Morgan", "Frank"), False)
