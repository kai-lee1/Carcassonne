class Tile:
    def __init__(self, types, connected):
        self.types = types
        self.rotations = 0
        self.connected = connected
        self.meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]
        self.type = ""
        self.completion = [0, 0, 0, 0]

    def __init__(self, type):
        self.type = type
        self.meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]
        self.completion = [0, 0, 0, 0]
        if type == "allcity":
            self.types = [2, 2, 2, 2]
            self.connected = [[True, True, True],
                              [True, True, True],
                              [True, True, True],
                              [True, True, True]]
        elif type == "1not":
            self.types = [2, 2, 0, 2]
            self.connected = [[True, True, True],
                              [True, True, True],
                              [True, True, True],
                              [True, True, True]]
        elif type == "1road":
            self.types = [2, 2, 1, 2]
            self.connected = [[True, True, True],
                              [True, True, True],
                              [True, True, True],
                              [True, True, True]]
        elif type == "cornercity":
            self.types = [2, 0, 0, 2]
            self.connected = [[True, True, True],
                              [True, True, True],
                              [True, True, True],
                              [True, True, True]]
        elif type == "cornercityroad":
            self.types = [2, 1, 1, 2]
            self.connected = [[False, False, True],
                              [False, True, False],
                              [False, True, False],
                              [True, False, False]]
        elif type == "bowtiecity":
            self.types = [0, 2, 0, 2]
            self.connected = [[True, False, True],
                              [True, True, True],
                              [False, True, True],
                              [True, True, True]]
        elif type == "2adjacentcity":
            self.types = [2, 0, 0, 2]
            self.connected = [[True, True, False],
                              [True, True, True],
                              [True, True, True],
                              [False, True, True]]
        elif type == "bowtiefield":
            self.types = [2, 0, 2, 0]
            self.connected = [[True, False, True],
                              [True, True, True],
                              [False, True, True],
                              [True, True, True]]
        elif type == "1edgecity":
            self.types = [2, 0, 0, 0]
            self.connected = [[True, True, True],
                              [True, True, True],
                              [True, True, True],
                              [True, True, True]]
        elif type == "edgecityroadleft":
            self.types = [2, 0, 1, 1]
            self.connected = [[True, False, False],
                              [True, True, True],
                              [False, True, True],
                              [False, True, True]]
        elif type == "edgecityroadright":
            self.types = [2, 1, 1, 0]
            self.connected = [[False, False, True],
                              [False, True, True],
                              [False, True, True],
                              [True, True, True]]
        elif type == "edgecityroadt":
            self.types = [2, 1, 1, 1]
            self.connected = [[False, False, False],
                              [False, False, False],
                              [False, False, False],
                              [False, False, False]]
        elif type == "edgecityroadstraight":
            self.types = [2, 1, 0, 1]
            self.connected = [[False, False, False],
                              [False, True, True],
                              [False, True, True],
                              [False, True, True]]
        elif type == "straightroad":
            self.types = [0, 1, 0, 1]
            self.connected = [[True, False, True],
                              [True, True, True],
                              [False, True, True],
                              [True, True, True]]
        elif type == "Lroad":
            self.types = [0, 0, 1, 1]
            self.connected = [[True, True, True],
                              [True, True, True],
                              [True, True, True],
                              [True, True, True]]
        elif type == "Troad":
            self.types = [0, 1, 1, 1]
            self.connected = [[True, False, True],
                              [True, False, False],
                              [False, False, False],
                              [True, False, False]]
        elif type == "+road":
            self.types = [1, 1, 1, 1]
            self.connected = [[False, False, False],
                              [False, False, False],
                              [False, False, False],
                              [False, False, False]]

    def connects(self, pos1):
        newTile = self.copy()
        newTile.meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]

        if self.placeMeeple(pos1, newTile):
            return newTile.meeple[2:14]

        if pos1 % 3 == 0:
            if pos1 == 0:
                self.placeMeeple(10, newTile)
                return newTile.meeple[2:14]
            self.placeMeeple(pos1 - 2, newTile)
            return newTile.meeple[2:14]

        if pos1 == 11:
            self.placeMeeple(1, newTile)
            return newTile.meeple[2:14]
        self.placeMeeple(pos1 + 2, newTile)
        return newTile.meeple[2:14]

    def placeMeeple(self, position, tile):
        if position % 3 == 1:
            tile.meeple[1] = tile.types[position // 3]
            if tile.types[(position) // 3] == 2:
                tile.meeple[position + 2] = 1
                tile.meeple[position + 1] = 1
                tile.meeple[position + 3] = 1
                for j in range(3):
                    if position // 3 < j:
                        if tile.connected[position // 3][j] and tile.types[j + 1] == 2:
                            tile.meeple[(j + 1) * 3 + 2 + 1] = 1
                            tile.meeple[(j + 1) * 3 + 2] = 1
                            tile.meeple[(j + 1) * 3 + 2 + 2] = 1
                    elif tile.connected[position // 3][j] and tile.types[j] == 2:
                        tile.meeple[j * 3 + 2 + 1] = 1
                        tile.meeple[j * 3 + 2] = 1
                        tile.meeple[j * 3 + 2 + 2] = 1
            elif tile.types[(position) // 3] == 1:
                tile.meeple[position + 2] = 1
                for j in range(3):
                    if position // 3 < j:
                        if tile.connected[position // 3][j] and tile.types[j + 1] == 1:
                            tile.meeple[(j + 1) * 3 + 2 + 1] = 1
                            break
                    elif tile.connected[position // 3][j] and tile.types[j] == 1:
                        tile.meeple[j * 3 + 2 + 1] = 1
                        break
            else:
                tile.meeple[position + 2] = 1
                tile.meeple[position + 1] = 1
                tile.meeple[position + 3] = 1
                for j in range(3):
                    if position // 3 <= j:
                        if tile.connected[position // 3][j] and tile.types[j + 1] == 0:
                            tile.meeple[(j + 1) * 3 + 2 + 1] = 1
                            tile.meeple[(j + 1) * 3 + 2] = 1
                            tile.meeple[(j + 1) * 3 + 2 + 2] = 1
                            if tile.types[j] == 1:
                                tile.meeple[(j * 3 + 2 + 2) % 12] = 1
                            if tile.types[(j + 2) % 4] == 1:
                                tile.meeple[(((j + 2) % 4) * 3 + 2) % 12] = 1
                    elif tile.connected[position // 3][j] and tile.types[j] == 0:
                        tile.meeple[j * 3 + 2 + 1] = 1
                        tile.meeple[j * 3 + 2] = 1
                        tile.meeple[j * 3 + 2 + 2] = 1
                        if tile.types[(j + 3) % 4] == 1:
                            tile.meeple[(((j + 3) % 4) * 3 + 2 + 2) % 12] = 1
                        if tile.types[(j + 1) % 4] == 1:
                            tile.meeple[((j + 1) * 3 + 2) % 12] = 1
                if tile.types[(position // 3 + 1) % 4] == 1:
                    tile.meeple[(position + 2) % 12 + 2] = 1
                if tile.types[(position // 3 + 3) % 4] == 1:
                    tile.meeple[(position + 10) % 12 + 2] = 1

                if tile.type == "edgecityroadleft":
                    tile.meeple[(position + 7) % 12 + 2] = 1
                elif tile.type == "edgecityroadright":
                    tile.meeple[(position + 5) % 12 + 2] = 1
        elif position % 3 == 0:
            if tile.types[(position) // 3] != 1 or tile.types[((position) // 3 - 1) % 3] == 0:
                tile.meeple[0] = -1
                tile.meeple[14] = -1
                return False
            if tile.types[((position) // 3 - 1) % 3] == 1:
                tile.meeple[position + 2] = 1
                tile.meeple[(position - 1) % 12 + 2] = 1
            else:
                tile.meeple[position + 2] = 1
                if tile.type == "cornercityroad":
                    tile.meeple[(position + 5) % 12 + 2] = 1
                elif tile.type == "edgecityroadt" or tile.type == "edgecityroadstraight":
                    tile.meeple[(position + 8) % 12 + 2] = 1
            tile.meeple[1] = 0
        elif position % 3 == 2:
            if tile.types[(position) // 3] != 1 or tile.types[((position) // 3 + 1) % 3] == 0:
                tile.meeple[0] = -1
                tile.meeple[14] = -1
                return False
            if tile.types[((position) // 3 + 1) % 3] == 1:
                tile.meeple[position + 2] = 1
                tile.meeple[(position + 1) % 12 + 2] = 1
            else:
                tile.meeple[position + 2] = 1
                if tile.type == "cornercityroad":
                    tile.meeple[(position + 7) % 12 + 2] = 1
                elif tile.type == "edgecityroadt" or tile.type == "edgecityroadstraight":
                    tile.meeple[(position + 4) % 12 + 2] = 1
            tile.meeple[1] = 0
        else:
            return False
        return True

    def copy(self):
        ret = Tile(self.types.copy(), self.connected.copy())
        ret.type = self.type
        ret.meeple = self.meeple.copy()
        ret.rotations = self.rotations
        ret.completion = self.completion.copy()
        return ret

    def __str__(self):
        ret = ""
        for j in range(4):
            ret += str(self.types[j])
        for x in self.meeple:
            if x > 0:
                ret += str(x)
                break
        return ret


