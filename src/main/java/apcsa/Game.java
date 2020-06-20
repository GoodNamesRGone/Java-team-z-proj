package apcsa;
/* Game Class Starter File
 * Last Edit: 5/25/2020
 */

import javax.crypto.spec.GCMParameterSpec;

public class Game {
  private int lives;
  private String instructions = "images/textinstruc.jpg";
  private int score;
  private int pewpew;
  private final String meteorPic = "images/meteor.png";
  private final String background = "images/space.png";
  //private final String titleGif = "images/maskglitch.gif";
  private final String userLaser = "images/cool laser.png";
  private final Grid grid;
  private int userRow;
  private final int userCol;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private final String userPic = "images/helmet user.png";
  private final String getPic = "images/get.gif";
  private String userInput = "Yes";
  private int x = 0;
  private String cycleGif = "gif/frame(" + x + ").png" ;
  private int allTimeScore = 0;
  private McImage textIns; 
  public Game() {

    grid = new Grid(6, 10);// can edit
    // grid.setBackground(imgName);
    userRow = 3;
    userCol = 0;
    pewpew = 7;
    grid.fullscreen();
    msElapsed = 0;
    lives = 5;
    score = 0;
    updateTitle();
   grid.setMultiCellImage(instructions, new Location(0, 0), 5, 3);
    
  }

  

  public void play() {

    // titlescreen method
    titleScreen();

    // start the game
    // grid.setMovableBackground(meteorPic, 0, 0, 0.1, 0.1);//offset can be 0,0.
    // Background tbd
    while (userInput.equals("Yes")) {
      while (!isGameOver()) {
        grid.pause(100);
        handleKeyPress();
        if (msElapsed % 300 == 0) {
          handleCollision();
          scrollLeft();
          populateRightEdge();
          score += 10;
        }
        updateTitle();

        msElapsed += 100;
      }
      
        userInput = grid.showInputDialog("Would you like to continue? Yes or No?");
        lives += 5;
        score -= 5000;
        pewpew += 7;
    }
    grid.getFrame().dispose();
  }


  public void titleScreen() {
    boolean start = grid.checkLastKeyPressed() == -1;
    while (start) {
     cycleGif = "gif/frame(" + x + ").png" ;
        x++;
      grid.pause(100);
        if(x >= 123){
          x=0;
        }
      grid.setBackground(cycleGif);
      grid.setMultiCellImage(instructions, new Location(0, 0), 6, 3);

      start = grid.checkLastKeyPressed() == -1;
    }
    
    grid.removeBackground();
    grid.removeMultiCellImage();
  }
 
  
  

  public void handleKeyPress() {

    // check last key pressed
    final int key = grid.checkLastKeyPressed();
    System.out.println(key);

    // set "w" key to move the plane up
    if (key == 87 && userRow != 0) {
      // check case where out of bounds
      if (meteorPic.equals(grid.getImage(new Location(userRow - 1, userCol)))) {
        lives--;
      }
      // change the field for userrow

      userRow--;

      // shift the user picture up in the array
      final Location loc = new Location(userRow, 0);
      grid.setImage(loc, userPic);// insert user pics

      final Location oldLoc = new Location(userRow + 1, 0);
      grid.setImage(oldLoc, null);

    }
    // if I push down arrow, then plane goes down
    // "s" key
    if (key == 83 && userRow != grid.getNumRows() - 1) {
      // check case where out of bounds
      if (meteorPic.equals(grid.getImage(new Location(userRow + 1, userCol)))) {
        lives--;
      }
      // change the field for userrow
      userRow++;
      System.out.println(userRow);
      // shift the user picture up in the array
      final Location loc = new Location(userRow, 0);
      grid.setImage(loc, userPic);// insert user pics

      final Location oldLoc = new Location(userRow - 1, 0);
      grid.setImage(oldLoc, null);
    }

    if (key == 32 && pewpew != 0) {// look for spacebar number
      final Location laserLoc = new Location(userRow, 1);
      grid.setImage(laserLoc, userLaser);// find a user laser pic
      pewpew--;
    }

  }

  public void populateRightEdge() {
    // get the last column
    final int lastCol = grid.getNumCols() - 1;
    final int lastRow = grid.getNumRows() - 1;
    // loop through last column
    for (int r = 0; r <= lastRow; r++) {
      // find location for each cell in last row
      final Location loc = new Location(r, lastCol);

      // get a random number to pct of appearances
      final double rando = Math.random();
      final double thresh = 0.2;
      // decide if an object should appear
      if (rando < thresh) {

        grid.setImage(loc, meteorPic);// if random thing happens this appears. substitute it for laser

      }
    }
  }

  public void scrollLeft() {

    // get the last column
    final int lastCol = grid.getNumCols() - 1;
    final int lastRow = grid.getNumRows() - 1;

    // looping through each column
    for (int c = 0; c <= lastCol - 1; c++) {

      // right column and left column
      final int rightCol = c + 1;
      final int leftCol = c;

      // loop through each row
      for (int r = 0; r <= lastRow; r++) {

        // move items from right to left
        final Location rightLoc = new Location(r, rightCol);
        final Location leftLoc = new Location(r, leftCol);

        // copy a picture. Copying from right to left

        final String rightPic = grid.getImage(rightLoc);
        grid.setImage(leftLoc, rightPic);

        // erase the old image
        grid.setImage(rightLoc, null);
      }
    }
    final Location loc = new Location(userRow, 0);
    grid.setImage(loc, userPic);

  }

  public void handleCollision() {
    final Location frontLoc = new Location(userRow, userCol + 1);
    // int lastCol = grid.getNumCols()-1;
    // int lastRow = grid.getNumRows() - 1;
    // if (loc.getCol() == lastCol || loc.getRow() == lastRow){
    // lastCol = lastCol;
    // lastRow = lastRow;
    // }
    if (meteorPic.equals(grid.getImage(frontLoc))) {
      lives--;
    }
  }

  public int getPewpew() {
    return pewpew;
  }

  public int getScore() {
    return score;
  }

  public int getLives() {
    return lives;
  }

  public void updateTitle() {
    grid.setTitle("AnZaiety:  " + getScore() + " with " + getLives() + " lives and " + getPewpew() + " laser.");
  }

  public boolean isGameOver() {
     if (getScore() == 1500) {
      grid.showMessageDialog(highScoreCheck() + " Yayyyyy you did it!");
      return true;
    } else if (getLives() == 0) {
      grid.showMessageDialog(highScoreCheck() + " Game over! Looks like the hatchet buried you this time... ");
      return true;
    } else {
      return false;
    }
  }

  public String highScoreCheck() {
    String status = "Your grudge got the best of you...Try mastering the mask's powers to beat your highscore of " + allTimeScore;
    ;
    if(getScore()>allTimeScore){
      status = "You beat your all time High score of " + allTimeScore + " with a score of " + getScore() + " !";
      allTimeScore = getScore();
    }
    else if (getScore()==allTimeScore){
      status = "Almost there...You tied your highscore of " + getScore() + " !";

    }
    return status;
  }

  public static void main(String[] args) {
    Game game = new Game();
    game.play();   
  }
}