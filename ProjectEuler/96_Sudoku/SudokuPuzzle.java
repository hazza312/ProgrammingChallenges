class SudokuPuzzle {

  static final int SUDOKU_SIZE = 9;
  static final int BOX_SIZE = 3;

  private int[][] puzzle;

  SudokuPuzzle() {
    puzzle = new int[SUDOKU_SIZE][SUDOKU_SIZE];
  }
  
  private boolean rowIsValid(int x, int y) {
    for (int i = 0; i < SUDOKU_SIZE; i++) {
      if (puzzle[y][i] == puzzle[y][x] && i != x) {
        return false;
      }
    }
    
    return true;  
  }
  
  private boolean columnIsValid(int x, int y) {
    for (int i = 0; i < SUDOKU_SIZE; i++) {
      if (puzzle[i][x] == puzzle[y][x] && i != y) {
        return false;
      }
    }
    
    return true;  
  }
  
  private boolean boxIsValid(int x, int y) {
    int startX = (x / BOX_SIZE) * BOX_SIZE;
    int startY = (y / BOX_SIZE) * BOX_SIZE;
    
    for (int i = startX; i < startX + BOX_SIZE; i++) {
      for (int j = startY; j < startY + BOX_SIZE; j++) {
        if (puzzle[j][i] == puzzle[y][x] && i != x && j != y) {
          return false;
        }
      }
    }
    
    return true;    
  }

  
  private boolean solve(int x, int y) {  
    if (y == SUDOKU_SIZE) {
      return true;
    }

    int nextX = (x+1) % SUDOKU_SIZE;
    int nextY = (nextX == 0) ? y+1 : y; 
    
    if (puzzle[y][x] != 0) {
      return solve(nextX, nextY);
    }
     
    for (int i = 1; i <= SUDOKU_SIZE; i++) {
      puzzle[y][x] = i;

      if (rowIsValid(x,y) && columnIsValid(x,y) && boxIsValid(x,y) && solve(nextX, nextY)) {
        return true;
      }
      
      puzzle[y][x] = 0;      
    }

    return false;  
  }

  public boolean solve() {
    return solve(0, 0);
  } 

  public void set(int x, int y, int value){
    puzzle[y][x] = value;
  }  

  public int get(int x, int y) {
    return puzzle[y][x];
  } 

}
