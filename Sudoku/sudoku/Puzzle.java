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
import java.util.*;
/**
 * The Sudoku number puzzle to be solved
 */
public class Puzzle {
   // All variables have package access
   // The numbers on the puzzle
   int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   // The clues - isGiven (no need to guess) or need to guess
   boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

   // Constructor
   public Puzzle() {
      super();
   }

   public void newPuzzle(int cellsToGuess) {
      // Step 1: Generate a fully solved Sudoku board
      generateSolvedBoard();

      // Step 2: Randomly remove cells based on the difficulty level
      Random random = new Random();

      // Jumlah false yang diinginkan

      // Hitung total elemen dalam array
      int totalCells = isGiven.length * isGiven[0].length;

      // Pastikan jumlah false tidak melebihi jumlah total sel
      if (cellsToGuess > totalCells) {
         System.out.println("Jumlah false melebihi jumlah total sel!");
         return;
      }

      // Mengisi array dengan true
      for (int row = 0; row < isGiven.length; row++) {
         for (int col = 0; col < isGiven[0].length; col++) {
              isGiven[row][col] = true; // Isi semua dengan true terlebih dahulu
         }
      }

      // Mengisi sejumlah 'false' sesuai dengan input
      int falseCount = 0;
      while (falseCount < cellsToGuess) {
         int row = random.nextInt(isGiven.length);
         int col = random.nextInt(isGiven[0].length);
         
          // Hanya ganti menjadi false jika elemen tersebut belum false
         if (isGiven[row][col] == true) {
            isGiven[row][col] = false;
            falseCount++;
         }
      }
   }

   private void generateSolvedBoard() {
      // Inisialisasi board kosong
      for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            numbers[row][col] = 0;
            isGiven[row][col] = true; // Semua sel diisi awalnya
         }
      }
      
      // Gunakan algoritma Backtracking untuk mengisi board
      solve(0, 0);
   }

   private boolean solve(int row, int col) {
      if (row == SudokuConstants.GRID_SIZE) {
         return true; // Solved the entire board
      }
      
      int nextRow = (col == SudokuConstants.GRID_SIZE - 1) ? row + 1 : row;
      int nextCol = (col == SudokuConstants.GRID_SIZE - 1) ? 0 : col + 1;

      if (numbers[row][col] != 0) {
         return solve(nextRow, nextCol); // Skip pre-filled cells
      }

      Random randomBoard = new Random();
      int[] nums = randomBoard.ints(1, 10).distinct().limit(9).toArray(); // Shuffle 1-9

      for (int num : nums) {
         if (isValid(num, row, col)) {
            numbers[row][col] = num;
            if (solve(nextRow, nextCol)) {
               return true;
            }
            numbers[row][col] = 0; // Backtrack
         }
      }
      return false;
   }
   private boolean isValid(int num, int row, int col) {
      // Cek baris
      for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
         if (numbers[row][i] == num) return false;
      }
      
      // Cek kolom
      for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
         if (numbers[i][col] == num) return false;
      }

      // Cek kotak 3x3
      int boxRow = (row / 3) * 3;
      int boxCol = (col / 3) * 3;
      for (int r = 0; r < 3; r++) {
         for (int c = 0; c < 3; c++) {
            if (numbers[boxRow + r][boxCol + c] == num) return false;
         }
      }
      return true;
   }

   //(For advanced students) use singleton design pattern for this class
}