package com.example.model;

public class Maze {

    private Tile[][] grid;

    private static final String[] layout = {
    "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1",
    "1 s s s s s s s s s s s s 1 1 s s s s s s s s s s s s 1",
    "1 s 1 1 1 1 s 1 1 1 1 1 s 1 1 s 1 1 1 1 1 s 1 1 1 1 s 1",
    "1 b 1 1 1 1 s 1 1 1 1 1 s 1 1 s 1 1 1 1 1 s 1 1 1 1 b 1",
    "1 s 1 1 1 1 s 1 1 1 1 1 s 1 1 s 1 1 1 1 1 s 1 1 1 1 s 1",
    "1 s s s s s s s s s s s s s s s s s s s s s s s s s s 1",
    "1 s 1 1 1 1 s 1 1 s 1 1 1 1 1 1 1 1 s 1 1 s 1 1 1 1 s 1",
    "1 s 1 1 1 1 s 1 1 s 1 1 1 1 1 1 1 1 s 1 1 s 1 1 1 1 s 1",
    "1 s s s s s s 1 1 s s s s 1 1 s s s s 1 1 s s s s s s 1",
    "1 1 1 1 1 1 s 1 1 1 1 1 0 1 1 0 1 1 1 1 1 s 1 1 1 1 1 1",
    "1 1 1 1 1 1 s 1 1 1 1 1 0 1 1 0 1 1 1 1 1 s 1 1 1 1 1 1",
    "1 1 1 1 1 1 s 1 1 0 0 0 0 0 0 0 0 0 0 1 1 s 1 1 1 1 1 1",
    "1 1 1 1 1 1 s 1 1 0 1 1 1 Gr Gp 1 1 1 0 1 1 s 1 1 1 1 1 1",
    "0 0 0 0 0 0 s 0 0 0 1 Gb Gy 0 0 Gy 1 0 0 0 0 s 0 0 0 0 0 0",
    "1 1 1 1 1 1 s 1 1 0 1 1 1 1 1 1 1 1 0 1 1 s 1 1 1 1 1 1",
    "1 1 1 1 1 1 s 1 1 0 0 0 0 0 0 0 0 0 0 1 1 s 1 1 1 1 1 1",
    "1 1 1 1 1 1 s 1 1 0 1 1 1 1 1 1 1 1 0 1 1 s 1 1 1 1 1 1",
    "1 1 1 1 1 1 s 1 1 0 1 1 1 1 1 1 1 1 0 1 1 s 1 1 1 1 1 1",
    "1 s s s s s s s s s s s s 1 1 s s s s s s s s s s s s 1",
    "1 s 1 1 1 1 s 1 1 1 1 1 s 1 1 s 1 1 1 1 1 s 1 1 1 1 s 1",
    "1 s 1 1 1 1 s 1 1 1 1 1 s 1 1 s 1 1 1 1 1 s 1 1 1 1 s 1",
    "1 b s s 1 1 s s s s s s s 0 P s s s s s s s 1 1 s s b 1",
    "1 1 1 s 1 1 s 1 1 s 1 1 1 1 1 1 1 1 s 1 1 s 1 1 s 1 1 1",
    "1 1 1 s 1 1 s 1 1 s 1 1 1 1 1 1 1 1 s 1 1 s 1 1 s 1 1 1",
    "1 s s s s s s 1 1 s s s s 1 1 s s s s 1 1 s s s s s s 1",
    "1 s 1 1 1 1 1 1 1 1 1 1 s 1 1 s 1 1 1 1 1 1 1 1 1 1 s 1",
    "1 s 1 1 1 1 1 1 1 1 1 1 s 1 1 s 1 1 1 1 1 1 1 1 1 1 s 1",
    "1 s s s s s s s s s s s s s s s s s s s s s s s s s s 1",
    "1 s 1 1 1 1 s 1 1 1 1 1 1 1 1 1 1 1 1 s 1 1 1 1 1 1 s 1",
    "1 s s s s s s s s s s s s 1 1 s s s s s s s s s s s s 1",
    "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"
    };

    // constructor - converts layout into Tile[][] grid
    public Maze() {
    // 1. first initialize the grid
    grid = new Tile[31][28];
    
    // 2. then loop through layout and fill it
    for (int row = 0; row < layout.length; row++) {
        String[] tokens = layout[row].split(" ");
        for (int col = 0; col < tokens.length; col++) {
            String token = tokens[col];
            // create the right Tile based on token
            if (token.equals("1")){
                grid[row][col] = new Tile(Tile.TileType.WALL);
            }
            else if (token.equals("0")){
                grid[row][col] = new Tile(Tile.TileType.EMPTY);
            }
            else if (token.equals("s")){
                grid[row][col] = new Tile(Tile.TileType.SMALL_PELLET);
            }
            else if (token.equals("b")){
                grid[row][col] = new Tile(Tile.TileType.POWER_PELLET);
            }
            else if (token.equals("P") || token.equals("Gr") || token.equals("Gp") 
                || token.equals("Gb") || token.equals("Gy")) {
            grid[row][col] = new Tile(Tile.TileType.EMPTY);
            }

            }
        }
    }

    // getter for a specific tile
    public Tile getTile(int x, int y) {
        return grid[x][y];
    }

}


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
0  0  0  0  0  0  s  0  0  0  1  Gb Gy 0  0  Gy 1  0  0  0  0  s  0  0  0  0  0  0
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
Gy = Yellow ghost starting position */