
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

def isPawnAttacking(attacker, x, y, board):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    return diff_x in (-1, 1) and diff_y == direction(attacker['owner'])

def isBisshopAttacking(attacker, x, y, board):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    if abs(diff_y) == abs(diff_x):
        return not isPathObstructed(attacker, x, y, board)

def isRookAttacking(attacker, x, y, board):
    if x == attacker['x'] or y == attacker['y']:
        return not isPathObstructed(attacker, x, y, board)

def isKnightAttacking(attacker, x, y, board):
    diff_x = x - attacker['x']
    diff_y = y - attacker['y']
    return (abs(diff_x), abs(diff_y)) in ((1, 2), (2, 1))

def isQueenAttacking(attacker, x, y, board):
    return isBisshopAttacking(attacker, x, y, board) or isRookAttacking(attacker, x, y, board)

def isKingAttacking(attacker, x, y, board):
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

def isAttacking(attacker, x, y, board):
    return getAttackMethod(attacker)(attacker, x, y, board)



def squareTaken(x, y, pieces):
    return any(filter(lambda piece: (piece['x'], piece['y']) == (x, y), pieces))

def canEscapeCheck(king, board):
    comrades = findByOwner(board, king['owner'])
    for (x, y) in getSurroundingSquares(king):
        if not squareTaken(x, y, comrades):
            if not findSquareAttackers(x, y, board, king['owner']):
                return True
    return False

def isDoubleCheck(attackers):
    return len(attackers) > 1

def isPinnedTo(defender, king):
    return False

def isProtected(piece, board):
    enemy_piece = copyPiece(piece)
    enemy_piece['owner'] = enemy(piece['owner'])
    reversed_board = copyBoard(board)
    if reversed_board.count(piece) > 0:
        reversed_board.remove(piece)
    #reversed_board.append(enemy_piece)
    return canPieceBeCaptured(enemy_piece, reversed_board, strict=False)

def canPieceBeCaptured(piece, board, strict=True):
    for defender in findSquareAttackers(piece['x'], piece['y'], board, piece['owner']):
        if not strict:
            return True
        else:
            if defender['piece'] != 'king' :
                if not isPinnedTo(defender, board):
                    return True
            else:
                if not isProtected(piece, board):
                    return True
    return False

def canAttackBeBlocked(attacker, king, board):
    if attacker['piece'] != 'knight':
        for (x,y) in getInBeetweenSquares(attacker, king['x'], king['y']):
            fake = {'type':'fake', 'x':x, 'y':y, 'owner':enemy(king['owner'])}
            if canPieceBeCaptured(fake, board):
                return True
    return False

def findSquareAttackers(x, y, board, target_player):
    enemies = findByOwner(board, enemy(target_player))
    return filter(lambda p: isAttacking(p, x, y, board), enemies)


# Returns true if the arrangement of the
# pieces is a check mate, otherwise false
def isMate(board, player):
    # outputBoard(pieces)
    king = findByOwner(findByType(board, "king"), player)[0]
    enemies = findByOwner(board, enemy(player))
    attackers = filter(lambda p: isAttacking(p, king['x'], king['y'], board), enemies)
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
    king = findByOwner(findByType(board, "king"), player)[0]
    attackers = findSquareAttackers(king['x'], king['y'], board, player)
    return attackers if len(attackers) > 0 else False
