
class GUI {
    static strdata;
    static data;
    static sp;
  
    static gui(board) {
      let j;
  
      this.data = board.board;
      this.strdata = new Array(133).fill(0).map(() => new Array(133));
      for (let i = 0; i < 133; i++) {
        for (let k = 0; k < 133; k++) {
          this.strdata[k][i] = this.data[i][k];
        }
      }
  
      const columnNames = new Array(133);
      columnNames[0] = "Row #";
      for (let i = 0; i < 133; i++) {
        columnNames[i] = i.toString();
      }
  
      j = new JTable(this.strdata, columnNames);
      j.setBounds(133, 133, 500, 133);
      j.setRowHeight(222);
      j.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      for (let i = 0; i < 133; i++) {
        j.getColumnModel().getColumn(i).setCellRenderer(new ImageRenderer());
        j.getColumnModel().getColumn(i).setPreferredWidth(222);
      }
  
      this.sp = new JScrollPane(j);
  
      return this.sp;
    }
  }
  
  class ImageRenderer extends DefaultTableCellRenderer {
    lbl = new JLabel();
  
    blank = new ImageIcon(getClass().getResource("res/emptytile.png"));
    plusroad = new ImageIcon(getClass().getResource("res/+road.png"));
    oneedgecity = new ImageIcon(getClass().getResource("res/1edgecity.png"));
    onenot = new ImageIcon(getClass().getResource("res/1not.png"));
    oneroad = new ImageIcon(getClass().getResource("res/1road.png"));
    twoadjcity = new ImageIcon(getClass().getResource("res/2adjacentcity.png"));
    allcity = new ImageIcon(getClass().getResource("res/allcity.png"));
    bowtiecity = new ImageIcon(getClass().getResource("res/bowtiecity.png"));
    bowtiefield = new ImageIcon(getClass().getResource("res/bowtiefield.png"));
    cornercity = new ImageIcon(getClass().getResource("res/cornercity.png"));
    cornercityroad = new ImageIcon(getClass().getResource("res/cornercityroad.png"));
    edgecityroadleft = new ImageIcon(getClass().getResource("res/edgecityroadleft.png"));
    edgecityroadright = new ImageIcon(getClass().getResource("res/edgecityroadright.png"));
    edgecityroadstraight = new ImageIcon(getClass().getResource("res/edgecityroadstraight.png"));
    edgecityroadt = new ImageIcon(getClass().getResource("res/edgecityroadt.png"));
    lroad = new ImageIcon(getClass().getResource("res/Lroad.png"));
    straightroad = new ImageIcon(getClass().getResource("res/straightroad.png"));
    troad = new ImageIcon(getClass().getResource("res/Troad.png"));
    meeple = new ImageIcon(getClass().getResource("res/meeple.png"));
    meepley = new ImageIcon(getClass().getResource("res/meepleyellow.png"));
    meepleg = new ImageIcon(getClass().getResource("res/meeplegreen.png"));
  
    getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column) {
      let img = this.blank;
      if (value.type === "") {
        this.lbl.setIcon(this.blank);
        this.lbl.setText(`(${column}, ${row})`);
        return this.lbl;
      }
      if (value.type === "edgecityroadstraight") {
        img = new ImageIcon(this.edgecityroadstraight.getImage());
      } else if (value.type === "allcity") {
        img = new ImageIcon(this.allcity.getImage());
      } else if (value.type === "1not") {
        img = new ImageIcon(this.onenot.getImage());
      } else if (value.type === "1road") {
        img = new ImageIcon(this.oneroad.getImage());
      } else if (value.type === "cornercity") {
        img = new ImageIcon(this.cornercity.getImage());
      } else if (value.type === "cornercityroad") {
        img = new ImageIcon(this.cornercityroad.getImage());
      } else if (value.type === "bowtiecity") {
        img = new ImageIcon(this.bowtiecity.getImage());
      } else if (value.type === "2adjacentcity") {
        img = new ImageIcon(this.twoadjcity.getImage());
      } else if (value.type === "bowtiefield") {
        img = new ImageIcon(this.bowtiefield.getImage());
      } else if (value.type === "1edgecity") {
        img = new ImageIcon(this.oneedgecity.getImage());
      } else if (value.type === "edgecityroadleft") {
        img = new ImageIcon(this.edgecityroadleft.getImage());
      } else if (value.type === "edgecityroadright") {
        img = new ImageIcon(this.edgecityroadright.getImage());
      } else if (value.type === "edgecityroadt") {
        img = new ImageIcon(this.edgecityroadt.getImage());
      } else if (value.type === "straightroad") {
        img = new ImageIcon(this.straightroad.getImage());
      } else if (value.type === "Lroad") {
        img = new ImageIcon(this.lroad.getImage());
      } else if (value.type === "Troad") {
        img = new ImageIcon(this.troad.getImage());
      } else if (value.type === "+road") {
        img = new ImageIcon(this.plusroad.getImage());
      }
      this.lbl.setText(`(${column}, ${row})`);
      const combined = new BufferedImage(220, 220, BufferedImage.TYPE_INT_ARGB);
      const g = combined.createGraphics();
      const old = g.getTransform();
      g.rotate(value.rotations * Math.PI / 2, 110, 110);
      g.drawImage(img.getImage(), 0, 0, null);
      g.setTransform(old);
      if (value.meeple[14] !== -1) {
        const pos = this.meeplePos(value.meeple[14]);
        if (value.meeple[0] === 0) {
          g.drawImage(this.meeple.getImage(), pos[0], pos[1], null);
        } else if (value.meeple[0] === 1) {
          g.drawImage(this.meepley.getImage(), pos[0], pos[1], null);
        } else if (value.meeple[0] === 2) {
          g.drawImage(this.meepleg.getImage(), pos[0], pos[1], null);
        }
      }
      g.dispose();
      this.lbl.setIcon(new ImageIcon(combined));
      return this.lbl;
    }
  
    meeplePos(pos) {
      switch (pos) {
        case 0:
          return [42, 14];
        case 1:
          return [110, 14];
        case 2:
          return [178, 14];
        case 3:
          return [206, 42];
        case 4:
          return [206, 110];
        case 5:
          return [206, 178];
        case 6:
          return [178, 206];
        case 7:
          return [110, 206];
        case 8:
          return [42, 206];
        case 9:
          return [14, 178];
        case 10:
          return [14, 110];
        case 11:
          return [14, 42];
        default:
          return [0, 0];
      }
    }
  }
  
  