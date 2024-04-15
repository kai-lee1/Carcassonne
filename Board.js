
class Board {
    constructor(playerCount) {
      this.turnCount = 0;
      this.board = Array.from({ length: 133 }, () => Array.from({ length: 133 }, () => new EmptyTile()));
      this.board[66][66] = new Tile("edgecityroadstraight");
      this.extended = [0, 0, 0, 0];
      this.players = Array.from({ length: playerCount }, () => new Player());
      this.tiles = [];
      this.fieldMeeples = [];
      this.generateTiles();
    }
  
    gameTime(board) {
      while (this.turnCount < 66) {
        const turn = this.turnCount % this.players.length;
        CarcassonneMain.currentTile = this.tiles[this.turnCount];
        CarcassonneMain.waitButton();
        if (CarcassonneMain.end) {
          break;
        }
        this.players[turn].placeTile(board);
        this.turnCount += 1;
      }
      this.endGame(board);
      CarcassonneMain.drawGUI(board);
    }
  
    endGame(board) {
      for (let i = 0; i < board.board.length; i++) {
        for (let j = 0; j < board.board[i].length; j++) {
          const current = board.board[i][j];
          if (current.types[0] !== -1) {
            if (current.meeple[1] === 0) {
              const s = new Scorer(board, i, j);
              s.farmScore(i, j, board);
            }
            if (current.meeple[1] === 2) {
              const s = new Scorer(board, i, j);
              s.endCityScore(i, j, board);
            }
          }
          if (current.meeple[1] === 1) {
            const s = new Scorer(board, i, j);
            s.endRoadScore(i, j, board);
          }
        }
      }
    }
  
    generatePlayers(playerCount) {
      this.players = Array.from({ length: playerCount }, () => new Player());
    }
  
    generateBoard() {
      for (let i = 0; i < 133; i++) {
        for (let j = 0; j < 133; j++) {
          this.board[i][j] = new EmptyTile();
        }
      }
      this.board[66][66] = new Tile("edgecityroadstraight");
    }
  
    otherSide(side) {
      switch (side) {
        case 0:
          return 2;
        case 1:
          return 3;
        case 2:
          return 0;
        case 3:
          return 1;
        default:
          return -1000000;
      }
    }
  
    validSide(tile1, tile2, side) {
      if (tile2.types[this.otherSide(side)] === -1) {
        return true;
      }
      return tile2.types[this.otherSide(side)] === tile1.types[side];
    }
  
    generateTiles() {
      const tileTypes = [
        "cornercity",
        "bowtiefield",
        "1edgecity",
        "edgecityroadt",
        "Lroad",
        "Troad",
        "bowtiefield",
        "Lroad",
        "edgecityroadstraight",
        "allcity",
        "1not",
        "1road",
        "cornercity",
        "cornercityroad",
        "bowtiecity",
        "2adjacentcity",
        "1edgecity",
        "edgecityroadleft",
        "edgecityroadright",
        "straightroad",
        "+road"
      ];
  
      for (const type of tileTypes) {
        const count = type === "cornercity" ? 4 : type === "bowtiefield" ? 3 : type === "1edgecity" ? 4 : type === "edgecityroadt" ? 3 : type === "Lroad" ? 9 : type === "Troad" ? 4 : type === "1not" ? 4 : type === "1road" ? 3 : type === "cornercityroad" ? 5 : type === "bowtiecity" ? 3 : type === "2adjacentcity" ? 2 : type === "edgecityroadleft" ? 3 : type === "edgecityroadright" ? 3 : type === "straightroad" ? 8 : 1;
        for (let i = 0; i < count; i++) {
          this.tiles.push(new Tile(type));
        }
      }
  
      this.shuffleTiles();
    }
  
    shuffleTiles() {
      this.tiles = this.shuffleArray(this.tiles);
    }
  
    shuffleArray(array) {
      for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
      }
      return array;
    }
  }
  
  