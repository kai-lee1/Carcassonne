import CarcassonneMain, GUI, Input, Player, Scorer, Tile, EmptyTile

class Player:
    def __init__(self):
        self.meeple_count = 7
        self.score = 0

    def place_tile(self, board):
        try:
            x = int(CarcassonneMain.inp.coordx.getText())
        except (ValueError, NumberFormatException):
            CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates")
            CarcassonneMain.waitButton()
            self.place_tile(board)
            return

        try:
            y = int(CarcassonneMain.inp.coordy.getText())
        except (ValueError, NumberFormatException):
            CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates")
            CarcassonneMain.waitButton()
            self.place_tile(board)
            return

        try:
            rotate_ans = int(CarcassonneMain.inp.rots.getText())
        except (ValueError, NumberFormatException):
            CarcassonneMain.inp.error.setText("Invalid rotations. Try new number")
            CarcassonneMain.waitButton()
            self.place_tile(board)
            return

        self.rotate_tile(board.turn_count, rotate_ans, board)

        if x > 0 and y > 0 and (board.valid_side(board.tiles[board.turn_count], board.board[x][y - 1], 0)) and (board.valid_side(board.tiles[board.turn_count], board.board[x + 1][y], 1)) and (board.valid_side(board.tiles[board.turn_count], board.board[x][y + 1], 2)) and (board.valid_side(board.tiles[board.turn_count], board.board[x - 1][y], 3)) and (board.board[x][y].types[0] == -1) and ((board.board[x + 1][y].types[0] != -1) or (board.board[x][y + 1].types[0] != -1) or (board.board[x - 1][y].types[0] != -1) or (board.board[x][y - 1].types[0] != -1)):

            board.board[x][y] = board.tiles[board.turn_count]

            worked = False
            try:
                position = int(CarcassonneMain.inp.meeple.getText())
            except (ValueError, NumberFormatException):
                position = -1

            if position != -1:
                worked = self.place_meeple(position, x, y, board)
            else:
                worked = True

            while not worked:
                CarcassonneMain.inp.error.setText("invalid meeple position. Choose -1 to not place one.")
                CarcassonneMain.waitButton()
                if int(CarcassonneMain.inp.meeple.getText()) != -1:
                    try:
                        position = int(CarcassonneMain.inp.meeple.getText())
                    except (ValueError, NumberFormatException):
                        position = -1
                    if position != -1:
                        worked = self.place_meeple(position, x, y, board)
                    else:
                        worked = True
                else:
                    break

            if worked:
                self.meeple_count -= 1

            scorer = Scorer(board, x, y)
            scorer.scoring(x, y, board)

        else:
            CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates")
            CarcassonneMain.waitButton()
            self.place_tile(board)

    def place_meeple(self, position, x, y, board):
        tile = board.board[x][y]
        turn = board.turn_count % len(board.players)
        tile.meeple[0] = turn
        tile.meeple[14] = position

        switch = {
            1: self.handle_edge_case,
            0: self.handle_side_case,
            2: self.handle_side_case
        }
        case_handler = switch.get(position % 3, lambda: False)
        return case_handler(position, x, y, board, tile)

    def handle_edge_case(self, position, x, y, board, tile):
        tile.meeple[1] = board.board[x][y].types[position // 3]
        if tile.types[position // 3] == 2:
            tile.meeple[position + 2] = 1
            tile.meeple[position + 1] = 1
            tile.meeple[position + 3] = 1
            for j in range(3):
                if position // 3 <= j:
                    if board.board[x][y].connected[position // 3][j] and board.board[x][y].types[j + 1] == 2:
                        tile.meeple[(j + 1) * 3 + 2 + 1] = 1
                        tile.meeple[(j + 1) * 3 + 2] = 1
                        tile.meeple[(j + 1) * 3 + 2 + 2] = 1
                elif board.board[x][y].connected[position // 3][j] and board.board[x][y].types[j] == 2:
                    tile.meeple[j * 3 + 2 + 1] = 1
                    tile.meeple[j * 3 + 2] = 1
                    tile.meeple[j * 3 + 2 + 2] = 1
        elif tile.types[position // 3] == 1:
            tile.meeple[position + 2] = 1
            for j in range(3):
                if position // 3 <= j:
                    if board.board[x][y].connected[position // 3][j] and board.board[x][y].types[j + 1] == 1:
                        tile.meeple[(j + 1) * 3 + 2 + 1] = 1
                        break
                elif board.board[x][y].connected[position // 3][j] and board.board[x][y].types[j] == 1:
                    tile.meeple[j * 3 + 2 + 1] = 1
                    break
        else:
            board.field_meeples.append([x, y])
            tile.meeple[position + 2] = 1
            tile.meeple[position + 1] = 1
            tile.meeple[position + 3] = 1
            for j in range(3):
                if position // 3 <= j:
                    if board.board[x][y].connected[position // 3][j] and board.board[x][y].types[j + 1] == 0:
                        tile.meeple[(j + 1) * 3 + 2 + 1] = 1
                        tile.meeple[(j + 1) * 3 + 2] = 1
                        tile.meeple[(j + 1) * 3 + 2 + 2] = 1
                elif board.board[x][y].connected[position // 3][j] and board.board[x][y].types[j] == 0:
                    tile.meeple[j * 3 + 2 + 1] = 1
                    tile.meeple[j * 3 + 2] = 1
                    tile.meeple[j * 3 + 2 + 2] = 1
            if tile.types[(position // 3 + 1) % 4] == 0:
                tile.meeple[(position + 2) % 12 + 2] = 1
            if tile.types[(position // 3 + 3) % 4] == 0:
                tile.meeple[(position - 2) % 12 + 2] = 1

            if tile.type == "edgecityroadleft":
                tile.meeple[(position + 7) % 12 + 2] = 1
            elif tile.type == "edgecityroadright":
                tile.meeple[(position + 5) % 12 + 2] = 1
        return True

    def handle_side_case(self, position, x, y, board, tile):
        if tile.types[position // 3] != 1 or tile.types[(position // 3 - 1) % 3] == 0:
            tile.meeple[0] = -1
            tile.meeple[14] = -1
            return False
        board.field_meeples.append([x, y])
        if tile.types[(position // 3 - 1) % 3] == 1:
            tile.meeple[position + 2] = 1
            tile.meeple[(position - 1) % 12 + 2] = 1
        else:
            tile.meeple[position + 2] = 1
            if tile.type == "cornercityroad":
                tile.meeple[(position + 5) % 12 + 2] = 1
            elif tile.type == "edgecityroadt" or tile.type == "edgecityroadstraight":
                tile.meeple[(position + 8) % 12 + 2] = 1
        tile.meeple[1] = 0
        return True

    def rotate_tile(self, turn, rotate, board):
        board.tiles[turn].rotations += rotate
        for _ in range(rotate):
            old = board.tiles[turn].types[:]
            board.tiles[turn].types = [old[3], old[0], old[1], old[2]]

            for j in range(4):
                oldcon = board.tiles[turn].connected[j][:]
                if j != 3:
                    board.tiles[turn].connected[j] = [oldcon[2], oldcon[0], oldcon[1]]

            oldcono = board.tiles[turn].connected[:]
            board.tiles[turn].connected = [oldcono[3], oldcono[0], oldcono[1], oldcono[2]]

