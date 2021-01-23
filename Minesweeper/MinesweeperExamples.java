import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javalib.impworld.WorldScene;
import javalib.worldimages.*;
import tester.Tester;

//examples for testing the game
class GameExample implements IGameConstants {

  //EXAMPLES
  MineGame game1;
  MineGame game1A;
  MineGame game2;

  // default cell
  Cell cellA;

  ArrayList<Cell> rowAGame1;
  ArrayList<ArrayList<Cell>> gridGame1;
  ArrayList<Cell> rowAGame2;
  ArrayList<ArrayList<Cell>> gridGame2;

  // grid with no neighbors yet
  // 2 x 2
  Cell cellE1;
  Cell cellE2;
  Cell cellE3;
  Cell cellE4;
  ArrayList<Cell> rowE1;
  ArrayList<Cell> rowE2;
  ArrayList<ArrayList<Cell>> gridE1;
  MineGame gameE1;

  // 3 x 3
  Cell cellE00;
  Cell cellE01;
  Cell cellE02;
  Cell cellE10;
  Cell cellE11;
  Cell cellE12;
  Cell cellE20;
  Cell cellE21;
  Cell cellE22;
  ArrayList<Cell> rowEA;
  ArrayList<Cell> rowEB;
  ArrayList<Cell> rowEC;
  ArrayList<ArrayList<Cell>> gridE2;
  MineGame gameE2;
  MineGame gameE2Update;

  // "intermediate" game board
  ArrayList<Cell> neighbor00;
  Cell cell00;
  ArrayList<Cell> neighbor01;
  Cell cell01;
  ArrayList<Cell> neighbor02;
  Cell cell02;

  ArrayList<Cell> neighbor10;
  Cell cell10;
  ArrayList<Cell> neighbor11;
  Cell cell11;
  ArrayList<Cell> neighbor12;
  Cell cell12;

  ArrayList<Cell> neighbor20;
  Cell cell20;
  ArrayList<Cell> neighbor21;
  Cell cell21;
  ArrayList<Cell> neighbor22;
  Cell cell22;

  Cell cell10Dupe;

  ArrayList<Cell> rowAGame2Draw;
  ArrayList<Cell> rowBGame2Draw;
  ArrayList<Cell> rowCGame2Draw;
  ArrayList<ArrayList<Cell>> gridGame2Draw;
  MineGame game3;

  // test draw examples
  WorldScene emptyScene;  
  WorldImage cellBase;
  WorldImage cellFlagged;
  WorldImage cellMine;
  WorldImage cellNum1;
  WorldImage cellNum2;
  WorldImage cellGrey;

