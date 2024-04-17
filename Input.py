import tkinter as tk
from tkinter import messagebox
import CarcassonneMain, GUI, Input, Player, Scorer, Tile, EmptyTile

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


