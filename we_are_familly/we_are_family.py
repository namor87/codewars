class Person:

    def __init__(self, name):
        self._name = name
        self._parents = {}
        self._childeren = {}

    def add_parent(self, parent):
        pass

    def add_child(self, parent):
        pass

    def female(self):
        pass

    def male(self):
        pass

    def is_female(self):
        pass

    def is_male(self):
        pass

    def __no_circular(self):
        return False


class family:

    def __init__(self):
        self._people = {}

    def set_parent_of(self, child, parent):
        pass

    def female(self, name):
        pass

    def male(self, name):
        pass

    def is_female(self, name):
        pass

    def is_male(self, name):
        pass

    def get_parents_of(self, name):
        pass

    def get_children_of(self, name):
        pass

