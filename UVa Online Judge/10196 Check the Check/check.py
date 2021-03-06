# hk 4/20

WHITE = True
BLACK = False

KING = 'k'
KNIGHT = 'n'
ROOK = 'r'
QUEEN = 'q'
PAWN = 'p'
BISHOP = 'b'
EMPTY = '.'


class Board:
    def __init__(self, board):
        self.board = board
        self.kings = [(0,0), (0,0)]

        for y, row in enumerate(board):
            for x, val in enumerate(row):
                if val.lower() == KING:
                    self.kings[self.piece_colour(val)] = (x, y)

    def empty(self):
        return self.kings == [(0, 0), (0, 0)]

    def piece_colour(self, piece):
        return piece.isupper()

    def some_square_is_king(self, squares, attack_colour):
        return any(p == self.kings[not attack_colour] for p in squares)

    def pawn_creates_check(self, x, y, pawn_colour):
        dir = 1 if pawn_colour == BLACK else -1
        return self.some_square_is_king(((x+1, y+dir), (x-1, y+dir)), pawn_colour)

    def knight_creates_check(self, x, y, knight_colour):
        threat_points = (
            (x-2, y+1), (x-1, y+2), (x+1, y+2), (x+2, y+1),
            (x-2, y-1), (x-1, y-2), (x+1, y-2), (x+2, y-1))

        return self.some_square_is_king(threat_points, knight_colour)

    def king_creates_check(self, x, y, attack_king_colour):
        threat_points = [(xi,yi) for xi in range(x-1, x+2) for yi in range(y-1, y+2) if (xi, yi) != (x, y)]
        return self.some_square_is_king(threat_points, attack_king_colour)

    def rook_creates_check(self, x, y, rook_colour):
        kx, ky = self.kings[not rook_colour]
        return ((x == kx) or (y == ky)) and not self.blocked(x, y, kx, ky)

    def bishop_creates_check(self, x, y, bishop_colour):
        kx, ky = self.kings[not bishop_colour]
        return abs(x - kx) == abs(y - ky) and not self.blocked(x, y, kx, ky)

    def queen_creates_check(self, x, y, queen_colour):
        return self.rook_creates_check(x, y, queen_colour) or self.bishop_creates_check(x, y, queen_colour)

    def blocked(self, x, y, dst_x, dst_y):
        """ Are there any pieces in the way? @pre: a H,V or D path between x, y, and dst x, dst y"""
        dx, dy = (dst_x - x), (dst_y - y)
        x_step = 0 if dx == 0 else int(dx / abs(dx))
        y_step = 0 if dy == 0 else int(dy / abs(dy))

        while True:
            x += x_step
            y += y_step

            if (x, y) == (dst_x, dst_y):
                return False

            if self.board[y][x] != EMPTY:
                return True

    def has_check(self):
        check_fns = {
            KING: self.king_creates_check,
            QUEEN: self.queen_creates_check,
            KNIGHT: self.knight_creates_check,
            ROOK: self.rook_creates_check,
            PAWN: self.pawn_creates_check,
            BISHOP: self.bishop_creates_check
        }

        for y, row in enumerate(self.board):
            for x, piece in enumerate(row):
                if piece != EMPTY and check_fns[piece.lower()](x, y, self.piece_colour(piece)):
                    return not self.piece_colour(piece)

        return None


def main():
    game_no = 1

    while True:
        board = Board([input() for _ in range(8)])
        if board.empty():
            break

        input() # blank line between inputs
        colour_name = {BLACK: 'black', WHITE: 'white', None: 'no'}[board.has_check()]
        print("Game #%d: %s king is in check." % (game_no, colour_name))

        game_no += 1


if __name__ == '__main__':
    main()