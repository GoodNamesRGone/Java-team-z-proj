/* Game Class Starter File
 * Last Edit: 5/25/2020
 */

public class Game {
  private String laserPic = "images/laser.png";
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String userPic = "images/user.gif";
  private String getPic = "images/get.gif";
  public Game() {

    grid = new Grid(5, 10);// can edit
    //grid.setBackground(imgName);
    userRow = 3;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), userPic);
  }
  
  public void play() {
    
    //title screen

    //start the game
    grid.setMovableBackground(laserPic, 0, 0, 0.1, 0.1);//offset can be 0,0. Background tbd
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
  
  public void handleKeyPress(){

    //check last key pressed
    int key = grid.checkLastKeyPressed();
    System.out.println(key);

    //set "w" key to move the plane up
    if(key == 87 && userRow == 0){
        //check case where out of bounds

        //change the field for userrow

        userRow--;

        //shift the user picture up in the array
        Location loc = new Location(userRow, 0);
        grid.setImage(loc, "user.gif");//insert user pics
        
        Location oldLoc = new Location(userRow+1, 0);
        grid.setImage(oldLoc, null);

  }
    //if I push down arrow, then plane goes down
//"s" key
    if(key == 83 && userRow == 0){
      //check case where out of bounds
      //change the field for userrow
      userRow--;
      //shift the user picture up in the array
      Location loc = new Location(userRow, 0);
      grid.setImage(loc, "user.gif");//insert user pics
    }  
      Location oldLoc = new Location(userRow+1, 0);
      grid.setImage(oldLoc, null);

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
   double thresh = 0.5;
    //decide if an object should appear
      if(rando < thresh){

          grid.setImage(loc, laserPic);//if random thing happens this appears. substitute it for laser


      }
    }
  }
  public void scrollLeft(){

    //get the last column
    int lastCol = grid.getNumCols()-1;
    int lastRow = grid.getNumRows() - 1;

    //looping through each column
    for(int c = 0; c <=lastCol; c++){

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
}
  
    public void handleCollision(Location loc) {
      int lastCol = grid.getNumCols()-1;
      int lastRow = grid.getNumRows() - 1;
      if (loc.getCol() == lastCol || loc.getRow() == lastRow){
        lastCol = lastCol;
        lastRow = lastRow;
      }
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