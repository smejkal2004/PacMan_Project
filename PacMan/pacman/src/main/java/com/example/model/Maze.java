package com.example.model;

public class Maze {

    private Tile[][] grid;

    private static final String[] layout = {
    
    "XXXXXXXXXXXXXXXXXXX",
    "X........X........X",
    "XBXX.XXX.X.XXX.XXBX",
    "X.................X",
    "X.XX.X.XXXXX.X.XX.X",
    "X....X... ...X....X",
    "XXXX.XXXX XXXX.XXXX",
    "000X.X       X.X000",
    "XXXX.X XXrXX X.XXXX",
    "0       bpo       0",
    "XXXX.X XXXXX X.XXXX",
    "000X.X       X.X000",
    "XXXX.X XXXXX X.XXXX",
    "X........X........X",
    "X.XX.XXX.X.XXX.XX.X",
    "XB.X.....P.....X.BX",
    "XX.X.X.XXXXX.X.X.XX",
    "X....X...X...X....X",
    "X.XXXXXX.X.XXXXXX.X",
    "X.................X",
    "XXXXXXXXXXXXXXXXXXX"
    
    };

    // constructor - converts layout into Tile[][] grid
    public Maze() {
    // initialises the grid
        grid = new Tile[layout.length][layout[0].length()];
    
    // matches the grid automatically to the layout
    for (int row = 0; row < layout.length; row++) {

        for (int col = 0; col < layout[row].length(); col++) { 
            
        char token = layout[row].charAt(col);

            // create the right Tile based on token
            if (token == 'X'){
                grid[row][col] = new Tile(Tile.TileType.WALL);
            }
            else if (token == '0'){
                grid[row][col] = new Tile(Tile.TileType.EMPTY);
            }
            else if (token == '.'){
                grid[row][col] = new Tile(Tile.TileType.SMALL_PELLET);
            }
            else if (token == 'B'){
                grid[row][col] = new Tile(Tile.TileType.POWER_PELLET);
            }
            else if (token == 'P' || token == 'r' || token == 'p' 
                || token == 'b' || token == 'o' || token == ' '){ {
            grid[row][col] = new Tile(Tile.TileType.EMPTY);
            }

            }
        }
    }
    }
    // number of rows
    public int getRows() {
        return grid.length;
    }
    // number of columns 
    public int getCols() {
        return grid[0].length;
    }

    // getter for a specific tile
    public Tile getTile(int row, int col) {
        return grid[row][col];
    }
    public void setTile(int row, int col, Tile tile) {
        grid[row][col] = tile;
    }
}

/* New map: 
    "XXXXXXXXXXXXXXXXXXX",
    "X........X........X",
    "XBXX.XXX.X.XXX.XXBX",
    "X.................X",
    "X.XX.X.XXXXX.X.XX.X",
    "X....X... ...X....X",
    "XXXX.XXXX XXXX.XXXX",
    "000X.X       X.X000",
    "XXXX.X XXrXX X.XXXX",
    "0      XbpoX      0",
    "XXXX.X XXXXX X.XXXX",
    "000X.X   C   X.X000",
    "XXXX.X XXXXX X.XXXX",
    "X........X........X",
    "X.XX.XXX.X.XXX.XX.X",
    "XB.X.....P.....X.BX",
    "XX.X.X.XXXXX.X.X.XX",
    "X....X...X...X....X",
    "X.XXXXXX.X.XXXXXX.X",
    "X.................X",
    "XXXXXXXXXXXXXXXXXXX" 
    Legend:
    X  = wall
    0  = empty space (no pellet)
    .  = small pellet
    B = big/power pellet
    P  = Pac-Man starting position
    r = Red ghost starting position
    p = Pink ghost starting position
    b = Blue ghost starting position
    o = Orange ghost starting position
    C = Cherry (Will add later)*/



