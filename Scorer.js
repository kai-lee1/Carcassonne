class Scorer {
	constructor(board, x, y) {
		this.score = 0;
		this.tiletracker = [];
		this.board = board;
		this.x = x;
		this.y = y;
	}

	scoring(x, y, board) {
		const checkedSides = [0, 0, 0, 0];
		let meeplesPresent;
		let xy;
		let xyn;
		c: for (let i = 0; i < 4; i++) {
			let tx = x;
			let ty = y;
			const type = board.board[tx][ty].types[i];
			meeplesPresent = new Array(board.players.length).fill(0);
			this.tiletracker = [];

			if (checkedSides[i] === 1)
				continue c;

			switch (type) {

				case 1:
					switch (i) {
						case 0:
							xy = [tx, ty - 1];
							break;
						case 1:
							xy = [tx + 1, ty];
							break;
						case 2:
							xy = [tx, ty + 1];
							break;
						case 3:
							xy = [tx - 1, ty];
							break;
						default:
							xy = [0, 0];
							break;
					}
					if (board.board[tx][ty].meeple[i * 3 + 1 + 2] === 1) {
						meeplesPresent[board.board[tx][ty].meeple[0]] = 1;
					}
					if (!this.tiletracker.includes(board.board[x][y]))
						this.tiletracker.push(board.board[x][y]);

					this.roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4);
					let s = -1;
					for (let j = 0; j < 3; j++) {
						if (i <= j) {
							if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] === 1) {
								s = j + 1;
								break;
							}
						} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] === 1) {
							s = j;
							break;
						}

					}
					switch (s) {
						case 0:
							xyn = [tx, ty - 1];
							break;
						case 1:
							xyn = [tx + 1, ty];
							break;
						case 2:
							xyn = [tx, ty + 1];
							break;
						case 3:
							xyn = [tx - 1, ty];
							break;
						case -1:
							xyn = [xy[0], xy[1]];
						default:
							xyn = [0, 0];
							break;
					}
					if (s !== -1) {
						this.roadScore(xyn[0], xyn[1], board, meeplesPresent, (s + 2) % 4);
						checkedSides[s] = 1;
					}
					for (let k = 0; k < this.tiletracker.length; k++) {
						if (this.tiletracker[k].types[0] === -1)
							continue c;
					}
					for (let k = 0; k < meeplesPresent.length; k++) {
						if (meeplesPresent[k] === 1) {
							board.players[k].score += this.tiletracker.length;
						}
					}
					this.tiletracker = [];
					if (board.board[tx][ty].meeple[i * 3 + 1 + 2] === 1) {
						board.players[board.board[x][y].meeple[0]].meepleCount += 1;
						board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];
					}
					board.board[x][y].completion[i] = 1;
					this.sweepRoadMeeples(xy[0], xy[1], board, (i + 2) % 4);
					if (s !== -1) {
						board.board[x][y].completion[s] = 1;
						this.sweepRoadMeeples(xyn[0], xyn[1], board, (s + 2) % 4);
					}
					break;

				case 2:
					if (board.board[x][y].meeple[i * 3 + 2 + 1] === 1) {
						meeplesPresent[board.board[x][y].meeple[0]] = 1;
					}

					this.tiletracker.push(board.board[x][y]);

					const sides = [];

					sides.push(i);

					for (let j = 0; j < 3; j++) {
						if (i > j) {
							if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] === 2) {
								sides.push(j);
							}
						} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] === 2) {
							sides.push(j + 1);
						}
					}

					for (let j = 0; j < sides.length; j++) {
						checkedSides[sides[j]] = 1;
						switch (sides[j]) {
							case 0:
								xy = [tx, y - 1];
								break;
							case 1:
								xy = [tx + 1, y];
								break;
							case 2:
								xy = [tx, y + 1];
								break;
							case 3:
								xy = [tx - 1, y];
								break;
							default:
								xy = [0, 0];
						}
						this.cityScore(xy[0], xy[1], board, meeplesPresent, (sides[j] + 2) % 4);
					}

					for (let k = 0; k < this.tiletracker.length; k++) {
						if (this.tiletracker[k].types[0] === -1)
							continue c;
					}

					for (let k = 0; k < meeplesPresent.length; k++) {
						if (meeplesPresent[k] === 1) {
							board.players[k].score += this.tiletracker.length * 2;
							// removing meeples after city finishes scoring
						}
					}
					this.tiletracker = [];
					if (board.board[tx][ty].meeple[i * 3 + 1 + 2] === 1) {
						board.players[board.board[x][y].meeple[0]].meepleCount += 1;
						board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];
					}
					board.board[x][y].completion[i] = CarcassonneMain.cityCount;
					for (let j = 0; j < sides.length; j++) {
						switch (sides[j]) {
							case 0:
								xy = [tx, y - 1];
								break;
							case 1:
								xy = [tx + 1, y];
								break;
							case 2:
								xy = [tx, y + 1];
								break;
							case 3:
								xy = [tx - 1, y];
								break;
							default:
								xy = [0, 0];
						}
						board.board[x][y].completion[sides[j]] = CarcassonneMain.cityCount;
						this.sweepCityMeeples(xy[0], xy[1], board, (sides[j] + 2) % 4);
					}
					CarcassonneMain.cityCount++;
					break;

				case 0:
					break;
				default:
					break;
			}
		}

	}

	sweepRoadMeeples(x, y, board, previousSide) {
		let i = -1;
		board.board[x][y].completion[previousSide] = 1;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] === 1) {

			board.players[board.board[x][y].meeple[0]].meepleCount += 1;
			board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];
		}

		for (let j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] === 1) {
					i = j + 1;
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] === 1) {
				i = j;
				break;
			}
		}

		if (!this.tiletracker.includes(board.board[x][y]))
			this.tiletracker.push(board.board[x][y]);

		if (i === -1) { 
			return;
		}

		board.board[x][y].completion[i] = 1;


		let xy;
		switch (i) {
			case 0:
				xy = [x, y - 1];
				break;
			case 1:
				xy = [x + 1, y];
				break;
			case 2:
				xy = [x, y + 1];
				break;
			case 3:
				xy = [x - 1, y];
				break;
			default:
				xy = [0, 0];
				break;
		}

		if (board.board[xy[0]][xy[1]].types[0] === -1) {
			this.tiletracker.push(new EmptyTile());
		}

		if (x === this.x && y === this.y) { 
			return;
		}

		this.sweepRoadMeeples(xy[0], xy[1], board, (i + 2) % 4);
	}

	roadScore(x, y, board, meeplesPresent, previousSide) {
		let i = -1;

		for (let j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] === 1) {
					i = j + 1;
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] === 1) {
				i = j;
				break;
			}
		}

		if (!this.tiletracker.includes(board.board[x][y]))
			this.tiletracker.push(board.board[x][y]);

		if (i === -1) {
			return;
		}


		let xy;
		switch (i) {
			case 0:
				xy = [x, y - 1];
				break;
			case 1:
				xy = [x + 1, y];
				break;
			case 2:
				xy = [x, y + 1];
				break;
			case 3:
				xy = [x - 1, y];
				break;
			default:
				xy = [0, 0];
				break;
		}

		if (board.board[xy[0]][xy[1]].types[0] === -1) { 
			this.tiletracker.push(new EmptyTile());
		}

		if (x === this.x && y === this.y) { 
			return;
		}

		this.roadScore(xy[0], xy[1], board, meeplesPresent, (i + 2) % 4);

	}

	sweepCityMeeples(x, y, board, previousSide) {
		board.board[x][y].completion[previousSide] = CarcassonneMain.cityCount;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] === 1) {
			board.players[board.board[x][y].meeple[0]].meepleCount += 1;
			board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];
		}

		if (!this.tiletracker.includes(board.board[x][y]))
			this.tiletracker.push(board.board[x][y]);
		else
			return;

		const sides = [];

		for (let j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] === 2) {
					sides.push(j + 1);
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] === 2) {
				sides.push(j);
				break;
			}
		}

		if (sides.length === 0)
			return;


		let xy;
		for (let i = 0; i < sides.length; i++) {
			const j = sides[i];
			switch (j) {
				case 0:
					xy = [x, y - 1];
					break;
				case 1:
					xy = [x + 1, y];
					break;
				case 2:
					xy = [x, y + 1];
					break;
				case 3:
					xy = [x - 1, y];
					break;
				default:
					xy = [0, 0];
					break;
			}
			if (board.board[xy[0]][xy[1]].types[0] === -1) { 
				this.tiletracker.push(new EmptyTile());
				return;
			}
			board.board[x][y].completion[j] = CarcassonneMain.cityCount;
			this.sweepCityMeeples(xy[0], xy[1], board, (j + 2) % 4);
		}
	}

	cityScore(x, y, board, meeplesPresent, previousSide) {

		if (!this.tiletracker.includes(board.board[x][y]))
			this.tiletracker.push(board.board[x][y]);

		else
			return;

		if (board.board[x][y].meeple[previousSide * 3 + 1 + 2] === 1) {

			meeplesPresent[board.board[x][y].meeple[0]] = 1;
		}

		const sides = [];

		for (let j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] === 2) {
					sides.push(j + 1);
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] === 2) {
				sides.push(j);
				break;
			}
		}

		if (sides.length === 0)
			return;


		let xy;
		for (let i = 0; i < sides.length; i++) {
			const j = sides[i];
			switch (j) {
				case 0:
					xy = [x, y - 1];
					break;
				case 1:
					xy = [x + 1, y];
					break;
				case 2:
					xy = [x, y + 1];
					break;
				case 3:
					xy = [x - 1, y];
					break;
				default:
					xy = [0, 0];
					break;
			}
			if (board.board[xy[0]][xy[1]].types[0] === -1) { 
				this.tiletracker.push(new EmptyTile());
				return;
			}
			this.cityScore(xy[0], xy[1], board, meeplesPresent, (j + 2) % 4);
		}
	}

	endRoadScore(x, y, board) {
		const checkedSides = [0, 0, 0, 0];
		let xy;
		let xyn;
		c: for (let i = 0; i < 4; i++) {
			let tx = x;
			let ty = y;
			const type = board.board[tx][ty].types[i];
			this.tiletracker = [];

			if (checkedSides[i] === 1)
				continue c;

			if (type === 1 && board.board[x][y].meeple[i * 3 + 3] === 1) {
				switch (i) {
					case 0:
						xy = [tx, ty - 1];
						break;
					case 1:
						xy = [tx + 1, ty];
						break;
					case 2:
						xy = [tx, ty + 1];
						break;
					case 3:
						xy = [tx - 1, ty];
						break;
					default:
						xy = [0, 0];
						break;
				}
				if (!this.tiletracker.includes(board.board[x][y]))
					this.tiletracker.push(board.board[x][y]);

				this.roadScoreEnd(xy[0], xy[1], board, (i + 2) % 4);
				let s = -1;
				for (let j = 0; j < 3; j++) {
					if (i <= j) {
						if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] === 1) {
							s = j + 1;
							break;
						}
					} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] === 1) {
						s = j;
						break;
					}

				}
				switch (s) {
					case 0:
						xyn = [tx, ty - 1];
						break;
					case 1:
						xyn = [tx + 1, ty];
						break;
					case 2:
						xyn = [tx, ty + 1];
						break;
					case 3:
						xyn = [tx - 1, ty];
						break;
					case -1:
						xyn = [xy[0], xy[1]];
					default:
						xyn = [0, 0];
						break;
				}
				if (s !== -1) {
					this.roadScoreEnd(xyn[0], xyn[1], board, (s + 2) % 4);
					checkedSides[s] = 1;
				}
				for (let k = 0; k < this.tiletracker.length; k++) {
					if (this.tiletracker[k].types[0] === -1) {
						this.tiletracker.splice(k, 1);
						k--;
					}
				}
				board.players[board.board[x][y].meeple[0]].score += this.tiletracker.length;
				this.tiletracker = [];
				if (board.board[tx][ty].meeple[i * 3 + 1 + 2] === 1) {
					board.players[board.board[x][y].meeple[0]].meepleCount += 1;
					board.board[x][y].meeple = [-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1];
				}
			}
		}
	}

	roadScoreEnd(x, y, board, previousSide) {
		let i = -1;

		for (let j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] === 1) {
					i = j + 1;
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] === 1) {
				i = j;
				break;
			}
		}

		if (!this.tiletracker.includes(board.board[x][y]))
			this.tiletracker.push(board.board[x][y]);

		if (i === -1) { 
			return;
		}

		let xy;
		switch (i) {
			case 0:
				xy = [x, y - 1];
				break;
			case 1:
				xy = [x + 1, y];
				break;
			case 2:
				xy = [x, y + 1];
				break;
			case 3:
				xy = [x - 1, y];
				break;
			default:
				xy = [0, 0];
				break;
		}

		if (board.board[xy[0]][xy[1]].types[0] === -1) {
			this.tiletracker.push(new EmptyTile());
		}

		if (x === this.x && y === this.y) { 
			return;
		}

		this.roadScoreEnd(xy[0], xy[1], board, (i + 2) % 4);

	}

	endCityScore(x, y, board) {
		const checkedSides = [0, 0, 0, 0];
		let xy;
		c: for (let i = 0; i < 4; i++) { 
			let tx = x;
			let ty = y;
			const type = board.board[tx][ty].types[i];
			this.tiletracker = [];

			if (checkedSides[i] === 1)
				continue c;

			if (type === 2 && board.board[x][y].meeple[i * 3 + 3] === 1) { 

				this.tiletracker.push(board.board[x][y]);

				const sides = [];

				sides.push(i);

				for (let j = 0; j < 3; j++) {
					if (i > j) {
						if (board.board[x][y].connected[i][j] && board.board[x][y].types[j] === 2) {
							sides.push(j);
						}
					} else if (board.board[x][y].connected[i][j] && board.board[x][y].types[j + 1] === 2) {
						sides.push(j + 1);
					}
				}

				for (let j = 0; j < sides.length; j++) {
					checkedSides[sides[j]] = 1;
					switch (sides[j]) {
						case 0:
							xy = [tx, y - 1];
							break;
						case 1:
							xy = [tx + 1, y];
							break;
						case 2:
							xy = [tx, y + 1];
							break;
						case 3:
							xy = [tx - 1, y];
							break;
						default:
							xy = [0, 0];
					}
					this.cityScore(xy[0], xy[1], board, meeplesPresent, (sides[j] + 2) % 4);
				}

				for (let k = 0; k < this.tiletracker.length; k++) {
					if (this.tiletracker[k].types[0] === -1) {
						this.tiletracker.splice(k, 1);
						k--;
					}
				}
				board.players[board.board[x][y].meeple[0]].score += this.tiletracker.length;
				this.tiletracker = [];
			}
		}
	}

	cityScoreEnd(x, y, board, previousSide) {
		if (!this.tiletracker.includes(board.board[x][y]))
			this.tiletracker.push(board.board[x][y]);
		else
			return;

		const sides = [];

		for (let j = 0; j < 3; j++) {

			if (previousSide <= j) {
				if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j + 1] === 2) {
					sides.push(j + 1);
					break;
				}
			} else if (board.board[x][y].connected[previousSide][j] && board.board[x][y].types[j] === 2) {
				sides.push(j);
				break;
			}
		}

		if (sides.length === 0)
			return;


		let xy;
		for (let i = 0; i < sides.length; i++) {
			const j = sides[i];
			switch (j) {
				case 0:
					xy = [x, y - 1];
					break;
				case 1:
					xy = [x + 1, y];
					break;
				case 2:
					xy = [x, y + 1];
					break;
				case 3:
					xy = [x - 1, y];
					break;
				default:
					xy = [0, 0];
					break;
			}
			if (board.board[xy[0]][xy[1]].types[0] === -1) { 
				this.tiletracker.push(new EmptyTile());
				return;
			}
			this.cityScoreEnd(xy[0], xy[1], board, (j + 2) % 4);
		}
	}

	farmScore(x, y, board) {
		const visited = new Map();
		const cities = new Array(CarcassonneMain.cityCount).fill(0);
		for (let i = 0; i < board.board.length; i++) {
			for (let j = 0; j < board.board[i].length; j++) {
				visited.set(i * 1000 + j, new Array(12).fill(0));
			}
		}

		

		this.farmScoreLoop(x, y, board, visited, board.board[x][y].meeple[14], cities);

		for (let i = 0; i < cities.length; i++) {
			if (cities[i] === 1) {
				board.players[board.board[x][y].meeple[0]].score += 3;
			}
		}

	}

	farmScoreLoop(x, y, board, visited, pos, cities) {
		if (x < 0 || y < 0 || x >= board.board.length || y >= board.board[0].length) {
			return;
		}

		if (board.board[x][y].types[0] === -1) {
			return;
		}

		if (visited.get(x * 1000 + y)[pos] === 1) {
			return;
		}

		visited.get(x * 1000 + y)[pos] = 1;

		for (let i = 0; i < 4; i++) {
			if (board.board[x][y].types[i] === 2 && fieldCityConnected(board.board[x][y], pos, i) && board.board[x][y].completion[i] !== 0) {
				cities[board.board[x][y].completion[i]] = 1;
			}
		}

		const connecteds = board.board[x][y].connects(pos);
		for (let i = 0; i < connecteds.length; i++) {
			if (connecteds[i] === 1) {
				const newSide = oppositeSide(i);
				let xy;
				switch (Math.floor(i / 3)) {
					case 0:
						xy = [x, y - 1];
						break;
					case 1:
						xy = [x + 1, y];
						break;
					case 2:
						xy = [x, y + 1];
						break;
					case 3:
						xy = [x - 1, y];
						break;
					default:
						xy = [0, 0];
						break;
				}
				this.farmScoreLoop(xy[0], xy[1], board, visited, newSide, cities);
			}
		}
	}

}

function fieldCityConnected(tile, fieldPos, citySide) {
	const connecteds = tile.connects(fieldPos);
	for (let i = 0; i < connecteds.length; i++) {
		if (connecteds[i] === 1 && (i + 1) % 3 === 0) {
			if (Math.floor(i / 3) < citySide) {
				if (tile.connected[Math.floor(i / 3)][citySide - 1]) {
					return true;
				}
			}
		}
	}

	if (Math.abs((citySide * 3 + 1) - fieldPos) === 2) {
		return true;
	}

	if (fieldPos === 0 && citySide === 3) {
		return true;
	}
	
	return false;
}

function oppositeSide(x) { 
	switch (x){
		case 0:
		case 1:
		case 2:
		case 6:
		case 7:
		case 8:
			return 8 - x;
		case 3:
		case 4:
		case 5:
		case 9:
		case 10:
		case 11:
			return 14 - x;
		default:
			return -69420;
	}
}


