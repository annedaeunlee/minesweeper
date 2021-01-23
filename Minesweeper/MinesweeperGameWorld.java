import java.awt.Color;
import java.util.ArrayList; // for the arrays as list
import java.util.Random;
import javalib.impworld.*;
import javalib.worldimages.Posn;
import javalib.worldimages.TextImage;


// to represent the Game set up
class MineGame extends World implements IGameConstants {
  int row;
  int column;
  int mines;
  ArrayList<ArrayList<Cell>> grid;
  Random rand = new Random();

  // ACTUAL CONSTRUCTOR
  MineGame(int row, int column, int mines) {
    this.row = row;
    this.column = column;
    this.mines = mines;
    this.grid = this.makeGrid();
    this.updateGrid(); // link all the neighbors
    this.addMines(); // add the given number of mines
    this.neighborMines(); // updates the numbers of mines surrounding each cell
  }

  // CONSTRUCTOR for general testing
  MineGame(int row, int column, int mines, Random rand) {
    this(row, column, mines);
    this.grid = this.makeGrid();
    this.updateGrid(); // link all the neighbors
    this.rand = rand;
    this.addMines(); // add the given number of mines
    this.neighborMines();
  }

  // CONSTRUCTOR for more specific testing
  MineGame(int row, int column, int mines, Random rand, ArrayList<ArrayList<Cell>> grid) {
    this(row, column, mines, rand);
    this.grid = grid; 
    // no update grid bc this constructor is used for testing the update grid method
    // no add mines, bc also used to test add mines
  }

