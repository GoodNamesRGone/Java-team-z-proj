package apcsa;
/* Game Class Starter File
 * Last Edit: 5/25/2020
 */

public class Game {
  private int lives;
  private int score;
  private String meteorPic = "images/meteor.png";
  private String userLaser = "images/cool laser.png";
  private Grid grid;
  private int userRow;
  private int userCol;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String userPic = "images/helmet user.png";
  private String getPic = "images/get.gif";
  public Game() {

    grid = new Grid(5, 10);// can edit
    //grid.setBackground(imgName);
    userRow = 3;
    userCol = 0;
    grid.fullscreen(); 
    msElapsed = 0;
    lives = 5;
    score = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), userPic);
  }
  
  public void play() {
    
    titleScreen();

    //start the game
    //grid.setMovableBackground(meteorPic, 0, 0, 0.1, 0.1);//offset can be 0,0. Background tbd
    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
  }

  public void titleScreen() {
    boolean start = grid.checkLastKeyPressed() == 13;

    while(!start){
      //render in the gif
      
      start = grid.checkLastKeyPressed() == 13;
        }
    
  }
  
  public void handleKeyPress(){

    //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    //set "w" key to move the plane up
    if(key == 87 && userRow != 0){
        //check case where out of bounds

        //change the field for userrow

        userRow--;

        //shift the user picture up in the array
        Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic);//insert user pics
        
        Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
//"s" key
    if(key == 83 && userRow != grid.getNumRows()-1){
      //check case where out of bounds
      //change the field for userrow
      userRow++;
      System.out.println(userRow);
      //shift the user picture up in the array
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, userPic);//insert user pics
      
      Location oldLoc = new Location(userRow -1, 0);
      grid.setImage(oldLoc, null);
    }

    if(key == 32) {//look for spacebar number
      Location laserLoc = new Location(userRow, 1);
      grid.setImage(laserLoc, userLaser);//find a user laser pic

    }



  }
  
  
  public void populateRightEdge(){
    //get the last column
      int lastCol = grid.getNumCols()-1;
      int lastRow = grid.getNumRows() -1;
    //loop through last column
    for(int r = 0; r <= lastRow; r++) {
      //find location for each cell in last row
      Location loc = new Location(r, lastCol);


    //get a random number to pct of appearances
   double rando = Math.random();
   double thresh = 0.2;
    //decide if an object should appear
      if(rando < thresh){

          grid.setImage(loc, meteorPic);//if random thing happens this appears. substitute it for laser


      }
    }
  }
  public void scrollLeft(){

    //get the last column
    int lastCol = grid.getNumCols()-1;
    int lastRow = grid.getNumRows() - 1;

    //looping through each column
    for(int c = 0; c <=lastCol - 1; c++){

        //right column and left column
        int rightCol = c + 1;
        int leftCol = c;

        //loop through each row
        for(int r = 0; r <= lastRow; r++) {

        //move items from right to left
          Location rightLoc = new Location(r, rightCol);
          Location leftLoc = new Location(r, leftCol);

         
		//copy a picture. Copying from right to left
         
          String rightPic = grid.getImage(rightLoc);
           grid.setImage(leftLoc, rightPic);


          //erase the old image
          grid.setImage(rightLoc, null);
        }
  }
       Location loc = new Location(userRow, 0);
        grid.setImage(loc, userPic);


}
  
    public void handleCollision() {
      // Location frontLoc = new Location(userRow, userCol+1);
      // int lastCol = grid.getNumCols()-1;
      // int lastRow = grid.getNumRows() - 1;
      // if (loc.getCol() == lastCol || loc.getRow() == lastRow){
      //   lastCol = lastCol;
      //   lastRow = lastRow;
      // }
      // if (grid.getImage(loc) == lastRow) {
      //   lives--;
      // }
    }
  
  
  public int getScore() {
    return 0;
  }
  
  public void updateTitle() {
    grid.setTitle("Game:  " + getScore());
  }
  
  public boolean isGameOver() {
    return false;
  }
    
  public static void main(String[] args) {
    Game game = new Game();
    game.play();   
  }
}