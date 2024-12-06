/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #9
 * 1 - 5026231010 - Daniel Setiawan Yulius Putra
 * 2 - 5026231160 - Muhammad Gandhi Taqi Utomo
 * 3 - 5026231184 - Dzaky Ahmad
 */
package sudoku;
import java.util.Random;
/**
 * The Sudoku number puzzle to be solved.
 */
public class Puzzle {
   // All variables have package access
   int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

   // Constructor
   public Puzzle() {
      super();
   }

   // Constructor to initialize the puzzle with a certain number of cells to guess
   public Puzzle(int cellsToGuess) {
      super();
      newPuzzle(cellsToGuess); // Call the method to generate the puzzle
   }

   // Generate a new randomized puzzle
   public void newPuzzle(int cellsToGuess) {
      numbers = generateFullSudoku(); // Generate a complete valid Sudoku board
      randomizeBoard();               // Randomize the board
      removeNumbers(cellsToGuess);    // Remove numbers to create the puzzle
   }

   // Step 1: Generate a complete valid Sudoku board
   private int[][] generateFullSudoku() {
      int[][] board = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
      solveSudoku(board, 0, 0);
      return board;
   }

   // Helper function to solve Sudoku recursively (backtracking)
   private boolean solveSudoku(int[][] board, int row, int col) {
      if (row == SudokuConstants.GRID_SIZE) return true; // Base case: all rows are done
      if (col == SudokuConstants.GRID_SIZE) return solveSudoku(board, row + 1, 0); // Move to next row
      if (board[row][col] != 0) return solveSudoku(board, row, col + 1); // Skip filled cells

      for (int num = 1; num <= SudokuConstants.GRID_SIZE; num++) {
        if (isSafe(board, row, col, num)) {
            board[row][col] = num;
            if (solveSudoku(board, row, col + 1)) return true; // Recurse
            board[row][col] = 0; // Backtrack
        }
    }
    return false;
}

// Check if it's safe to place a number in a cell
private boolean isSafe(int[][] board, int row, int col, int num) {
    for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
        if (board[row][i] == num || board[i][col] == num) return false; // Check row and column
        if (board[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3] == num) return false; // Check 3x3 sub-grid
    }
    return true;
}

private void randomizeBoard() {
    Random random = new Random();
    for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
        int row1 = random.nextInt(3) + (i / 3) * 3; // Random row within a 3x3 sub-grid
        int row2 = random.nextInt(3) + (i / 3) * 3; // Swap rows
        swapRows(numbers, row1, row2);

        int col1 = random.nextInt(3) + (i / 3) * 3; // Random column within a 3x3 sub-grid
        int col2 = random.nextInt(3) + (i / 3) * 3; // Swap columns
        swapColumns(numbers, col1, col2);
    }
}

// Swap rows
private void swapRows(int[][] board, int row1, int row2) {
    int[] temp = board[row1];
    board[row1] = board[row2];
    board[row2] = temp;
}

// Swap columns
private void swapColumns(int[][] board, int col1, int col2) {
    for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
        int temp = board[i][col1];
        board[i][col1] = board[i][col2];
        board[i][col2] = temp;
    }
}

private void removeNumbers(int cellsToGuess) {
    Random random = new Random();
    int cellsRemoved = 0;
    isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

    while (cellsRemoved < cellsToGuess) {
        int row = random.nextInt(SudokuConstants.GRID_SIZE);
        int col = random.nextInt(SudokuConstants.GRID_SIZE);
        if (numbers[row][col] != 0) {
            numbers[row][col] = 0; // Remove the number
            isGiven[row][col] = false; // Mark as not given
            cellsRemoved++;
        }
    }

    // Mark remaining cells as given
    for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            if (numbers[row][col] != 0) {
                isGiven[row][col] = true;
            }
        }
    }
   }
}