  // to set the initial conditions
  void initialData() {

    // game example
    this.game1 = new MineGame(2, 2, 0, new Random(0));
    this.game1A = new MineGame(2, 2, 2, new Random(0));
    this.game2 = new MineGame(3, 3, 2, new Random(1));

    // an default cell with no neighbors
    this.cellA = new Cell();

    // An INTITIAL GRID BOARDs - for testing makeGrid
    this.rowAGame1 = new ArrayList<Cell>(Arrays.asList(this.cellA, this.cellA)); // 2 x 2
    this.gridGame1 = new ArrayList<ArrayList<Cell>>(Arrays.asList(this.rowAGame1, this.rowAGame1));
    this.rowAGame2 = new ArrayList<Cell>(Arrays.asList(this.cellA,this.cellA,this.cellA)); // 3 x 3
    this.gridGame2 = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        this.rowAGame2, this.rowAGame2, this.rowAGame2));

    // UNLINKED DEFAULT GRID - for testing update methods
    // game w a 2 x 2 grid with no mines
    this.cellE1 = new Cell();
    this.cellE2 = new Cell();
    this.cellE3 = new Cell();
    this.cellE4 = new Cell();

    this.rowE1 = new ArrayList<Cell>(Arrays.asList(cellE1, cellE2));
    this.rowE2 = new ArrayList<Cell>(Arrays.asList(cellE3, cellE4));
    this.gridE1 = new ArrayList<ArrayList<Cell>>(Arrays.asList(this.rowE1, this.rowE2));
    this.gameE1 = new MineGame(2, 2, 0, new Random(0), this.gridE1);

    // game w a 3 x 3 grid with 2 mines placed with Random(1)
    this.cellE00 = new Cell();
    this.cellE01 = new Cell();
    this.cellE02 = new Cell();
    this.cellE10 = new Cell();
    this.cellE11 = new Cell();
    this.cellE12 = new Cell();
    this.cellE20 = new Cell();
    this.cellE21 = new Cell();
    this.cellE22 = new Cell();
    this.rowEA = new ArrayList<Cell>(Arrays.asList(cellE00, cellE01, cellE02));
    this.rowEB = new ArrayList<Cell>(Arrays.asList(cellE10, cellE11, cellE12));
    this.rowEC = new ArrayList<Cell>(Arrays.asList(cellE20, cellE21, cellE22));
    this.gridE2 = new ArrayList<ArrayList<Cell>>(Arrays.asList(rowEA, rowEB, rowEC));
    this.gameE2 = new MineGame(3, 3, 0, new Random(2), this.gridE2);
    this.gameE2Update = new MineGame(3, 3, 0);

    // "INTERMEDIATE BOARD" for testing - similar to game2, 
    // (so same mine set up and dimension, but with different "modes" and neighbors are from game2)
    // a collections of CELLS with neighbors from game2 
    // row 1
    this.neighbor00 = this.game2.getCell(0, 0).neighbors;
    this.cell00 = new Cell(false, this.neighbor00,-2, 2); // un-clicked
    this.neighbor01 = this.game2.getCell(0, 1).neighbors;
    this.cell01 = new Cell(true, this.neighbor01, 2, 1);
    this.neighbor02 = this.game2.getCell(0, 2).neighbors;
    this.cell02 = new Cell(false, this.neighbor02, 1, 1); 
    // row 2
    this.neighbor10 = this.game2.getCell(1, 0).neighbors;
    this.cell10 = new Cell(true, this.neighbor10, -2, 1); // un-clicked
    this.neighbor11 = this.game2.getCell(1, 1).neighbors;
    this.cell11 = new Cell(false, this.neighbor11, 1, 2);
    this.neighbor12 = this.game2.getCell(1, 2).neighbors;
    this.cell12 = new Cell(false, this.neighbor12, 1, 1); 
    // row 3
    this.neighbor20 = this.game2.getCell(2, 0).neighbors;
    this.cell20 = new Cell(false, this.neighbor20, 1, 1);
    this.neighbor21 = this.game2.getCell(2, 1).neighbors;
    this.cell21 = new Cell(false, this.neighbor21, 1, 1);
    this.neighbor22 = this.game2.getCell(2, 2).neighbors;
    this.cell22 = new Cell(false, this.neighbor22, 1, 0); 
    // this case is for drawing flag
    this.cell10Dupe = new Cell(true, this.neighbor10, 1, 1);
    // the ROWS
    this.rowAGame2Draw = new ArrayList<Cell>(Arrays.asList(this.cell00, this.cell01, this.cell02));
    this.rowBGame2Draw = new ArrayList<Cell>(Arrays.asList(this.cell10, this.cell11, this.cell12));
    this.rowCGame2Draw = new ArrayList<Cell>(Arrays.asList(this.cell20, this.cell21, this.cell22));
    // the GRID
    this.gridGame2Draw = new ArrayList<ArrayList<Cell>>(Arrays.asList(
        this.rowAGame2Draw, this.rowBGame2Draw, this.rowCGame2Draw));
    // the GAME
    this.game3 = new MineGame(3, 3, 2, new Random(1), this.gridGame2Draw);

    // a 3 x 3 empty scene
    this.emptyScene = this.game2.getEmptyScene();

    // EXAMPLE IMAGES - testing draw methods
    this.cellBase = INIT_CELL;
    this.cellFlagged = FLAGGED_CELL;
    this.cellNum1 = new OverlayImage(new TextImage(Integer.toString(1),
        TEXT_SIZE, TEXT_COLOR1), REVEALED_CELL);
    this.cellNum2 = new OverlayImage(new TextImage(Integer.toString(2),
        TEXT_SIZE, TEXT_COLOR2), REVEALED_CELL);
    this.cellGrey = REVEALED_CELL;
    this.cellMine = MINE_CELL;
  }

  // to test the make grid method
  void testMakeGrid(Tester t) {
    this.initialData();
    t.checkExpect(this.game1.makeGrid(), this.gridGame1);
    t.checkExpect(this.game2.makeGrid(), this.gridGame2);
  }

  // to test the make row method
  void testMakeRow(Tester t) {
    this.initialData();
    t.checkExpect(this.game1.makeRow(), this.rowAGame1);
    t.checkExpect(this.game2.makeRow(), this.rowAGame2);
  }


  // to test the get cell method
  void testGetCell(Tester t) {
    this.initialData();
    t.checkExpect(this.game3.getCell(0, 0), this.cell00);
    t.checkExpect(this.game3.getCell(0, 1), this.cell01);
    t.checkExpect(this.game3.getCell(0, 2), this.cell02);
  }


  // to test the update grid method
  void testUpdateGrid(Tester t) {
    this.initialData();
    this.gameE1.updateGrid();
    t.checkExpect(this.gridE1, this.game1.grid);
    this.initialData();
    this.gameE2.updateGrid();
    t.checkExpect(this.gameE2.grid, this.gameE2Update.grid);
  }


  // to test the update neighbors method
  void testUpdateNeighbors(Tester t) {
    this.initialData();
    this.gameE1.updateNeighbors(cellE1, 0, 0);
    t.checkExpect(cellE1.neighbors, 
        new ArrayList<Cell>(Arrays.asList(null, cellE2, null, cellE3, null, cellE4, null, null)));
    this.gameE1.updateNeighbors(cellE2, 0, 1);
    t.checkExpect(cellE2.neighbors, 
        new ArrayList<Cell>(Arrays.asList(cellE1, null, null, cellE4, null, null, null, cellE3)));
    this.gameE1.updateNeighbors(cellE3, 1, 0);
    t.checkExpect(cellE3.neighbors, 
        new ArrayList<Cell>(Arrays.asList(null, cellE4, cellE1, null, null, null, cellE2, null)));
  }


  // to test the update neighbor method
  void testUpdateNeighbor(Tester t) {
    this.initialData();
    this.gameE1.updateNeighbor(cellE1, 0, 1);
    t.checkExpect(cellE1.neighbors, new ArrayList<Cell>(Arrays.asList(this.cellE2)));
    this.gameE1.updateNeighbor(cellE1, 0, -1);
    t.checkExpect(cellE1.neighbors, new ArrayList<Cell>(Arrays.asList(cellE2, null)));
    this.gameE1.updateNeighbor(cellE1, 1, 0);
    t.checkExpect(cellE1.neighbors, new ArrayList<Cell>(Arrays.asList(cellE2, null, cellE3)));
  }


  // to test the update (a single cell) method
  void testUpdate(Tester t) {
    this.initialData();
    cellA.update(cellA);
    t.checkExpect(cellA.neighbors, new ArrayList<Cell>(Arrays.asList(cellA)));
    cellA.update(cell00);
    t.checkExpect(cellA.neighbors, new ArrayList<Cell>(Arrays.asList(cellA, cell00)));
    cellA.update(cell10);
    t.checkExpect(cellA.neighbors, new ArrayList<Cell>(Arrays.asList(cellA, cell00, cell10)));
    cellA.update(null);
    t.checkExpect(cellA.neighbors,new ArrayList<Cell>(Arrays.asList(cellA, cell00, cell10, null)));
  }


  // to test the new get method
  void testNewGet(Tester t ) {
    this.initialData();
    // case1
    t.checkExpect(this.game3.newGet(-1, -1), null);
    t.checkExpect(this.game3.newGet(5, 4), null);
    // case2
    t.checkExpect(this.game3.newGet(1, 1), this.cell11);
    t.checkExpect(this.game3.newGet(2, 0), this.cell20);
  }


  // to test the add mines method
  void testAddMines(Tester t) {
    this.initialData();
    t.checkExpect(this.game2.getCell(0, 0).isMine, false); 
    t.checkExpect(this.game2.getCell(0, 1).isMine, true); 
    t.checkExpect(this.game2.getCell(0, 2).isMine, false);
    t.checkExpect(this.game2.getCell(1, 0).isMine, true); 
    t.checkExpect(this.game2.getCell(1, 1).isMine, false);
    t.checkExpect(this.game2.getCell(1, 2).isMine, false);
    t.checkExpect(this.game2.getCell(2, 0).isMine, false);
    t.checkExpect(this.game2.getCell(2, 1).isMine, false);
    t.checkExpect(this.game2.getCell(2, 2).isMine, false);
    // for game2, there are supposed to be 2 mines, 
    // and our tests show the isMine field is true for 2 places (which means it does have 2 mines)
    this.initialData();
    MineGame mineTest = new MineGame(2, 2, 0, new Random(1), this.gridE1);
    mineTest.addMines();
    t.checkExpect(mineTest.getCell(0, 0).isMine, false);
    t.checkExpect(mineTest.getCell(0, 1).isMine, false);
    t.checkExpect(mineTest.getCell(1, 0).isMine, false);
    t.checkExpect(mineTest.getCell(1, 1).isMine, false);
    // similarly, this MineGame with no mines is correct

  }

  
  // to test the count neighboring mines method
  void testNeighborMines(Tester t) {
    this.initialData();
    t.checkExpect(this.game2.getCell(0, 0).mines, 2);
    t.checkExpect(this.game2.getCell(0, 1).mines, 1); 
    t.checkExpect(this.game2.getCell(0, 2).mines, 1);
    t.checkExpect(this.game2.getCell(1, 0).mines, 1); 
    t.checkExpect(this.game2.getCell(1, 1).mines, 2);
    t.checkExpect(this.game2.getCell(1, 2).mines, 1);
    t.checkExpect(this.game2.getCell(2, 0).mines, 1);
    t.checkExpect(this.game2.getCell(2, 1).mines, 1);
    t.checkExpect(this.game2.getCell(2, 2).mines, 0);
  }

  
  // to test the draw cell method
  void testDrawCell(Tester t) {
    this.initialData();
    t.checkExpect(this.cell00.drawCell(), this.cellBase);
    t.checkExpect(this.cell01.drawCell(), this.cellFlagged);
    t.checkExpect(this.cell02.drawCell(), this.cellNum1);
    t.checkExpect(this.cell10Dupe.drawCell(), this.cellMine);
    t.checkExpect(this.cell11.drawCell(), this.cellNum2);
    t.checkExpect(this.cell22.drawCell(), this.cellGrey);
  }

  
  // to test the draw left case method
  void testDrawLeftCase(Tester t) {
    this.initialData();
    t.checkExpect(this.cell02.drawLeftCase(), this.cellNum1);
    t.checkExpect(this.cell10Dupe.drawLeftCase(), this.cellMine);
    t.checkExpect(this.cell11.drawLeftCase(), this.cellNum2);
    t.checkExpect(this.cell22.drawLeftCase(), this.cellGrey);

  }


  // to test the count mines method
  void testCountMines(Tester t) {
    this.initialData();
    // the placements of the mines found when checking add mines
    // cell that has no mines around it
    t.checkExpect(this.game2.getCell(2, 2).countMines(), 0);
    // cell that has 1 mines around it
    t.checkExpect(this.game2.getCell(0, 1).countMines(), 1);
    // cell that has 2 mines around it
    t.checkExpect(this.game2.getCell(1, 1).countMines(), 2);
  }


  // to test the makeScene method
  void testMakeScene(Tester t) {
    // a 2 x 2 default grid
    this.initialData();
    WorldScene scene1 = this.game1.getEmptyScene();
    scene1.placeImageXY(this.cellBase, 25, 25);
    scene1.placeImageXY(this.cellBase, 75, 25);
    scene1.placeImageXY(this.cellBase, 25, 75);
    scene1.placeImageXY(this.cellBase, 75, 75);  
    t.checkExpect(this.game1.makeScene(), scene1);

    // a 3 x 3 grid with different cases
    this.initialData();
    WorldScene scene2 = emptyScene;
    scene2.placeImageXY(this.cellBase, 25, 25);
    scene2.placeImageXY(this.cellFlagged, 75, 25);
    scene2.placeImageXY(this.cellNum1, 125, 25);
    scene2.placeImageXY(this.cellBase, 25, 75);
    scene2.placeImageXY(this.cellNum2, 75, 75);
    scene2.placeImageXY(this.cellNum1, 125, 75);
    scene2.placeImageXY(this.cellNum1, 25, 125);
    scene2.placeImageXY(this.cellNum1, 75, 125);
    scene2.placeImageXY(this.cellGrey, 125, 125);
    t.checkExpect(this.game3.makeScene(), scene2);

  }


  // to test the draw grid method
  void testDrawGrid(Tester t) {
    // a 2 x 2 default grid
    this.initialData();
    WorldScene scene1 = this.game1.getEmptyScene();
    scene1.placeImageXY(this.cellBase, 25, 25);
    scene1.placeImageXY(this.cellBase, 75, 25);
    scene1.placeImageXY(this.cellBase, 25, 75);
    scene1.placeImageXY(this.cellBase, 75, 75);  
    t.checkExpect(this.game1.drawGrid(this.emptyScene), scene1);

    // a 3 x 3 grid with different cases
    this.initialData();
    WorldScene scene2 = emptyScene;
    scene2.placeImageXY(this.cellBase, 25, 25);
    scene2.placeImageXY(this.cellFlagged, 75, 25);
    scene2.placeImageXY(this.cellNum1, 125, 25);
    scene2.placeImageXY(this.cellBase, 25, 75);
    scene2.placeImageXY(this.cellNum2, 75, 75);
    scene2.placeImageXY(this.cellNum1, 125, 75);
    scene2.placeImageXY(this.cellNum1, 25, 125);
    scene2.placeImageXY(this.cellNum1, 75, 125);
    scene2.placeImageXY(this.cellGrey, 125, 125);
    t.checkExpect(this.game3.drawGrid(this.emptyScene), scene2);

  }


  // to test the onMouseClicked handler method
  // covers any clicking situation
  void testOnMouseClicked(Tester t) {
    this.initialData();

    // Left case
    // click on a flagged cell, nothing changes
    this.game3.onMouseClicked(new Posn(60, 10), "LeftButton");
    t.checkExpect(this.game3.getCell(0,1).mode, 2);
    // click on an already revealed cell, nothing changes
    this.game3.onMouseClicked(new Posn(110, 0), "LeftButton");
    t.checkExpect(this.game3.getCell(0,2).mode, 1);
    // click on a non-revealed cell: a mine, a numbered, a non-mine-surround cell
    this.game1A.onMouseClicked(new Posn(55, 15), "LeftButton"); // a mine
    t.checkExpect(this.game1A.getCell(0, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(0, 1).mode, 1);
    t.checkExpect(this.game1A.getCell(1, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(1, 1).mode, 1);
    this.game1A.onMouseClicked(new Posn(0, 0), "LeftButton"); // a numbered
    t.checkExpect(this.game1A.getCell(0, 0).mode, 1);
    this.game2.onMouseClicked(new Posn(125, 125), "LeftButton"); // a non-mine-surround cell
    t.checkExpect(this.game2.getCell(0,0).mode, -2);
    t.checkExpect(this.game2.getCell(0,1).mode, -2);
    t.checkExpect(this.game2.getCell(0,2).mode, -2);
    t.checkExpect(this.game2.getCell(1,0).mode, -2);
    t.checkExpect(this.game2.getCell(1,1).mode, 1);
    t.checkExpect(this.game2.getCell(1,2).mode, 1);
    t.checkExpect(this.game2.getCell(2,0).mode, -2);
    t.checkExpect(this.game2.getCell(2,1).mode, 1);
    t.checkExpect(this.game2.getCell(2,2).mode, 1);

    // right case
    // flagged to normal
    this.game3.onMouseClicked(new Posn(60, 10), "RightButton");
    t.checkExpect(this.game3.getCell(0,1).mode, -2);
    // normal to flagged
    this.game3.onMouseClicked(new Posn(0, 10), "RightButton");
    t.checkExpect(this.game3.getCell(0,0).mode, 2);
    // revealed to nothing changes
    this.game3.onMouseClicked(new Posn(110, 0), "RightButton");
    t.checkExpect(this.game3.getCell(0,2).mode, 1);
  }

  
  // to test the update method when someone uses right click (on any Cell)
  void testUpdateRightClicked(Tester t) {
    this.initialData();
    // clicking something that was already flagged (2)
    this.cell01.updateRightClicked();
    t.checkExpect(this.cell01.mode, -2); // un-flags it
    // clicking something that has not been clicked (-2)
    this.cell01.updateRightClicked();
    t.checkExpect(this.cell01.mode, 2); // flags it
    // clicking something that is already revealed (1)
    this.cell02.updateRightClicked();
    t.checkExpect(this.cell02.mode, 1); // nothing happens
  }


  // to test the update method when someone uses left click (on a non-mine Cell)
  void testUpdateLeftClicked(Tester t) {
    // not testing this method on a cell that is flagged (mode is 1)
    // bc although doesn't have restrictions about it, it should never reach 
    // that case since it's mode was checked before reaching this method
    this.initialData();
    // the mode will always be updated - check
    // when it is a mine
    this.game1A.getCell(0,1).updateLeftClicked();
    t.checkExpect(this.game1A.getCell(0,1).mode, 1);
    // when it does have surrounding mines
    this.game1A.getCell(0,0).updateLeftClicked();
    t.checkExpect(this.game1A.getCell(0,0).mode, 1);
    // when it's not a mine and has no surrounding mines (flood-fill)
    // check that it updates the correct cells
    this.game2.getCell(2,2).updateLeftClicked();
    t.checkExpect(this.game2.getCell(0,0).mode, -2);
    t.checkExpect(this.game2.getCell(0,1).mode, -2);
    t.checkExpect(this.game2.getCell(0,2).mode, -2);
    t.checkExpect(this.game2.getCell(1,0).mode, -2);
    t.checkExpect(this.game2.getCell(1,1).mode, 1);
    t.checkExpect(this.game2.getCell(1,2).mode, 1);
    t.checkExpect(this.game2.getCell(2,0).mode, -2);
    t.checkExpect(this.game2.getCell(2,1).mode, 1);
    t.checkExpect(this.game2.getCell(2,2).mode, 1);
  }

  // to test the click left method
  // testing under the assumption that reached this method under
  // conditions where this cell is still click-able (i.e. mode is -2)
  void testClickLeft(Tester t) {
    this.initialData();
    // when the cell is not a mine
    this.game1A.clickLeft(this.game1A.getCell(0,0));
    t.checkExpect(this.game1A.getCell(0, 0).mode, 1);
    // when you win
    this.game1A.clickLeft(this.game1A.getCell(1,0));
    t.checkExpect(this.game1A.getCell(1, 0).mode, 1);
    t.checkExpect(this.game1A.getCell(0, 1).mode, -2);
    t.checkExpect(this.game1A.getCell(1, 1).mode, -2); // check mines updated
    WorldScene scene1 = this.game1A.getEmptyScene(); // check the WorldScene is updated
    scene1.placeImageXY(this.cellNum2, 25, 25);
    scene1.placeImageXY(this.cellBase, 75, 25);
    scene1.placeImageXY(this.cellNum2, 25, 75);
    scene1.placeImageXY(this.cellBase, 75, 75);  
    scene1.placeImageXY(new TextImage("Good job! You won", Color.red), 50, 50);
    t.checkExpect(this.game1A.makeScene(), scene1);

    // when the cell is a mine (when you lose)
    this.initialData();
    this.game1A.clickLeft(this.game1A.getCell(0,1));
    t.checkExpect(this.game1A.getCell(0, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(0, 1).mode, 1);
    t.checkExpect(this.game1A.getCell(1, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(1, 1).mode, 1); // check that mines are updated
    WorldScene scene2 = this.game1A.getEmptyScene(); // check the WorldScene is updated
    scene2.placeImageXY(this.cellBase, 25, 25);
    scene2.placeImageXY(this.cellMine, 75, 25);
    scene2.placeImageXY(this.cellBase, 25, 75);
    scene2.placeImageXY(this.cellMine, 75, 75);  
    scene2.placeImageXY(new TextImage("Oh no! You lost", Color.red), 50, 50);
    t.checkExpect(this.game1A.makeScene(), scene2);
  }

  // to test method click (all) mines
  void testClickMine(Tester t) {
    // check that it only updates the cells that are mines
    this.initialData();
    this.game1.clickMine();
    t.checkExpect(this.game1A.getCell(0, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(0, 1).mode, -2);
    t.checkExpect(this.game1A.getCell(1, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(1, 1).mode, -2);
    this.initialData();
    // checks that it correctly updates the cells that are mines
    this.game1A.clickMine();
    t.checkExpect(this.game1A.getCell(0, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(0, 1).mode, 1);
    t.checkExpect(this.game1A.getCell(1, 0).mode, -2);
    t.checkExpect(this.game1A.getCell(1, 1).mode, 1);
  }

  // to test if conditions = win game?
  void testWinGame(Tester t) {
    this.initialData();
    // when the player has not finished the game (with no mines)
    t.checkExpect(this.game1.winGame(), false);
    // when the player flat out loses (although method technically wouldn't reach this case)
    t.checkExpect(this.game1A.winGame(), false);
    // when the player has a game of all mines (automatically wins)
    MineGame gameMine = new MineGame(2, 2, 4);
    t.checkExpect(gameMine.winGame(), true);
    // when the player got all the mines (win)
    this.cell00.mode = 1; // making the non-mine cell clicked
    this.cell01.mode = -2; // making the mine cell un-clicked
    t.checkExpect(this.game3.winGame(), true);
  }

  
  // to test getting the clicked cell method
  void testClickCell(Tester t) {
    this.initialData();
    t.checkExpect(this.game1A.clickCell(new Posn(0, 0)), this.game1A.getCell(0, 0));
    t.checkExpect(this.game1A.clickCell(new Posn(49, 59)), this.game1A.getCell(1, 0));
    t.checkExpect(this.game1A.clickCell(new Posn(60, 49)), this.game1A.getCell(0, 1));
    t.checkExpect(this.game1A.clickCell(new Posn(50, 0)), this.game1A.getCell(0, 1));
    t.checkExpect(this.game1A.clickCell(new Posn(100, 100)), this.game1A.getCell(1, 1));
  }

  
  // to test getting the corresponding position of one orientation method
  void testGetPosn(Tester t) {
    this.initialData();
    t.checkExpect(this.game1A.getPosn(-2, 2), 0);
    t.checkExpect(this.game1A.getPosn(0, 2), 0);
    t.checkExpect(this.game1A.getPosn(49, 2), 0);
    t.checkExpect(this.game1A.getPosn(50, 2), 1);
    t.checkExpect(this.game1A.getPosn(99, 2), 1);
    t.checkExpect(this.game1A.getPosn(100, 2), 1);
  }

  
  // to test the make last scene method
  void testLastScene(Tester t) {
    // 2 x 2 game - lost
    // (mines are at (0, 1) and (1, 1))
    this.initialData();
    this.game1A.getCell(0, 1).mode = 1;
    this.game1A.getCell(1, 1).mode = 1;
    WorldScene scene1 = this.game1.getEmptyScene();
    scene1.placeImageXY(this.cellBase, 25, 25);
    scene1.placeImageXY(this.cellMine, 75, 25);
    scene1.placeImageXY(this.cellBase, 25, 75);
    scene1.placeImageXY(this.cellMine, 75, 75);  
    scene1.placeImageXY(new TextImage("Oh no! You lost", Color.red), 50, 50);
    t.checkExpect(this.game1.drawGrid(this.emptyScene), scene1);

    // a 3 x 3 game - won
    this.initialData();
    WorldScene scene2 = emptyScene;
    scene2.placeImageXY(this.cellBase, 25, 25);
    scene2.placeImageXY(this.cellFlagged, 75, 25);
    scene2.placeImageXY(this.cellNum1, 125, 25);
    scene2.placeImageXY(this.cellBase, 25, 75);
    scene2.placeImageXY(this.cellNum2, 75, 75);
    scene2.placeImageXY(this.cellNum1, 125, 75);
    scene2.placeImageXY(this.cellNum1, 25, 125);
    scene2.placeImageXY(this.cellNum1, 75, 125);
    scene2.placeImageXY(this.cellGrey, 125, 125);
    scene2.placeImageXY(new TextImage("Good job! You won", Color.red), 75, 75); // make variable?
    t.checkExpect(this.game3.drawGrid(this.emptyScene), scene2);

  }

}

// to run the game
class RunGameWorld {

  void testGo(Tester t) {

    MineGame myWorld = new MineGame(10, 22, 10, new Random(1));

    myWorld.bigBang(myWorld.column * 50, myWorld.row * 50);
  }

}

