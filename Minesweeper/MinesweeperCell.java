import java.util.ArrayList;
import java.awt.Color;
import javalib.worldimages.*;

// to represent any constants used
interface IGameConstants {
  int CELL_SIZE = 50;
  Color OUTLINE_COLOR = Color.BLACK;
  Color INIT_COLOR = Color.LIGHT_GRAY;
  Color EMPTY_COLOR = Color.DARK_GRAY;

  int FLAG_SIZE = 25;
  Color FLAG_COLOR = Color.ORANGE;

  int BOMB_SIZE = 12;
  Color BOMB_COLOR = Color.RED;

  int TEXT_SIZE = 40;
  Color TEXT_COLOR1 = Color.BLUE;
  Color TEXT_COLOR2 = Color.WHITE;
  Color TEXT_COLOR3 = Color.ORANGE;
  Color TEXT_COLOR4 = Color.CYAN;
  Color TEXT_COLOR5 = Color.YELLOW;
  Color TEXT_COLOR6 = Color.GREEN;
  Color TEXT_COLOR7 = Color.MAGENTA;
  Color TEXT_COLOR8 = Color.PINK;

  // constants to represent a drawn cell
  WorldImage INIT_CELL = 
      new OverlayImage(
          new RectangleImage(CELL_SIZE, CELL_SIZE, "outline", OUTLINE_COLOR),
          new RectangleImage(CELL_SIZE, CELL_SIZE, "solid", INIT_COLOR));

  WorldImage REVEALED_CELL = 
      new OverlayImage(new RectangleImage(CELL_SIZE, CELL_SIZE, "outline", OUTLINE_COLOR),
          new RectangleImage(CELL_SIZE, CELL_SIZE, "solid", EMPTY_COLOR));

  WorldImage MINE_CELL = 
      new OverlayImage(new CircleImage(BOMB_SIZE, "solid", BOMB_COLOR), REVEALED_CELL);

  WorldImage FLAGGED_CELL = 
      new OverlayImage(new EquilateralTriangleImage(FLAG_SIZE, "solid", FLAG_COLOR), INIT_CELL);

}



// to represent a cell in the game
class Cell implements IGameConstants {
  boolean isMine;
  ArrayList<Cell> neighbors;
  int mines; // make a method in game world that counts all the mines with the count mines method
  int mode;
  // mode is represented by 3 integers:
  // -2: default cell (un-clicked)
  //  1: when it's been revealed (left-clicked)
  //  2: when it's been flagged (right-clicked)

  // default constructor
  Cell() {
    this.isMine = false;
    this.neighbors = new ArrayList<Cell>(8);
    this.mode = -2;
  }

  // convenience constructor
  Cell(boolean isMine, ArrayList<Cell> neighbors, int mode, int mines) {
    this.isMine = isMine;
    this.neighbors = neighbors;
    this.mode = mode;
    this.mines = mines;
  }

  // updates this cell's list of neighbors with given cell
  // EFFECT: updates this cell's list of neighbor with the given cell at the given index
  void update(Cell c) {
    this.neighbors.add(c);
  }

  // to count the number of mines in the neighbors field of this Cell
  int countMines() {
    int i = 0;
    int mines = 0;
    while (i < 8) {
      if (this.neighbors.get(i) != null &&
          this.neighbors.get(i).isMine) {
        mines = mines + 1;
      }
      i = i + 1;
    }
    return mines;
  }

  // to draw a single cell
  WorldImage drawCell() {

    // if the cell is not clicked, draw it like this
    if (this.mode == -2) {
      return INIT_CELL;
    }

    // if the cell is left clicked, check to see if it is mine, or a cell with 
    // a number on it, or it have the flood-fill behavior
    else if (this.mode == 1) {
      return this.drawLeftCase();
    }
    // if the cell is right clicked, flag it (should be able to un-flag it?)
    else {
      return FLAGGED_CELL;
    }
  }

  // to draw a cell if it's been left-clicked depending on if it is
  // a mine, a cell with no neighboring mines, or a cell surrounded by a number of mines
  WorldImage drawLeftCase() {

    // if the cell is a mine
    if (this.isMine) {
      return MINE_CELL;
    }

    // or a cell with no mines surrounding
    else if (this.mines == 0) {
      return REVEALED_CELL;
    }

    // or a cell with mines around it
    else if (this.mines == 1) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR1), REVEALED_CELL);
    }

    else if (this.mines == 2) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR2), REVEALED_CELL);
    }
    else if (this.mines == 3) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR3), REVEALED_CELL);
    }
    else if (this.mines == 4) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR4), REVEALED_CELL);
    }
    else if (this.mines == 5) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR5), REVEALED_CELL);
    }
    else if (this.mines == 6) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR6), REVEALED_CELL);
    }
    else if (this.mines == 7) {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR7), REVEALED_CELL);
    }
    else {
      return new OverlayImage(new TextImage(Integer.toString(this.mines),
          TEXT_SIZE, TEXT_COLOR7), REVEALED_CELL);
    }

  }

  // UNDER CONDITION that this Cell is still click-able (mode is -2) AND not a mine
  // update the Cell's mode and apply "flood-fill effect" accordingly
  // EFFECT: modifies this Cell's mode to be "left-clicked" (mode = 1)
  // and modifies its neighboring Cell's mode to be "left-clicked" if this
  // Cell is not a mine
  void updateLeftClicked() {
    this.mode = 1; // update mode to "revealed"
    // if has surrounding mines, end.
    // if no surrounding mines, flood-fill:
    if (this.mines == 0) {
      for (Cell c : this.neighbors) {
        // if this neighbor is a Cell, and has not been "clicked" yet:
        if ((c != null) && c.mode == -2) {
          c.updateLeftClicked();
        }
      }
    }
  }

  // UNDER CONDITION that this Cell's mode field is -2 or 2 (un-revealed)
  // flag or unflag the cell
  // EFFECT: modifies the mode field of this Cell to be the negation of its previous value
  void updateRightClicked() {
    if (this.mode != 1) {
      this.mode = - (this.mode);
    }
  }
}






















