from Tile import *
class EmptyTile(Tile):
    def __init__(self):
        super().__init__([0, 0, 0, 0], [[False, False, False] for _ in range(4)])
        self.types = [-1, -1, -1, -1]
        self.connected = [
            [False, False, False],
            [False, False, False],
            [False, False, False],
            [False, False, False]
        ]