// Old map below:
/* 1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1
1  s  s  s  s  s  s  s  s  s  s  s  s  1  1  s  s  s  s  s  s  s  s  s  s  s  s  1
1  s  1  1  1  1  s  1  1  1  1  1  s  1  1  s  1  1  1  1  1  s  1  1  1  1  s  1
1  b  1  1  1  1  s  1  1  1  1  1  s  1  1  s  1  1  1  1  1  s  1  1  1  1  b  1
1  s  1  1  1  1  s  1  1  1  1  1  s  1  1  s  1  1  1  1  1  s  1  1  1  1  s  1
1  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  1
1  s  1  1  1  1  s  1  1  s  1  1  1  1  1  1  1  1  s  1  1  s  1  1  1  1  s  1
1  s  1  1  1  1  s  1  1  s  1  1  1  1  1  1  1  1  s  1  1  s  1  1  1  1  s  1
1  s  s  s  s  s  s  1  1  s  s  s  s  1  1  s  s  s  s  1  1  s  s  s  s  s  s  1
1  1  1  1  1  1  s  1  1  1  1  1  0  1  1  0  1  1  1  1  1  s  1  1  1  1  1  1
1  1  1  1  1  1  s  1  1  1  1  1  0  1  1  0  1  1  1  1  1  s  1  1  1  1  1  1
1  1  1  1  1  1  s  1  1  0  0  0  0  0  0  0  0  0  0  1  1  s  1  1  1  1  1  1
1  1  1  1  1  1  s  1  1  0  1  1  1  Gr Gp 1  1  1  0  1  1  s  1  1  1  1  1  1
0  0  0  0  0  0  s  0  0  0  1  Gb Gy 0  0  Go 1  0  0  0  0  s  0  0  0  0  0  0
1  1  1  1  1  1  s  1  1  0  1  1  1  1  1  1  1  1  0  1  1  s  1  1  1  1  1  1
1  1  1  1  1  1  s  1  1  0  0  0  0  0  0  0  0  0  0  1  1  s  1  1  1  1  1  1
1  1  1  1  1  1  s  1  1  0  1  1  1  1  1  1  1  1  0  1  1  s  1  1  1  1  1  1
1  1  1  1  1  1  s  1  1  0  1  1  1  1  1  1  1  1  0  1  1  s  1  1  1  1  1  1
1  s  s  s  s  s  s  s  s  s  s  s  s  1  1  s  s  s  s  s  s  s  s  s  s  s  s  1
1  s  1  1  1  1  s  1  1  1  1  1  s  1  1  s  1  1  1  1  1  s  1  1  1  1  s  1
1  s  1  1  1  1  s  1  1  1  1  1  s  1  1  s  1  1  1  1  1  s  1  1  1  1  s  1
1  b  s  s  1  1  s  s  s  s  s  s  s  0  P  s  s  s  s  s  s  s  1  1  s  s  b  1
1  1  1  s  1  1  s  1  1  s  1  1  1  1  1  1  1  1  s  1  1  s  1  1  s  1  1  1
1  1  1  s  1  1  s  1  1  s  1  1  1  1  1  1  1  1  s  1  1  s  1  1  s  1  1  1
1  s  s  s  s  s  s  1  1  s  s  s  s  1  1  s  s  s  s  1  1  s  s  s  s  s  s  1
1  s  1  1  1  1  1  1  1  1  1  1  s  1  1  s  1  1  1  1  1  1  1  1  1  1  s  1
1  s  1  1  1  1  1  1  1  1  1  1  s  1  1  s  1  1  1  1  1  1  1  1  1  1  s  1
1  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  s  1
1  s  1  1  1  1  s  1  1  1  1  1  1  1  1  1  1  1  1  s  1  1  1  1  1  1  s  1
1  s  s  s  s  s  s  s  s  s  s  s  s  1  1  s  s  s  s  s  s  s  s  s  s  s  s  1
1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1  1

Legend:
1  = wall
0  = empty space (no pellet)
s  = small pellet
b  = big/power pellet
P  = Pac-Man starting position
Gr = Red ghost starting position
Gp = Pink ghost starting position
Gb = Blue ghost starting position
Go = Orange ghost starting position */