class Tile {
    constructor(types, connected) {
        this.types = types;
        this.connected = connected;
        this.meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];
        this.rotations = 0;
        this.completion = [0, 0, 0, 0];
    }

    connects(pos1) {
        let newTile = this.copy();
        newTile.meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];

        if (this.placeMeeple(pos1, newTile)) {
            return newTile.meeple.slice(2, 14);
        }

        if (pos1 % 3 === 0) {
            if (pos1 === 0) {
                this.placeMeeple(10, newTile);
                return newTile.meeple.slice(2, 14);
            }
            this.placeMeeple(pos1 - 2, newTile);
            return newTile.meeple.slice(2, 14);
        }

        if (pos1 === 11) {
            this.placeMeeple(1, newTile);
            return newTile.meeple.slice(2, 14);
        }
        this.placeMeeple(pos1 + 2, newTile);
        return newTile.meeple.slice(2, 14);
    }

    placeMeeple(position, tile) {
        switch (position % 3) {
            case 1:
                tile.meeple[1] = tile.types[Math.floor(position / 3)];
                if (tile.types[Math.floor(position / 3)] === 2) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[position + 1] = 1;
                    tile.meeple[position + 3] = 1;
                    for (let j = 0; j < 3; j++) {
                        if (Math.floor(position / 3) < j) {
                            if (tile.connected[Math.floor(position / 3)][j] && tile.types[j + 1] === 2) {
                                tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
                                tile.meeple[(j + 1) * 3 + 2] = 1;
                                tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
                            }
                        } else if (tile.connected[Math.floor(position / 3)][j] && tile.types[j] === 2) {
                            tile.meeple[j * 3 + 2 + 1] = 1;
                            tile.meeple[j * 3 + 2] = 1;
                            tile.meeple[j * 3 + 2 + 2] = 1;
                        }
                    }
                } else if (tile.types[Math.floor(position / 3)] === 1) {
                    tile.meeple[position + 2] = 1;
                    for (let j = 0; j < 3; j++) {
                        if (Math.floor(position / 3) < j) {
                            if (tile.connected[Math.floor(position / 3)][j] && tile.types[j + 1] === 1) {
                                tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
                                break;
                            }
                        } else if (tile.connected[Math.floor(position / 3)][j] && tile.types[j] === 1) {
                            tile.meeple[j * 3 + 2 + 1] = 1;
                            break;
                        }
                    }
                } else {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[position + 1] = 1;
                    tile.meeple[position + 3] = 1;
                    for (let j = 0; j < 3; j++) {
                        if (Math.floor(position / 3) <= j) {
                            if (tile.connected[Math.floor(position / 3)][j] && tile.types[j + 1] === 0) {
                                tile.meeple[(j + 1) * 3 + 2 + 1] = 1;
                                tile.meeple[(j + 1) * 3 + 2] = 1;
                                tile.meeple[(j + 1) * 3 + 2 + 2] = 1;
                                if (tile.types[j] === 1) {
                                    tile.meeple[(j * 3 + 2 + 2) % 12] = 1;
                                }
                                if (tile.types[(j + 2) % 4] === 1) {
                                    tile.meeple[(((j + 2) % 4) * 3 + 2) % 12] = 1;
                                }
                            }
                        } else if (tile.connected[Math.floor(position / 3)][j] && tile.types[j] === 0) {
                            tile.meeple[j * 3 + 2 + 1] = 1;
                            tile.meeple[j * 3 + 2] = 1;
                            tile.meeple[j * 3 + 2 + 2] = 1;
                            if (tile.types[(j + 3) % 4] === 1) {
                                tile.meeple[(((j + 3) % 4) * 3 + 2 + 2) % 12] = 1;
                            }
                            if (tile.types[(j + 1) % 4] === 1) {
                                tile.meeple[((j + 1) * 3 + 2) % 12] = 1;
                            }
                        }
                    }
                    if (tile.types[(Math.floor(position / 3) + 1) % 4] === 1) {
                        tile.meeple[(position + 2) % 12 + 2] = 1;
                    }
                    if (tile.types[(Math.floor(position / 3) + 3) % 4] === 1) {
                        tile.meeple[(position + 10) % 12 + 2] = 1;
                    }

                    if (tile.type === "edgecityroadleft") {
                        tile.meeple[(position + 7) % 12 + 2] = 1;
                    } else if (tile.type === "edgecityroadright") {
                        tile.meeple[(position + 5) % 12 + 2] = 1;
                    }
                }
                break;
            case 0:
                if (tile.types[Math.floor(position / 3)] !== 1 || tile.types[((position) / 3 - 1) % 3] === 0) {
                    tile.meeple[0] = -1;
                    tile.meeple[14] = -1;
                    return false;
                }
                if (tile.types[((position) / 3 - 1) % 3] === 1) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[(position - 1) % 12 + 2] = 1;
                } else {
                    tile.meeple[position + 2] = 1;
                    if (tile.type === "cornercityroad") {
                        tile.meeple[(position + 5) % 12 + 2] = 1;
                    } else if ((tile.type === "edgecityroadt") || (tile.type === "edgecityroadstraight")) {
                        tile.meeple[(position + 8) % 12 + 2] = 1;
                    }
                }
                tile.meeple[1] = 0;
                break;
            case 2:
                if (tile.types[Math.floor(position / 3)] !== 1 || tile.types[((position) / 3 + 1) % 3] === 0) {
                    tile.meeple[0] = -1;
                    tile.meeple[14] = -1;
                    return false;
                }
                if (tile.types[((position) / 3 + 1) % 3] === 1) {
                    tile.meeple[position + 2] = 1;
                    tile.meeple[(position + 1) % 12 + 2] = 1;
                } else {
                    tile.meeple[position + 2] = 1;
                    if (tile.type === "cornercityroad") {
                        tile.meeple[(position + 7) % 12 + 2] = 1;
                    } else if ((tile.type === "edgecityroadt") || (tile.type === "edgecityroadstraight")) {
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

    copy() {
        let ret = new Tile([...this.types], [...this.connected]);
        ret.type = this.type;
        ret.meeple = [...this.meeple];
        ret.rotations = this.rotations;
        ret.completion = [...this.completion];
        return ret;
    }

    toString() {
        let ret = "";
        for (let j = 0; j < 4; j++) {
            ret += this.types[j];
        }
        for (let x of this.meeple) {
            if (x > 0) {
                ret += x;
                break;
            }
        }
        return ret;
    }
}


