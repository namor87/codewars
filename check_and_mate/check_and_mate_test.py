import unittest
from check_and_mate import *


class TestChecks(unittest.TestCase):
    def test_pawn_check(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "pawn", 'owner': 1, 'x': 5, 'y': 6}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Pawn threatens king")

    def test_rook_check(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 4, 'y': 1}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Rook threatens king")

    def test_rook_check_blocked(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 4, 'y': 1},
            {'piece': "pawn", 'owner': 1, 'x': 4, 'y': 4}
        ]
        self.assertEquals(isCheck(pieces, 0), False, "Rook threat blocked by pawn")

    def test_knight_check(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "knight", 'owner': 1, 'x': 2, 'y': 6}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Knight threatens king")

    def test_bishop_check(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "bishop", 'owner': 1, 'x': 0, 'y': 3}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Bishop threatens king")

    def test_bishop_check_blocked(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "pawn", 'owner': 1, 'x': 2, 'y': 5},
            {'piece': "bishop", 'owner': 1, 'x': 0, 'y': 3}
        ]
        self.assertEquals(isCheck(pieces, 0), False, "Bishop attack blocked by pawn")

    def test_queen_check_file(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "queen", 'owner': 1, 'x': 4, 'y': 1}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Queen threatens king")

    def test_queen_check_rank(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "queen", 'owner': 1, 'x': 0, 'y': 7}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Queen threatens king")

    def test_queen_check_diagonal(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "queen", 'owner': 1, 'x': 7, 'y': 4}
        ]
        self.assertEquals(isCheck(pieces, 0), [pieces[2]], "Queen threatens king")

    def test_double_check(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "pawn", 'owner': 0, 'x': 4, 'y': 6},
            {'piece': "pawn", 'owner': 0, 'x': 5, 'y': 6},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "bishop", 'owner': 0, 'x': 5, 'y': 7},
            {'piece': "bishop", 'owner': 1, 'x': 1, 'y': 4},
            {'piece': "rook", 'owner': 1, 'x': 2, 'y': 7, 'prevX': 2, 'prevY': 5}
        ]
        def sortFunc(a, b):
            if (a['y'] == b['y']): return a['x'] - b['x']
            return a['y'] - b['y']
        self.assertEquals(sorted(isCheck(pieces, 0), key=lambda x: x['y']), [pieces[5], pieces[6]], "Double threat")


