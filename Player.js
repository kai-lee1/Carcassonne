class Player {
    constructor() {
        this.meepleCount = 7;
        this.score = 0;
    }

    placeTile(board) {
        let x = 0;
        try { x = parseInt(CarcassonneMain.inp.coordx.getText()); }
        catch (e) {
            CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates");
            CarcassonneMain.waitButton();
            this.placeTile(board);
            return;
        }
        let y = 0;
        try { y = parseInt(CarcassonneMain.inp.coordy.getText()); }
        catch (e) {
            CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates");
            CarcassonneMain.waitButton();
            this.placeTile(board);
            return;
        }
        let rotateans = 0;
        try { rotateans = parseInt(CarcassonneMain.inp.rots.getText()); }
        catch (e) {
            CarcassonneMain.inp.error.setText("Invalid rotations. Try new number");
            CarcassonneMain.waitButton();
            this.placeTile(board);
            return;
        }
        this.rotateTile(board.turnCount, rotateans, board);
        for (let j = 0; j < 4; j++) {
            // console.log(board.tiles.get(board.turnCount).types[j]);
        }
        if (x > 0 && y > 0 && (board.validSide(board.tiles.get(board.turnCount), board.board[x][y - 1], 0))
            && (board.validSide(board.tiles.get(board.turnCount), board.board[x + 1][y], 1))
            && (board.validSide(board.tiles.get(board.turnCount), board.board[x][y + 1], 2))
            && (board.validSide(board.tiles.get(board.turnCount), board.board[x - 1][y], 3))
            && (board.board[x][y].types[0] == -1)
            && ((board.board[x + 1][y].types[0] != -1) || (board.board[x][y + 1].types[0] != -1)
                || (board.board[x - 1][y].types[0] != -1) || (board.board[x][y - 1].types[0] != -1))) {

            board.board[x][y] = board.tiles.get(board.turnCount);

            let worked = false;
            let position;
            try {
                position = parseInt(CarcassonneMain.inp.meeple.getText());
            } catch (e) {
                position = -1;
            }

            if (position != -1)
                worked = this.placeMeeple(position, x, y, board);
            else
                worked = true;

            while (!worked) {
                CarcassonneMain.inp.error.setText("invalid meeple position. Choose -1 to not place one.");
                CarcassonneMain.waitButton();
                if (parseInt(CarcassonneMain.inp.meeple.getText()) != -1) {
                    try {
                        position = parseInt(CarcassonneMain.inp.meeple.getText());
                    } catch (e) {
                        position = -1;
                    }
                    if (position != -1)
                        worked = this.placeMeeple(position, x, y, board);
                    else
                        worked = true;
                } else {
                    break;
                }
            }
            if (worked) {
                this.meepleCount--;
            }
            const scorer = new Scorer(board, x, y);
            scorer.scoring(x, y, board);
        } else {
            CarcassonneMain.inp.error.setText("Invalid tile. Try new coordinates");
            CarcassonneMain.waitButton();
            this.placeTile(board);
        }
    }

    placeMeeple(position, x, y, board) {
        const tile = board.board[x][y];
        const turn = board.turnCount % board.players.length;
        tile.meeple[0] = turn;
        tile.meeple[14] = position;

        switch (position % 3) {
            case 1:
                tile.meeple[1] = board.board[x][y].types[Math.floor(position / 3)];
                if ((tile.types[(position) / 3] == 2)) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[position + 1] = 1;
                    tile.meeple[position + 3] = 1;
                    for (let j = 0; j < 3; j++) {
                        if (position / 3 <= j) {
                            if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 2) {
                                tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
                                tile.meeple[(j + 1) * 3 + 2] = 1;
                                tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
                            }
                        } else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 2) {
                            tile.meeple[j * 3 + 2 + 1] = 1;
                            tile.meeple[j * 3 + 2] = 1;
                            tile.meeple[j * 3 + 2 + 2] = 1;
                        }
                    }
                } else if ((tile.types[(position) / 3] == 1)) {
                    tile.meeple[position + 2] = 1;
                    for (let j = 0; j < 3; j++) {
                        if (position / 3 <= j) {
                            if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 1) {
                                tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
                                break;
                            }
                        } else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 1) {
                            tile.meeple[j * 3 + 2 + 1] = 1;
                            break;
                        }
                    }
                } else {
                    board.fieldMeeples.push([x, y]);
                    tile.meeple[position + 2] = 1;
                    tile.meeple[position + 1] = 1;
                    tile.meeple[position + 3] = 1;
                    for (let j = 0; j < 3; j++) {
                        if (position / 3 <= j) {
                            if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j + 1] == 0) {
                                tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
                                tile.meeple[(j + 1) * 3 + 2] = 1;
                                tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
                            }
                        } else if (board.board[x][y].connected[position / 3][j] && board.board[x][y].types[j] == 0) {
                            tile.meeple[j * 3 + 2 + 1] = 1;
                            tile.meeple[j * 3 + 2] = 1;
                            tile.meeple[j * 3 + 2 + 2] = 1;
                        }
                    }
                    if (tile.types[(position / 3 + 1) % 4] == 0) {
                        tile.meeple[(position + 2) % 12 + 2] = 1;
                    }
                    if (tile.types[(position / 3 + 3) % 4] == 0) {
                        tile.meeple[(position - 2) % 12 + 2] = 1;
                    }

                    if (tile.type == "edgecityroadleft") {
                        tile.meeple[(position + 7) % 12 + 2] = 1;
                    } else if (tile.type == "edgecityroadright") {
                        tile.meeple[(position + 5) % 12 + 2] = 1;
                    }
                }
                break;
            case 0:
                if (tile.types[(position) / 3] != 1 || tile.types[((position) / 3 - 1) % 3] == 0) {
                    tile.meeple[0] = -1;
                    tile.meeple[14] = -1;
                    return false;
                }
                board.fieldMeeples.push([x, y]);
                if (tile.types[((position) / 3 - 1) % 3] == 1) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[(position - 1) % 12 + 2] = 1;
                } else {
                    tile.meeple[position + 2] = 1;
                    if (tile.type == "cornercityroad") {
                        tile.meeple[(position + 5) % 12 + 2] = 1;
                    } else if ((tile.type == "edgecityroadt") || (tile.type == "edgecityroadstraight")) {
                        tile.meeple[(position + 8) % 12 + 2] = 1;
                    }
                }
                tile.meeple[1] = 0;
                break;
            case 2:
                if (tile.types[(position) / 3] != 1 || tile.types[((position) / 3 + 1) % 3] == 0) {
                    tile.meeple[0] = -1;
                    tile.meeple[14] = -1;
                    return false;
                }
                board.fieldMeeples.push([x, y]);
                if (tile.types[((position) / 3 + 1) % 3] == 1) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[(position + 1) % 12 + 2] = 1;
                } else {
                    tile.meeple[position + 2] = 1;
                    if (tile.type == "cornercityroad") {
                        tile.meeple[(position + 7) % 12 + 2] = 1;
                    } else if ((tile.type == "edgecityroadt") || (tile.type == "edgecityroadstraight")) {
                        tile.meeple[(position + 4) % 12 + 2] = 1;
                    }
                }
                tile.meeple[1] = 0;
                break;
            default:
                return false;
        }
        return true;
    }

    rotateTile(turn, rotate, board) {
        let old;
        let oldcon;
        let oldcono;
        board.tiles.get(turn).rotations = board.tiles.get(turn).rotations + rotate;
        for (let i = 0; i < rotate; i++) {
            old = board.tiles.get(turn).types.slice();
            board.tiles.get(turn).types = [old[3], old[0], old[1], old[2]];

            for (let j = 0; j < 4; j++) {
                oldcon = board.tiles.get(turn).connected[j];
                if (j != 3)
                    board.tiles.get(turn).connected[j] = [oldcon[2], oldcon[0], oldcon[1]];
            }

            oldcono = board.tiles.get(turn).connected;
            board.tiles.get(turn).connected = [oldcono[3], oldcono[0], oldcono[1], oldcono[2]];
        }
    }
}


