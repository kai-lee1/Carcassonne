import tkinter as tk
from tkinter import ttk
from GUI import *
from Board import *
import time
import random
import tkinter as tk
from PIL import Image, ImageTk
from tkinter import messagebox

# Frame initialization
player_count = 2
root = tk.Tk()
current_tile = None  # Assuming EmptyTile() is a placeholder for None or similar
inp = None  # Assuming Input() needs to be defined or replaced
ready = False
end_wait_time = 500  # in seconds
city_count = 1
end = False
board = None  # Assuming Board() needs to be defined

def main():
    global board
    board = Board(player_count)  # Assuming Board class is defined elsewhere
    root.geometry("500x133")
    root.state('zoomed')
    root.title("Carcassonne")
    GUI.gui(board)  # Assuming GUI.gui() is replaced by a function gui()
    # Assuming GUI.sp is a scrollable pane, need to set scroll positions
    draw_gui(board)
    board.game_time(board)  # Assuming this method is defined in Board

def draw_gui(board):
    global root
    for widget in root.winfo_children():
        widget.destroy()
    pos = [64 * 222, 64 * 222]  # Assuming previous scroll positions
    table = gui(board)  # Assuming GUI.gui() is replaced by a function gui()
    input_widget = inp.draw()  # Assuming inp.draw() is defined
    split_pane = ttk.PanedWindow(root, orient='horizontal')
    split_pane.add(table)
    split_pane.add(input_widget)
    split_pane.pack(fill=tk.BOTH, expand=True)
    root.update()
    root.geometry(f"{root.winfo_screenwidth()}x{root.winfo_screenheight()}")
    root.after_idle(lambda: set_scroll_position(pos))
    root.mainloop()

def set_scroll_position(pos):
    # Assuming GUI.sp is a scrollable pane, need to set scroll positions
    pass

def wait_button():
    global ready, end
    draw_gui(board)
    start_time = time.time()
    while time.time() - start_time < end_wait_time and not ready:
        if ready or end:
            break
        else:
            time.sleep(0.1)
    ready = False

if __name__ == "__main__":
    main()


class Board:
    def __init__(self, player_count):
        self.turn_count = 0
        self.board = [[EmptyTile() for _ in range(133)] for _ in range(133)]  # the game board
        self.extended = [0, 0, 0, 0]  # -x x -y y
        self.players = [Player() for _ in range(player_count)]  # playercount is players.size
        self.tiles = []  # draw pile
        self.field_meeples = []
        self.generate_board()
        self.generate_tiles()

    def game_time(self, board):
        while self.turn_count < 66:
            turn = self.turn_count % len(self.players)
            CarcassonneMain.current_tile = board.tiles[board.turn_count]
            CarcassonneMain.wait_button()
            if CarcassonneMain.end:
                break
            self.players[turn].place_tile(board)
            self.turn_count += 1
        self.end_game(board)
        CarcassonneMain.draw_gui(board)

    def end_game(self, board):
        for i in range(len(board.board)):
            for j in range(len(board.board[i])):
                current = board.board[i][j]
                if current.types[0] != -1:  # if current is not empty and has meeple
                    if current.meeple[1] == 0:  # if meeple is on farm
                        s = Scorer(board, i, j)
                        s.farm_score(i, j, board)
                    if current.meeple[1] == 2:  # if meeple is on city
                        s = Scorer(board, i, j)
                        s.end_city_score(i, j, board)
                    if current.meeple[1] == 1:  # if meeple is on road
                        s = Scorer(board, i, j)
                        s.end_road_score(i, j, board)

    def generate_board(self):
        for i in range(133):
            for j in range(133):
                self.board[i][j] = EmptyTile()
        self.board[66][66] = Tile("edgecityroadstraight")

    def other_side(self, side):
        return {
            0: 2,
            1: 3,
            2: 0,
            3: 1
        }.get(side, -1000000)  # idk

    def valid_side(self, tile1, tile2, side):
        if tile2.types[self.other_side(side)] == -1:
            return True
        return tile2.types[self.other_side(side)] == tile1.types[side]

    def generate_tiles(self):
        self.tiles.extend([Tile("cornercity")])
        self.tiles.extend([Tile("bowtiefield")])
        self.tiles.extend([Tile("1edgecity")])
        self.tiles.extend([Tile("edgecityroadt") for _ in range(3)])
        self.tiles.extend([Tile("Lroad")])
        self.tiles.extend([Tile("Troad") for _ in range(4)])
        self.tiles.extend([Tile("bowtiefield") for _ in range(2)])
        self.tiles.extend([Tile("Lroad") for _ in range(8)])
        self.tiles.extend([Tile("edgecityroadstraight") for _ in range(3)])
        self.tiles.extend([Tile("allcity")])
        self.tiles.extend([Tile("1not") for _ in range(4)])
        self.tiles.extend([Tile("1road") for _ in range(3)])
        self.tiles.extend([Tile("cornercity") for _ in range(4)])
        self.tiles.extend([Tile("cornercityroad") for _ in range(5)])
        self.tiles.extend([Tile("bowtiecity") for _ in range(3)])
        self.tiles.extend([Tile("2adjacentcity") for _ in range(2)])
        self.tiles.extend([Tile("1edgecity") for _ in range(4)])
        self.tiles.extend([Tile("edgecityroadleft") for _ in range(3)])
        self.tiles.extend([Tile("edgecityroadright") for _ in range(3)])
        self.tiles.extend([Tile("straightroad") for _ in range(8)])
        self.tiles.extend([Tile("+road")])
        random.shuffle(self.tiles)