class TestMates(unittest.TestCase):
    def test_01_easy_escapable_check(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 4, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 4, 'y': 1}
        ]
        self.assertEquals(isMate(pieces, 0), False, "King can escape anywhere")

    def test_02_smothered_mate(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "pawn", 'owner': 0, 'x': 0, 'y': 6},
            {'piece': "pawn", 'owner': 0, 'x': 1, 'y': 6},
            {'piece': "rook", 'owner': 0, 'x': 1, 'y': 7},
            {'piece': "knight", 'owner': 1, 'x': 2, 'y': 6}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Smothered mate")

    def test_03_all_escape_squares_under_attack(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 7, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 2, 'y': 2},
            {'piece': "rook", 'owner': 1, 'x': 7, 'y': 1},
            {'piece': "rook", 'owner': 1, 'x': 1, 'y': 7},
            {'piece': "knight", 'owner': 1, 'x': 1, 'y': 1},
            {'piece': "bishop", 'owner': 1, 'x': 5, 'y': 5, 'prevX': 4, 'prevY': 6}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Mate in the middle of the board")

    def test_04_mate_with_two_rooks(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 7, 'y': 7, 'prevX': 7, 'prevY': 0},
            {'piece': "rook", 'owner': 1, 'x': 6, 'y': 6}
        ]
        self.assertEquals(isMate(pieces, 0), True, "King has nowhere to run")

    def test_05_capture_attacking_piece(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "pawn", 'owner': 0, 'x': 0, 'y': 6},
            {'piece': "pawn", 'owner': 0, 'x': 1, 'y': 6},
            {'piece': "bishop", 'owner': 0, 'x': 1, 'y': 7},
            {'piece': "knight", 'owner': 1, 'x': 2, 'y': 6}
        ]
        self.assertEquals(isMate(pieces, 0), False, "Knight can be taken")

    def test_06_capture_attacking_piece_with_king(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 1, 'y': 7, 'prevX': 1, 'prevY': 0},
            {'piece': "rook", 'owner': 1, 'x': 6, 'y': 6}
        ]
        self.assertEquals(isMate(pieces, 0), False, "Rook can be taken by king")

    def test_07_king_cant_capture_attacking_piece_which_is_protected(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 1, 'y': 7, 'prevX': 1, 'prevY': 0},
            {'piece': "rook", 'owner': 1, 'x': 6, 'y': 6},
            {'piece': "bishop", 'owner': 1, 'x': 7, 'y': 1}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Rook is protected and cant be taken by king ")

    def test_08_defender_is_pinned_and_cant_capture(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "pawn", 'owner': 0, 'x': 0, 'y': 6},
            {'piece': "pawn", 'owner': 0, 'x': 1, 'y': 6},
            {'piece': "bishop", 'owner': 0, 'x': 1, 'y': 7},
            {'piece': "knight", 'owner': 1, 'x': 2, 'y': 6, 'prevX': 4, 'prevY': 5},
            {'piece': "rook", 'owner': 1, 'x': 7, 'y': 7}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Knight can not be taken due to a pin")

    def test_09_check_can_be_blocked(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "rook", 'owner': 0, 'x': 1, 'y': 4},
            {'piece': "rook", 'owner': 1, 'x': 7, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 6, 'y': 6, 'prevX': 6, 'prevY': 0}
        ]
        self.assertEquals(isMate(pieces, 0), False, "Rook can block mate")

    def test_10_defender_is_pinned_and_cant_block(self):
        pieces = [
            {'piece': "king", 'owner': 1, 'x': 4, 'y': 0},
            {'piece': "king", 'owner': 0, 'x': 0, 'y': 7},
            {'piece': "rook", 'owner': 0, 'x': 2, 'y': 5},
            {'piece': "bishop", 'owner': 1, 'x': 4, 'y': 3},
            {'piece': "rook", 'owner': 1, 'x': 7, 'y': 7},
            {'piece': "rook", 'owner': 1, 'x': 6, 'y': 6, 'prevX': 6, 'prevY': 0}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Rook cant block mate due to a pin")


    def test_11_double_check_cant_be_blocked(self):
        pieces = [
            {'piece': "king",   'owner': 1, 'x': 4, 'y': 0},
            {'piece': "rook",   'owner': 1, 'x': 4, 'y': 2},
            {'piece': "knight", 'owner': 1, 'x': 3, 'y': 5, 'prevX': 4, 'prevY': 3},
            {'piece': "king",   'owner': 0, 'x': 4, 'y': 7},
            {'piece': "pawn",   'owner': 0, 'x': 3, 'y': 6},
            {'piece': "knight", 'owner': 0, 'x': 3, 'y': 7},
            {'piece': "knight", 'owner': 0, 'x': 5, 'y': 7},
            {'piece': "bishop", 'owner': 0, 'x': 7, 'y': 2}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Double check cant be blocked")

    def test_12_double_check_cant_be_captured(self):
        pieces = [
            {'piece': "king",   'owner': 1, 'x': 4, 'y': 0},
            {'piece': "rook",   'owner': 1, 'x': 4, 'y': 2},
            {'piece': "knight", 'owner': 1, 'x': 3, 'y': 5, 'prevX': 4, 'prevY': 3},
            {'piece': "king",   'owner': 0, 'x': 4, 'y': 7},
            {'piece': "pawn",   'owner': 0, 'x': 3, 'y': 6},
            {'piece': "pawn",   'owner': 0, 'x': 2, 'y': 6},
            {'piece': "rook",   'owner': 0, 'x': 3, 'y': 7},
            {'piece': "rook",   'owner': 0, 'x': 5, 'y': 7},
            {'piece': "bishop", 'owner': 0, 'x': 7, 'y': 4},
            {'piece': "bishop", 'owner': 0, 'x': 1, 'y': 3},
            {'piece': "queen",  'owner': 0, 'x': 0, 'y': 5}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Double check cant be captured")

    def test_13_double_check_cant_be_blocked_or_captured(self):
        pieces = [
            {'piece': "king",   'owner': 1, 'x': 4, 'y': 0},
            {'piece': "rook",   'owner': 1, 'x': 4, 'y': 2},
            {'piece': "knight", 'owner': 1, 'x': 3, 'y': 5, 'prevX': 4, 'prevY': 3},
            {'piece': "king",   'owner': 0, 'x': 4, 'y': 7},
            {'piece': "pawn",   'owner': 0, 'x': 3, 'y': 6},
            {'piece': "knight", 'owner': 0, 'x': 3, 'y': 7},
            {'piece': "queen",  'owner': 0, 'x': 5, 'y': 7},
            {'piece': "bishop", 'owner': 0, 'x': 6, 'y': 3}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Double check cant be blocked nor caprured")

    def test_14_pawn_cant_block_by_moving_diagonally(self):
        pieces = [
            {'piece': "king",   'owner': 1, 'x': 4, 'y': 0},
            {'piece': "queen",  'owner': 1, 'x': 7, 'y': 4, 'prevX': 3, 'prevY': 0},
            {'piece': "pawn",   'owner': 0, 'x': 3, 'y': 6},
            {'piece': "pawn",   'owner': 0, 'x': 4, 'y': 6},
            {'piece': "pawn",   'owner': 0, 'x': 5, 'y': 5},
            {'piece': "pawn",   'owner': 0, 'x': 6, 'y': 4},
            {'piece': "pawn",   'owner': 0, 'x': 7, 'y': 6},
            {'piece': "queen",  'owner': 0, 'x': 3, 'y': 7},
            {'piece': "king",   'owner': 0, 'x': 4, 'y': 7},
            {'piece': "bishop", 'owner': 0, 'x': 5, 'y': 7},
            {'piece': "knight", 'owner': 0, 'x': 6, 'y': 7},
            {'piece': "rook",   'owner': 0, 'x': 7, 'y': 7}
        ]
        self.assertEquals(isMate(pieces, 0), True, "Pawn cant move diagonally to block queen")

    def test_15_pawn_can_block_by_moving_forward(self):
        pieces = [
            {'piece': "king",   'owner': 1, 'x': 4, 'y': 0},
            {'piece': "queen",  'owner': 1, 'x': 7, 'y': 4, 'prevX': 3, 'prevY': 0},
            {'piece': "pawn",   'owner': 0, 'x': 3, 'y': 6},
            {'piece': "pawn",   'owner': 0, 'x': 4, 'y': 6},
            {'piece': "pawn",   'owner': 0, 'x': 5, 'y': 5},
            {'piece': "pawn",   'owner': 0, 'x': 6, 'y': 6},
            {'piece': "queen",  'owner': 0, 'x': 3, 'y': 7},
            {'piece': "king",   'owner': 0, 'x': 4, 'y': 7},
            {'piece': "bishop", 'owner': 0, 'x': 5, 'y': 7},
            {'piece': "knight", 'owner': 0, 'x': 6, 'y': 7}
        ]
        self.assertEquals(isMate(pieces, 0), False, "Pawn can move forward to block attack")

    def test_16_pawn_can_block_by_moving_forward_two_squares(self):
        pieces = [
            {'piece': "king",   'owner': 1, 'x': 4, 'y': 0},
            {'piece': "rook",   'owner': 1, 'x': 6, 'y': 0},
            {'piece': "bishop", 'owner': 1, 'x': 1, 'y': 1, 'prevX': 2, 'prevY': 0},
            {'piece': "pawn",   'owner': 0, 'x': 4, 'y': 6},
            {'piece': "pawn",   'owner': 0, 'x': 7, 'y': 6},
            {'piece': "king",   'owner': 0, 'x': 7, 'y': 7}
        ]
        self.assertEquals(isMate(pieces, 0), False, "Pawn can move forward two squares to block attack")

    def test_17_pawn_can_capture_attacking_pawn_en_passant(self):
        pieces = [
            {'piece': "bishop", 'owner': 1, 'x': 4, 'y': 2},
            {'piece': "rook",   'owner': 1, 'x': 5, 'y': 2},
            {'piece': "knight", 'owner': 1, 'x': 3, 'y': 3},
            {'piece': "pawn",   'owner': 1, 'x': 4, 'y': 3},
            {'piece': "king",   'owner': 1, 'x': 5, 'y': 3},
            {'piece': "pawn",   'owner': 1, 'x': 3, 'y': 4},
            {'piece': "pawn",   'owner': 0, 'x': 4, 'y': 4, 'prevX': 4, 'prevY': 6},
            {'piece': "knight", 'owner': 0, 'x': 2, 'y': 5},
            {'piece': "queen",  'owner': 0, 'x': 6, 'y': 5},
            {'piece': "pawn",   'owner': 0, 'x': 5, 'y': 6},
            {'piece': "king",   'owner': 0, 'x': 4, 'y': 7}
        ]
        self.assertEquals(isMate(pieces, 1), False, "Pawn can capture another pawn en passant")

if __name__ == '__main__':
    unittest.main()



