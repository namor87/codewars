
def enemy(player):
    return 1 - player

def direction(player):
    return (2 * player) - 1

def copyPiece(piece):
    return dict(piece)

def copyBoard(board):
    return list(board)

def sign(x):
    return 1 if x > 0 else (-1 if x < 0 else 0)


def findByOwner(pieces, player):
    return filter(lambda p: p['owner'] == player, pieces)

def findByType(pieces, pieceType):
    return filter(lambda piece: piece['piece'] == pieceType, pieces)

def findByPosition(pieces, x, y):
    return filter(lambda piece: piece['x'] == x and piece['y'] == y, pieces)

def findMyKing(board, owner):
    return findByOwner(findByType(board, "king"), owner)[0]


def getSurroundingSquares(piece):
    for i in range(-1, 2):
        for j in range(-1, 2):
            if (i, j) != (0, 0):
                new_x = piece['x'] + i
                new_y = piece['y'] + j
                if 0 <= new_x <= 7 and 0 <= new_y <= 7:
                    yield (new_x, new_y)


def getInBeetweenSquares(attacker, x, y, ):
    grad_x = sign(x - attacker['x'])
    grad_y = sign(y - attacker['y'])
    pos_x, pos_y = attacker['x'], attacker['y']
    while (pos_x + grad_x != x or pos_y + grad_y != y):
        pos_x += grad_x
        pos_y += grad_y
        yield (pos_x, pos_y)

def isPathObstructed(attacker, x, y, board):
    for (x, y) in getInBeetweenSquares(attacker, x, y, ):
        if findByPosition(board, x, y):
            return True
    return False


def isPawnOnStartingRank(y, direction):
    return (y - direction) in (0, 7)


def isPawnAttacking(attacker, x, y, board, with_attack):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    attack_direction = direction(attacker['owner'])
    if with_attack:
        return diff_x in (-1, 1) and diff_y == attack_direction
    else:
        if isPawnOnStartingRank(attacker['y'], attack_direction):
            return diff_x == 0 and diff_y == attack_direction
        else:
            return diff_x == 0 and diff_y in (attack_direction, 2*attack_direction)


def isBisshopAttacking(attacker, x, y, board, with_attack):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    if abs(diff_y) == abs(diff_x):
        return not isPathObstructed(attacker, x, y, board)

def isRookAttacking(attacker, x, y, board, with_attack):
    if x == attacker['x'] or y == attacker['y']:
        return not isPathObstructed(attacker, x, y, board)

def isKnightAttacking(attacker, x, y, board, with_attack):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    return (abs(diff_x), abs(diff_y)) in ((1, 2), (2, 1))

def isQueenAttacking(attacker, x, y, board, with_attack):
    return isBisshopAttacking(attacker, x, y, board, with_attack) or isRookAttacking(attacker, x, y, board, with_attack)

def isKingAttacking(attacker, x, y, board, with_attack):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    return abs(diff_x) <= 1 and abs(diff_y) <= 1 and (diff_x != 0 or diff_y != 0)

ATTACK_METHODS = {
    'pawn': isPawnAttacking,
    'rook': isRookAttacking,
    'bishop': isBisshopAttacking,
    'knight': isKnightAttacking,
    'queen': isQueenAttacking,
    'king': isKingAttacking
}

def getAttackMethod(piece):
    return ATTACK_METHODS[piece['piece']]


def canMoveInto(attacker, x, y, board, with_attack=True):
    return getAttackMethod(attacker)(attacker, x, y, board, with_attack)

def squareTaken(x, y, pieces):
    return any(filter(lambda piece: (piece['x'], piece['y']) == (x, y), pieces))

def canEscapeCheck(king, board):
    comrades = findByOwner(board, king['owner'])
    for (x, y) in getSurroundingSquares(king):
        if not squareTaken(x, y, comrades):
            if not findPiecesThatCanMoveToSquare(x, y, board, king['owner'], True):
                return True
    return False

def isDoubleCheck(attackers):
    return len(attackers) > 1

def isPinned(piece, board):
    owner = piece['owner']
    king = findMyKing(board, owner)
    attackers_with = findPiecesAttackers(king, board, owner)
    board_without = copyBoard(board)
    board_without.remove(piece)
    attackers_without = findPiecesAttackers(king, board_without, owner)
    diff = [i for i in attackers_without if i not in attackers_with]
    return len(diff) > 0

def isProtected(piece, board):
    enemy_piece = copyPiece(piece)
    enemy_piece['owner'] = enemy(piece['owner'])
    reversed_board = copyBoard(board)
    if reversed_board.count(piece) > 0:
        reversed_board.remove(piece)
    #reversed_board.append(enemy_piece)
    return canPieceBeCaptured(enemy_piece, reversed_board, strict=False)

def canPieceBeCaptured(piece, board, strict=True, with_attack=True):
    for defender in findPiecesThatCanMoveToSquare(piece['x'], piece['y'], board, piece['owner'], with_attack):
        if strict:
            if defender['piece'] != 'king':
                if not isPinned(defender, board):
                    return True
            else:
                if not isProtected(piece, board):
                    return True
        else:
            return True
    return False

def canAttackBeBlocked(attacker, king, board):
    if attacker['piece'] != 'knight':
        for (x, y) in getInBeetweenSquares(attacker, king['x'], king['y']):
            fake = {'type': 'fake', 'x': x, 'y': y, 'owner': enemy(king['owner'])}
            if canPieceBeCaptured(fake, board, strict=True, with_attack=False):
                return True
    return False


def findPiecesThatCanMoveToSquare(x, y, board, target_player, with_attack=True):
    enemies = findByOwner(board, enemy(target_player))
    return filter(lambda p: canMoveInto(p, x, y, board, with_attack), enemies)

def findPiecesAttackers(piece, board, target_player):
    return findPiecesThatCanMoveToSquare(piece['x'], piece['y'], board, target_player, True)


# Returns true if the arrangement of the
# pieces is a check mate, otherwise false
def isMate(board, player):
    # outputBoard(pieces)
    king = findByOwner(findByType(board, "king"), player)[0]
    enemies = findByOwner(board, enemy(player))
    attackers = filter(lambda p: canMoveInto(p, king['x'], king['y'], board), enemies)
    if attackers:
        if canEscapeCheck(king, board):
            return False
        if not isDoubleCheck(attackers):
            if canPieceBeCaptured(attackers[0], board):
                return False
            if canAttackBeBlocked(attackers[0], king, board):
                return False
        return True
    else:
        return False
# Returns an array of threats if the arrangement of
# the pieces is a check, otherwise false


def isCheck(board, player):
    # outputBoard(pieces)
    king = findMyKing(board, player)
    attackers = findPiecesAttackers(king, board, player)
    return attackers if len(attackers) > 0 else False
