import itertools

class Person:
    FEMALE = "F"
    MALE = "M"
    UNKNOWN = "?"

    def __init__(self, name):
        self._name = name
        self._parents = []
        self._children = set()
        self._gender = self.UNKNOWN

    def __repr__(self):
        return str(self.get_name())

    def get_name(self):
        return self._name

    def __is_ancestor_of(self, person):
        return self == person or any(map(lambda p: p.__is_ancestor_of(person), self._children))

    def add_parent(self, parent):
        if self._parents.__contains__(parent) :
            return True
        if len(self._parents) > 1:
            return False
        if len(self._parents) == 1:
            if all(map(Person.is_female, self._parents + [parent])) :
                return False
            if all(map(Person.is_male, self._parents + [parent])) :
                return False
        if self.__is_ancestor_of(parent) :
            return False
        self._parents.append(parent)
        return True

    def get_parents(self):
        return list(self._parents)

    def add_child(self, child):
        self._children.add(child)
        return True

    def get_children(self):
        return list(self._children)

    def female(self):
        if self.is_male():
            return False
        if any(map(Person.is_female, self._get_spouses())):
            return False
        self._gender = self.FEMALE
        for s in self._get_spouses():
            if s._gender == self.UNKNOWN:
                s.male()
        return True

    def _get_spouses(self):
        return filter(lambda x: self != x, itertools.chain.from_iterable(map(Person.get_parents, self._children)))

    def male(self):
        if self.is_female():
            return False
        if any(map(Person.is_male, self._get_spouses())):
            return False
        self._gender = self.MALE
        for s in self._get_spouses():
            if s._gender == self.UNKNOWN:
                s.female()
        return True

    def is_female(self):
        return self._gender == self.FEMALE

    def is_male(self):
        return self._gender == self.MALE


class family:
    def __init__(self):
        self._people = {}

    def __get_person(self, name):
        if not self._people.has_key(name):
            self._people[name] = Person(name)
        return self._people[name]

    def set_parent_of(self, child_name, parent_name):
        _child = self.__get_person(child_name)
        _parent = self.__get_person(parent_name)
        return _child.add_parent(_parent) and _parent.add_child(_child)

    def female(self, name):
        return self.__get_person(name).female()

    def male(self, name):
        return self.__get_person(name).male()

    def is_female(self, name):
        return self.__get_person(name).is_female()

    def is_male(self, name):
        return self.__get_person(name).is_male()

    def get_parents_of(self, name):
        return sorted(map(Person.get_name, self.__get_person(name).get_parents()))

    def get_children_of(self, name):
        return sorted(map(Person.get_name, self.__get_person(name).get_children()))