class EmptyTile:
    pass

class Tile:
    def __init__(self, tile_type):
        self.tile_type = tile_type

class Player:
    def place_tile(self, board):
        pass

class Scorer:
    def __init__(self, board, x, y):
        self.board = board
        self.x = x
        self.y = y

    def farm_score(self, x, y, board):
        pass

    def end_city_score(self, x, y, board):
        pass

    def end_road_score(self, x, y, board):
        pass

class CarcassonneMain:
    current_tile = None
    end = False

    @staticmethod
    def wait_button():
        pass

    @staticmethod
    def draw_gui(board):
        pass

# This Python code translates the provided Java code for the `Board` class and related classes. Here are the key points:

# 1. The `Board` class is defined with similar attributes and methods as the Java version.
# 2. The `__init__` method initializes the board, players, tiles, and other attributes.
# 3. The `game_time` method handles the game loop and player turns.
# 4. The `end_game` method scores the final game state.
# 5. The `generate_board` method initializes the game board with an empty tile and a starting tile.
# 6. The `other_side` and `valid_side` methods are helper functions for tile placement.
# 7. The `generate_tiles` method creates the tile deck with the specified tile types.
# 8. The `EmptyTile`, `Tile`, `Player`, `Scorer`, and `CarcassonneMain` classes are defined as placeholders for the corresponding Java classes.

# Note that some methods like `endGameField`, `sidescreator`, and `meeplesidescreator` are not included in the Python translation as they were commented out in the provided Java code.

class GUI:
    def __init__(self, board):
        self.board = board
        self.data = board.board
        self.strdata = [[None for _ in range(133)] for _ in range(133)]
        for i in range(133):
            for k in range(133):
                self.strdata[k][i] = self.data[i][k]

        self.root = tk.Tk()
        self.root.title("Carcassonne")

        self.canvas = tk.Canvas(self.root, width=2952, height=2952)
        self.canvas.pack()

        self.images = self.load_images()
        self.draw_board()

        self.root.mainloop()

    def load_images(self):
        images = {}
        images["blank"] = ImageTk.PhotoImage(Image.open("res/emptytile.png").resize((222, 222)))
        images["plusroad"] = ImageTk.PhotoImage(Image.open("res/+road.png").resize((222, 222)))
        images["oneedgecity"] = ImageTk.PhotoImage(Image.open("res/1edgecity.png").resize((222, 222)))
        images["onenot"] = ImageTk.PhotoImage(Image.open("res/1not.png").resize((222, 222)))
        images["oneroad"] = ImageTk.PhotoImage(Image.open("res/1road.png").resize((222, 222)))
        images["twoadjcity"] = ImageTk.PhotoImage(Image.open("res/2adjacentcity.png").resize((222, 222)))
        images["allcity"] = ImageTk.PhotoImage(Image.open("res/allcity.png").resize((222, 222)))
        images["bowtiecity"] = ImageTk.PhotoImage(Image.open("res/bowtiecity.png").resize((222, 222)))
        images["bowtiefield"] = ImageTk.PhotoImage(Image.open("res/bowtiefield.png").resize((222, 222)))
        images["cornercity"] = ImageTk.PhotoImage(Image.open("res/cornercity.png").resize((222, 222)))
        images["cornercityroad"] = ImageTk.PhotoImage(Image.open("res/cornercityroad.png").resize((222, 222)))
        images["edgecityroadleft"] = ImageTk.PhotoImage(Image.open("res/edgecityroadleft.png").resize((222, 222)))
        images["edgecityroadright"] = ImageTk.PhotoImage(Image.open("res/edgecityroadright.png").resize((222, 222)))
        images["edgecityroadstraight"] = ImageTk.PhotoImage(Image.open("res/edgecityroadstraight.png").resize((222, 222)))
        images["edgecityroadt"] = ImageTk.PhotoImage(Image.open("res/edgecityroadt.png").resize((222, 222)))
        images["lroad"] = ImageTk.PhotoImage(Image.open("res/Lroad.png").resize((222, 222)))
        images["straightroad"] = ImageTk.PhotoImage(Image.open("res/straightroad.png").resize((222, 222)))
        images["troad"] = ImageTk.PhotoImage(Image.open("res/Troad.png").resize((222, 222)))
        images["meeple"] = ImageTk.PhotoImage(Image.open("res/meeple.png").resize((30, 30)))
        images["meepley"] = ImageTk.PhotoImage(Image.open("res/meepleyellow.png").resize((30, 30)))
        images["meepleg"] = ImageTk.PhotoImage(Image.open("res/meeplegreen.png").resize((30, 30)))
        return images

    def draw_board(self):
        for row in range(133):
            for col in range(133):
                tile = self.strdata[col][row]
                if tile is None or tile.type == "":
                    image = self.images["blank"]
                else:
                    image = self.images[tile.type]
                    image = image.rotate(tile.rotations * 90, expand=True)
                    if tile.meeple[14] != -1:
                        meeple_pos = self.meeple_pos(tile.meeple[14])
                        if tile.meeple[0] == 0:
                            meeple_image = self.images["meeple"]
                        elif tile.meeple[0] == 1:
                            meeple_image = self.images["meepley"]
                        else:
                            meeple_image = self.images["meepleg"]
                        image.paste(meeple_image, meeple_pos, meeple_image)
                self.canvas.create_image(col * 222, row * 222, anchor=tk.NW, image=image)

    def meeple_pos(self, pos):
        positions = {
            0: (42, 14), 1: (110, 14), 2: (178, 14), 3: (206, 42),
            4: (206, 110), 5: (206, 178), 6: (178, 206), 7: (110, 206),
            8: (42, 206), 9: (14, 178), 10: (14, 110), 11: (14, 42)
        }
        x, y = positions.get(pos, (0, 0))
        return (x - 14, y - 14)