  // to make the entire grid by putting all the rows together represented as an ArrayList
  // EFFECT: add "ArrayList of Cells"'s that represent each row to an empty 
  // ArrayList of ArrayList of Cells (the number of rows determined by the row
  // field of this MineGame)
  ArrayList<ArrayList<Cell>> makeGrid() {
    ArrayList<ArrayList<Cell>> result = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < this.row; i = i + 1) {
      result.add(this.makeRow());
    }
    return result;
  }

  // to make one row with length equal to the columns field of this MineGame
  /// EFFECT: add the Cells that share a row to an empty 
  //  ArrayList of Cells (the number of Cells determined by the column
  // field of this MineGame)
  ArrayList<Cell> makeRow() {
    ArrayList<Cell> result = new ArrayList<Cell>();
    for (int i = 0; i < this.column; i = i + 1) {
      result.add(new Cell()); 
      // in notes, used func.apply(i) instead of directly creating new Cell?
    }
    return result;
  }

  // Returns the specified cell in the grid of this MineGame based on the
  // given location (row and column)
  Cell getCell(int row, int column) {
    return this.grid.get(row).get(column);
  }

  /////////////////////////////////// UPDATING NEIGHBORS //////////////////////////////////
  // to update the grid of cells in this MineGame to have each other as neighbors
  // EFFECT: modifies each Cell in the grid field (ArrayList of ArrayList of Cells) 
  // of this MineGame to include the surrounding cells of each in the correct order
  void updateGrid() {
    // for each row "row"
    for (int row = 0; row < this.row; row = row + 1) {
      // for each cell "i" in each row
      for (int i = 0; i < this.column; i = i + 1) {
        // to get the current cell the loop is on
        Cell currentCell = this.grid.get(row).get(i);
        // this.getCell(row, column);
        this.updateNeighbors(currentCell, row, i);
      }
    }
  }

  // updates the neighbors of the given currentCell with ALL its surrounding neighbors
  // EFFECT: updates the neighbor field of a cell (specified by given row/column)
  // in this grid of this MineGame completely (i.e. all 8 neighbors are added to the list
  // of neighbors in this cell)
  void updateNeighbors(Cell currentCell, int row, int column) {
    // 0. update "left" neighbor of the cell
    this.updateNeighbor(currentCell, row, column - 1);
    // 1. update "right" neighbor of the cell
    this.updateNeighbor(currentCell, row, column + 1);
    // 2. update top 
    this.updateNeighbor(currentCell, row - 1, column);
    // 3. update bottom
    this.updateNeighbor(currentCell, row + 1, column);
    // 4. update top left
    this.updateNeighbor(currentCell, row - 1, column - 1);
    // 5. update bottom right
    this.updateNeighbor(currentCell, row + 1, column + 1);
    // 6. update top right
    this.updateNeighbor(currentCell, row - 1, column + 1);
    // 7. update bottom left
    this.updateNeighbor(currentCell, row + 1, column - 1);
  }

  // updates the neighbors of the given currentCell with ONE neighbor
  // EFFECT: adds to the neighbors field of the given currentCell:
  // a neighbor cell in this grid of this MineGame
  // (neighbor is specified by location given with num/i)
  void updateNeighbor(Cell currentCell, int num, int i) {
    currentCell.update(this.newGet(num, i));
  }

  /////////////////////////////////// END OF UPDATING NEIGHBORS //////////////////////////////////


  // Returns the specified cell in the grid of this MineGame based on the
  // given location (row and column), if given location out of bounds return null
  Cell newGet(int row, int column) {
    if (row < 0 || column < 0 || row >= this.row || column >= this.column) {
      return null;
    }
    else {
      return this.getCell(row, column);
    }
  }

  // to add a designated number of mines randomly to this MineGame
  // EFFECT: Modifies the isMine field of the current Cell to be false if it is not false already
  // then adds 1 to the count until the count is one less than the number of mines needed
  void addMines() {
    int i = 0;
    while (i < this.mines) {
      Cell currCell = this.getCell(rand.nextInt(this.row), rand.nextInt(this.column));
      if (! currCell.isMine) {
        currCell.isMine = true;
        i = i + 1;
      }
    }
  }

  // to count the number of mines surrounding a cell and update the field accordingly
  // EFFECT: Modifies the mines field of each Cell in the grid field of this MineGame
  // to the number of mines in its neighbors
  void neighborMines() {
    // for the each row "row"
    for (int row = 0; row < this.row; row = row + 1) {
      // for each cell "i" in each row
      for (int i = 0; i < this.column; i = i + 1) {
        Cell cell = this.getCell(row, i);
        cell.mines = cell.countMines();
      }
    }
  }


  ////////////////////////////////////// "BIG-BANG" part /////////////////////////////////////////

  // returns the image/scene of the current WorldState
  @Override
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();

    return this.drawGrid(scene);
  }

  // to draw the grid of this MineGame (makeScene helper)
  // EFFECT: updates the default WorldScene with each loop
  // by placing an image determined by the cell in the grid a loop is on
  // over the WorldScene
  WorldScene drawGrid(WorldScene scene) {
    // for the each row "row"
    for (int row = 0; row < this.row; row = row + 1) {
      // for each cell "i" in each row
      for (int i = 0; i < this.column; i = i + 1) {
        scene.placeImageXY(this.getCell(row, i).drawCell(), 
            i * CELL_SIZE + CELL_SIZE / 2, 
            row * CELL_SIZE + CELL_SIZE / 2);
      }
    }
    return scene;
  }

  // updates the world state depending on the button and position of the mouse-clicked
  // EFFECT: modifies the mode field of the Cells in this MineGame, 
  // and the WorldState of the MineGame according to
  // which Cell was clicked (determined by given position) and which button was clicked
  @Override
  public void onMouseClicked(Posn pos, String buttonName) {    
    Cell clickedCell = this.clickCell(pos);
    // left case
    if (buttonName.equals("LeftButton") && clickedCell.mode == -2) {
      this.clickLeft(clickedCell);
    }
    // right case
    else if (buttonName.equals("RightButton")) {
      clickedCell.updateRightClicked();
    }
  }

  // UNDER CONDITION that this cell is still click-able (i.e. mode is -2):
  // updates the game state depending on the state of the given cell 
  // (if it's a bomb or not, and what "mode" its in) for LEFT-CLICKED cases
  // EFFECT: modifies the given clickedCell's mode field
  // and (if cellClicked indicates win or lose)
  // modifies world state to be end of world & display corresponding end scene
  void clickLeft(Cell clickedCell) {

    // if a mine: reveal this and all mines and end game
    if (clickedCell.isMine) {
      this.clickMine();
      this.endOfWorld("Oh no! You lost");
    }

    // if not a mine: 
    // reveal this cell, apply flood-fill if appropriate and check if all non-mines uncovered
    else {
      clickedCell.updateLeftClicked();
      if (this.winGame()) {
        this.endOfWorld("Good job! You won");
      }
    }
  }


  // to update the conditions when reveal all the mines in the game(when player left-clicks a mine)
  // EFFECT: updates all the cells that are mines in the grid field of this MineGame
  // such that their mode field is 1 ("revealed")
  void clickMine() {
    // for the each row "row"
    for (int row = 0; row < this.row; row = row + 1) {
      // for each cell "i" in each row
      for (int i = 0; i < this.column; i = i + 1) {
        Cell currCell = this.getCell(row, i);
        if (currCell.isMine) {
          currCell.mode = 1;
        }
      }
    }
  }

  // check if the conditions of the Cells of this current MineGame 
  // indicates player has won game (if all Cells with mine field of false have "1" as mode)
  boolean winGame() {
    boolean allClicked = true;
    // for the each row "row"
    for (int row = 0; row < this.row; row = row + 1) {
      // for each cell "i" in each row
      for (int i = 0; i < this.column; i = i + 1) {
        Cell curr = this.getCell(row, i);
        if (! curr.isMine) {
          allClicked = allClicked && curr.mode == 1;
        }
      }
    }
    return allClicked;
  }

  // returns the corresponding Cell in the Grid field of this MineGame
  // of the given Posn.
  Cell clickCell(Posn pos) {
    return this.getCell(getPosn(pos.y, this.row), getPosn(pos.x, this.column));
  }

  // returns the corresponding index in a certain orientation
  // specified by the given x/y position and row/column number
  // (if position out of bounds of the range of the MineGame, 
  // return the closest valid index)
  int getPosn(int pos, int direction) {
    if (pos / CELL_SIZE >= direction) {
      return (int) Math.floor(pos / CELL_SIZE) - 1;
    }
    else if (pos / CELL_SIZE < 0) {
      return 0;
    }
    else {
      return (int) Math.floor(pos / CELL_SIZE);
    }
  }

  // updates the WorldScene to be the endScene
  // EFFECT: modifies the worldScene to have the given end of game message
  // layered over the final game scene image
  @Override
  public WorldScene lastScene(String msg) {
    // font size?
    WorldScene endScene = this.makeScene();
    endScene.placeImageXY(new TextImage(msg, Color.red), 
        CELL_SIZE * this.column / 2, CELL_SIZE * this.row / 2);
    return endScene;
  }

  //////////////////////////// END OF "BIG-BANG" part ///////////////////////////////////////////


}
