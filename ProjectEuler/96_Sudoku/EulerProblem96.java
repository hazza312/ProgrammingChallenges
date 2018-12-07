import java.util.Scanner;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

class EulerProblem96 {

  // hk '18

  static final char HORIZONTAL_BAR  = '\u2500';
  static final char VERTICAL_BAR    = '\u2502';
  static final char CROSS_BAR       = '\u253C';

  PrintStream out;

  EulerProblem96() {
    out = new PrintStream(System.out);
  }
  
  SudokuPuzzle readPuzzle(Scanner in) {
    SudokuPuzzle puzzle = new SudokuPuzzle();
    in.useDelimiter("");
    
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        puzzle.set(x, y, in.nextInt());
      }
      
      in.nextLine();
    }  

    return puzzle;
  }
  
  void printPuzzle(SudokuPuzzle puzzle) {
    for (int y = 0; y < 9; y++) {
      for (int x = 0; x < 9; x++) {
        out.printf("%d", puzzle.get(x, y));
        if (x == 2 || x == 5) out.print(VERTICAL_BAR);
      }
      out.println();
      
      if (y == 2 || y == 5) {
        for (int i = 0; i < 9; i++) {
          out.print(HORIZONTAL_BAR);
          if (i == 2 || i == 5) out.print(CROSS_BAR);
        }
        out.println();   
      }
    }
    out.println();
  }

  int attemptPuzzle(int puzzleId, SudokuPuzzle puzzle) {
    long startTime = System.currentTimeMillis();

    if (puzzle.solve()) {
      long timeTakenMs = (System.currentTimeMillis() - startTime);
      out.printf("Puzzle %d was solved in %dms:\n", puzzleId, timeTakenMs);
      printPuzzle(puzzle);           
    } else {
      out.printf("Puzzle %d could not be solved\n", puzzleId);
    }

    return puzzle.get(0,0)*100 + puzzle.get(1,0)*10 + puzzle.get(2,0);
  }

  int calculateSum(Scanner fileScanner) {
    fileScanner.useDelimiter("Grid \\d+[\r\n]+");
    int sum = 0;

    for (int puzzleId = 1; fileScanner.hasNext(); puzzleId++) {
      sum += attemptPuzzle(puzzleId, readPuzzle(new Scanner(fileScanner.next())));      
    }

    return sum;
  }
  
  void start(String[] args) {
    if (args.length != 1) {
      out.println("usage: java Sudoku <input file>");
      System.exit(1);
    }

    try {
      File puzzleFile = new File(args[0]); 
      int sum = calculateSum(new Scanner(puzzleFile));
      out.printf("Sum of all puzzles = %d\n", sum);

    } catch (IOException e) {
      out.printf("Could not open file: %s\n", args[0]);
      System.exit(1);
    } 
  }
  
  public static void main(String[] args) {
    new EulerProblem96().start(args);
  }
}