if __name__ == "__main__":
    board = None  # Replace with your Board object
    gui = GUI(board)

# This Python code creates a graphical user interface (GUI) using the Tkinter library to display the Carcassonne board. It loads the tile images from the specified file paths and creates a canvas to draw the board. The `GUI` class has methods to load the images, draw the board, and position the meeple pieces on the tiles.

# Note that you need to have the Pillow library installed (`pip install pillow`) to use the `Image` and `ImageTk` modules for image handling. Additionally, make sure that the image files are present in the specified paths (`res/` directory).

# To use this code, you need to create an instance of the `Board` class and pass it to the `GUI` constructor. The `Board` class is not provided in the given code, so you'll need to implement it separately.

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

class Input:
    def __init__(self, master):
        self.master = master
        self.master.geometry("400x500")
        self.master.title("Input")
        self.score = [""] * CarcassonneMain.player_count
        self.main_panel = tk.Frame(master)
        self.main_panel.pack(fill=tk.BOTH, expand=True)
        
        self.coordx = tk.Entry(self.main_panel, width=40)
        self.coordy = tk.Entry(self.main_panel, width=40)
        self.rots = tk.Entry(self.main_panel, width=40)
        self.meeple = tk.Entry(self.main_panel, width=40)
        
        self.coordx_label = tk.Label(self.main_panel, text="X Coordinate:")
        self.coordy_label = tk.Label(self.main_panel, text="Y Coordinate:")
        self.rots_label = tk.Label(self.main_panel, text="Rotations (Clockwise):")
        self.meeple_label = tk.Label(self.main_panel, text="Meeple:")
        
        self.confirm_button = tk.Button(self.main_panel, text="Confirm", command=self.button_press)
        self.endgame_button = tk.Button(self.main_panel, text="End the game", command=self.end_game_press)
        
        self.tile_label = tk.Label(self.main_panel, text=f"Current Tile: {CarcassonneMain.current_tile.type}")
        self.error_label = tk.Label(self.main_panel, text="", fg="red")
        self.score_label = tk.Label(self.main_panel, text="Player Scores:")
        
        self.layout_widgets()
        
    def layout_widgets(self):
        widgets = [self.tile_label, self.coordx_label, self.coordx, self.coordy_label, self.coordy,
                   self.rots_label, self.rots, self.meeple_label, self.meeple, self.confirm_button,
                   self.endgame_button, self.error_label, self.score_label]
        for widget in widgets:
            widget.pack()
        for i, score in enumerate(self.score):
            tk.Label(self.main_panel, text=score).pack()

    def button_press(self):
        CarcassonneMain.ready = True

    def end_game_press(self):
        CarcassonneMain.end = True

class CarcassonneMain:
    player_count = 4
    current_tile = type('Tile', (object,), {'type': 'Castle', 'rotations': 0})
    ready = False
    end = False

    def __init__(self):
        self.root = tk.Tk()
        self.app = Input(self.root)
        self.root.mainloop()

if __name__ == "__main__":
    app = CarcassonneMain()


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
