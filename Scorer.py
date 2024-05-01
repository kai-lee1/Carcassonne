class Scorer:
    def __init__(self, board, x, y):
        self.score = 0
        self.tiletracker = []
        self.board = board
        self.x = x
        self.y = y

    def scoring(self, x, y, board):
        checkedSides = [0, 0, 0, 0]
        meeplesPresent = []
        xy = []
        xyn = []
        for i in range(4):
            tx = x
            ty = y
            type = board.board[tx][ty].types[i]
            meeplesPresent = [0] * len(board.players)
            self.tiletracker = []

            if checkedSides[i] == 1:
                continue

            if type == 1:
                if i == 0:
                    xy = [tx, ty - 1]
                elif i == 1:
                    xy = [tx + 1, ty]
                elif i == 2:
                    xy = [tx, ty + 1]
                elif i == 3:
                    xy = [tx - 1, ty]
                else:
                    xy = [0, 0]

                if board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1:
                    meeplesPresent[board.board[tx][ty].meeple[0]] = 1

                if board.board[x][y] not in self.tiletracker:
                    self.tiletracker.append(board.board[x][y])

                self.roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4)
                s = -1
                for j in range(3):
                    if i <= j:
                        if board.board[x][y].connected[i][j] and board.board[x][y].types[j + 1] == 1:
                            s = j + 1
                            break
                    elif board.board[x][y].connected[i][j] and board.board[x][y].types[j] == 1:
                        s = j
                        break

                if s != -1:
                    if s == 0:
                        xyn = [tx, ty - 1]
                    elif s == 1:
                        xyn = [tx + 1, ty]
                    elif s == 2:
                        xyn = [tx, ty + 1]
                    elif s == 3:
                        xyn = [tx - 1, ty]
                    else:
                        xyn = [0, 0]

                    self.roadScore(xyn[0], xyn[1], board, meeplesPresent, (s + 2) % 4)
                    checkedSides[s] = 1

                for k in range(len(self.tiletracker)):
                    if self.tiletracker[k].types[0] == -1:
                        continue

                for k in range(len(meeplesPresent)):
                    if meeplesPresent[k] == 1:
                        board.players[k].score += len(self.tiletracker)

                self.tiletracker = []

                if board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1:
                    board.players[board.board[x][y].meeple[0]].meepleCount += 1
                    board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]

                board.board[x][y].completion[i] = 1
                self.sweepRoadMeeples(xy[0], xy[1], board, (i + 2) % 4)

                if s != -1:
                    board.board[x][y].completion[s] = 1
                    self.sweepRoadMeeples(xyn[0], xyn[1], board, (s + 2) % 4)

            elif type == 2:
                if board.board[x][y].meeple[i * 3 + 2 + 1] == 1:
                    meeplesPresent[board.board[x][y].meeple[0]] = 1

                self.tiletracker.append(board.board[x][y])

                sides = [i]

                for j in range(3):
                    if i > j:
                        if board.board[x][y].connected[i][j] and board.board[x][y].types[j] == 2:
                            sides.append(j)
                    elif board.board[x][y].connected[i][j] and board.board[x][y].types[j + 1] == 2:
                        sides.append(j + 1)

                for j in range(len(sides)):
                    checkedSides[sides[j]] = 1
                    if sides[j] == 0:
                        xy = [tx, y - 1]
                    elif sides[j] == 1:
                        xy = [tx + 1, y]
                    elif sides[j] == 2:
                        xy = [tx, y + 1]
                    elif sides[j] == 3:
                        xy = [tx - 1, y]
                    else:
                        xy = [0, 0]
                    self.cityScore(xy[0], xy[1], board, meeplesPresent, (sides[j] + 2) % 4)

                for k in range(len(self.tiletracker)):
                    if self.tiletracker[k].types[0] == -1:
                        continue

                for k in range(len(meeplesPresent)):
                    if meeplesPresent[k] == 1:
                        board.players[k].score += len(self.tiletracker) * 2

                self.tiletracker = []

                if board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1:
                    board.players[board.board[x][y].meeple[0]].meepleCount += 1
                    board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]

                board.board[x][y].completion[i] = CarcassonneMain.cityCount

                for j in range(len(sides)):
                    if sides[j] == 0:
                        xy = [tx, y - 1]
                    elif sides[j] == 1:
                        xy = [tx + 1, y]
                    elif sides[j] == 2:
                        xy = [tx, y + 1]
                    elif sides[j] == 3:
                        xy = [tx - 1, y]
                    else:
                        xy = [0, 0]
                    board.board[x][y].completion[sides[j]] = CarcassonneMain.cityCount
                    self.sweepCityMeeples(xy[0], xy[1], board, (sides[j] + 2) % 4)

                CarcassonneMain.cityCount += 1

            elif type == 0:
                pass
            else:
                pass

    def sweepRoadMeeples(self, x, y, board, previousSide):
        i = -1
        board.board[x][y].completion[previousSide] = 1

        if board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1:
            board.players[board.board[x][y].meeple[0]].meepleCount += 1
            board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]

        for j in range(3):
            if previousSide <= j:
                if board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j + 1] == 1:
                    i = j + 1
                    break
            elif board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j] == 1:
                i = j
                break

        if board.board[x][y] not in self.tiletracker:
            self.tiletracker.append(board.board[x][y])

        if i == -1:
            return

        board.board[x][y].completion[i] = 1

        if i == 0:
            xy = [x, y - 1]
        elif i == 1:
            xy = [x + 1, y]
        elif i == 2:
            xy = [x, y + 1]
        elif i == 3:
            xy = [x - 1, y]
        else:
            xy = [0, 0]

        if board.board[xy[0]][xy[1]].types[0] == -1:
            self.tiletracker.append(EmptyTile())

        if x == self.x and y == self.y:
            return

        self.sweepRoadMeeples(xy[0], xy[1], board, (i + 2) % 4)

    def roadScore(self, x, y, board, meeplesPresent, previousSide):
        i = -1

        if board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1:
            meeplesPresent[board.board[x][y].meeple[0]] = 1

        for j in range(3):
            if previousSide <= j:
                if board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j + 1] == 1:
                    i = j + 1
                    break
            elif board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j] == 1:
                i = j
                break

        if board.board[x][y] not in self.tiletracker:
            self.tiletracker.append(board.board[x][y])

        if i == -1:
            return

        if x == self.x and y == self.y:
            return

        self.roadScore(x, y, board, meeplesPresent, (i + 2) % 4)

    def sweepCityMeeples(self, x, y, board, previousSide):
        board.board[x][y].completion[previousSide] = CarcassonneMain.cityCount

        if board.board[x][y].meeple[previousSide * 3 + 1 + 2] == 1:
            board.players[board.board[x][y].meeple[0]].meepleCount += 1
            board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]

        if board.board[x][y] not in self.tiletracker:
            self.tiletracker.append(board.board[x][y])
        else:
            return

        sides = []

        for j in range(3):
            if previousSide <= j:
                if board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j + 1] == 2:
                    sides.append(j + 1)
                    break
            elif board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j] == 2:
                sides.append(j)
                break

        if len(sides) == 0:
            return

        for i in range(len(sides)):
            checkedSides[sides[i]] = 1
            if sides[i] == 0:
                xy = [tx, y - 1]
            elif sides[i] == 1:
                xy = [tx + 1, y]
            elif sides[i] == 2:
                xy = [tx, y + 1]
            elif sides[i] == 3:
                xy = [tx - 1, y]
            else:
                xy = [0, 0]
            self.cityScore(xy[0], xy[1], board, meeplesPresent, (sides[i] + 2) % 4)

    def cityScore(self, x, y, board, meeplesPresent, previousSide):
        if board.board[x][y] not in self.tiletracker:
            self.tiletracker.append(board.board[x][y])
        else:
            return

        sides = []

        for j in range(3):
            if previousSide <= j:
                if board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j + 1] == 2:
                    sides.append(j + 1)
                    break
            elif board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j] == 2:
                sides.append(j)
                break

        if len(sides) == 0:
            return

        for i in range(len(sides)):
            if sides[i] == 0:
                xy = [x, y - 1]
            elif sides[i] == 1:
                xy = [x + 1, y]
            elif sides[i] == 2:
                xy = [x, y + 1]
            elif sides[i] == 3:
                xy = [x - 1, y]
            else:
                xy = [0, 0]
            self.cityScore(xy[0], xy[1], board, meeplesPresent, (sides[i] + 2) % 4)

    def endRoadScore(self, x, y, board):
        checkedSides = [0, 0, 0, 0]
        xy = []
        xyn = []
        for i in range(4):
            tx = x
            ty = y
            type = board.board[tx][ty].types[i]
            self.tiletracker = []

            if checkedSides[i] == 1:
                continue

            if type == 1 and board.board[x][y].meeple[i * 3 + 3] == 1:
                if i == 0:
                    xy = [tx, ty - 1]
                elif i == 1:
                    xy = [tx + 1, ty]
                elif i == 2:
                    xy = [tx, ty + 1]
                elif i == 3:
                    xy = [tx - 1, ty]
                else:
                    xy = [0, 0]

                if board.board[x][y] not in self.tiletracker:
                    self.tiletracker.append(board.board[x][y])

                self.roadScoreEnd(xy[0], xy[1], board, (i + 2) % 4)
                s = -1
                for j in range(3):
                    if i <= j:
                        if board.board[x][y].connected[i][j] and board.board[x][y].types[j + 1] == 1:
                            s = j + 1
                            break
                    elif board.board[x][y].connected[i][j] and board.board[x][y].types[j] == 1:
                        s = j
                        break

                if s != -1:
                    if s == 0:
                        xyn = [tx, ty - 1]
                    elif s == 1:
                        xyn = [tx + 1, ty]
                    elif s == 2:
                        xyn = [tx, ty + 1]
                    elif s == 3:
                        xyn = [tx - 1, ty]
                    else:
                        xyn = [0, 0]

                    self.roadScoreEnd(xyn[0], xyn[1], board, (s + 2) % 4)
                    checkedSides[s] = 1

                for k in range(len(self.tiletracker)):
                    if self.tiletracker[k].types[0] == -1:
                        self.tiletracker.remove(k)
                        k -= 1

                board.players[board.board[x][y].meeple[0]].score += len(self.tiletracker)
                self.tiletracker = []

                if board.board[tx][ty].meeple[i * 3 + 1 + 2] == 1:
                    board.players[board.board[x][y].meeple[0]].meepleCount += 1
                    board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1]

    def roadScoreEnd(self, x, y, board, previousSide):
        i = -1

        for j in range(3):
            if previousSide <= j:
                if board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j + 1] == 1:
                    i = j + 1
                    break
            elif board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j] == 1:
                i = j
                break

        if board.board[x][y] not in self.tiletracker:
            self.tiletracker.append(board.board[x][y])

        if i == -1:
            return

        if x == self.x and y == self.y:
            return

        self.roadScoreEnd(x, y, board, (i + 2) % 4)

    def endCityScore(self, x, y, board):
        checkedSides = [0, 0, 0, 0]
        xy = []
        for i in range(4):
            tx = x
            ty = y
            type = board.board[tx][ty].types[i]
            self.tiletracker = []

            if checkedSides[i] == 1:
                continue

            if type == 2 and board.board[x][y].meeple[i * 3 + 3] == 1:
                self.tiletracker.append(board.board[x][y])

                sides = [i]

                for j in range(3):
                    if i > j:
                        if board.board[x][y].connected[i][j] and board.board[x][y].types[j] == 2:
                            sides.append(j)
                    elif board.board[x][y].connected[i][j] and board.board[x][y].types[j + 1] == 2:
                        sides.append(j + 1)

                for j in range(len(sides)):
                    checkedSides[sides[j]] = 1
                    if sides[j] == 0:
                        xy = [tx, y - 1]
                    elif sides[j] == 1:
                        xy = [tx + 1, y]
                    elif sides[j] == 2:
                        xy = [tx, y + 1]
                    elif sides[j] == 3:
                        xy = [tx - 1, y]
                    else:
                        xy = [0, 0]
                    self.cityScoreEnd(xy[0], xy[1], board, (sides[j] + 2) % 4)

                for k in range(len(self.tiletracker)):
                    if self.tiletracker[k].types[0] == -1:
                        self.tiletracker.remove(k)
                        k -= 1

                board.players[board.board[x][y].meeple[0]].score += len(self.tiletracker)
                self.tiletracker = []

    def cityScoreEnd(self, x, y, board, previousSide):
        if board.board[x][y] not in self.tiletracker:
            self.tiletracker.append(board.board[x][y])
        else:
            return

        sides = []

        for j in range(3):
            if previousSide <= j:
                if board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j + 1] == 2:
                    sides.append(j + 1)
                    break
            elif board.board[x][y].connected[previousSide][j] and board.board[x][y].types[j] == 2:
                sides.append(j)
                break

        if len(sides) == 0:
            return

        for i in range(len(sides)):
            if sides[i] == 0:
                xy = [x, y - 1]
            elif sides[i] == 1:
                xy = [x + 1, y]
            elif sides[i] == 2:
                xy = [x, y + 1]
            elif sides[i] == 3:
                xy = [x - 1, y]
            else:
                xy = [0, 0]
            self.cityScoreEnd(xy[0], xy[1], board, (sides[i] + 2) % 4)

    def farmScore(self, x, y, board):
        visited = {}
        cities = [0] * CarcassonneMain.cityCount
        for i in range(len(board.board)):
            for j in range(len(board.board[i])):
                visited[i * 1000 + j] = [0] * 12

        self.farmScoreLoop(x, y, board, visited, board.board[x][y].meeple[14], cities)

        for i in range(len(cities)):
            if cities[i] == 1:
                board.players[board.board[x][y].meeple[0]].score += 3

    def farmScoreLoop(self, x, y, board, visited, pos, cities):
        if x < 0 or y < 0 or x >= len(board.board) or y >= len(board.board[0]):
            return

        if board.board[x][y].types[0] == -1:
            return

        if visited[x * 1000 + y][pos] == 1:
            return

        visited[x * 1000 + y][pos] = 1

        for i in range(4):
            if board.board[x][y].types[i] == 2 and self.fieldCityConnected(board.board[x][y], pos, i) and board.board[x][y].completion[i] != 0:
                cities[board.board[x][y].completion[i]] = 1

        connecteds = board.board[x][y].connects(pos)
        for i in range(len(connecteds)):
            if connecteds[i] == 1:
                newSide = self.oppositeSide(i)
                if i // 3 == 0:
                    xy = [x, y - 1]
                elif i // 3 == 1:
                    xy = [x + 1, y]
                elif i // 3 == 2:
                    xy = [x, y + 1]
                elif i // 3 == 3:
                    xy = [x - 1, y]
                else:
                    xy = [0, 0]
                self.farmScoreLoop(xy[0], xy[1], board, visited, newSide, cities)

    @staticmethod
    def fieldCityConnected(tile, fieldPos, citySide):
        connecteds = tile.connects(fieldPos)
        for i in range(len(connecteds)):
            if connecteds[i] == 1 and (i + 1) % 3 == 0:
                if i // 3 < citySide:
                    if tile.connected[i // 3][citySide - 1]:
                        return True

        if abs((citySide * 3 + 1) - fieldPos) == 2:
            return True

        if fieldPos == 0 and citySide == 3:
            return True

        return False

    @staticmethod
    def oppositeSide(x):
        if x in [0, 1, 2, 6, 7, 8]:
            return 8 - x
        elif x in [3, 4, 5, 9, 10, 11]:
            return 14 - x
        else:
            return -69420


