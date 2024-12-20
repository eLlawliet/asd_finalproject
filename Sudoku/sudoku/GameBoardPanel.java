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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for UI sizes
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
                                             // Board width/height in pixels

   // Define properties
   /** The game board composes of 9x9 Cells (customized JTextFields) */
   private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   /** It also contains a Puzzle with array numbers and isGiven */
   private Puzzle puzzle = new Puzzle();

   /** Constructor */
   public GameBoardPanel() {
      super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

      // Allocate the 2D array of Cell, and added into JPanel.
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col] = new Cell(row, col);
            super.add(cells[row][col]);   // JPanel
         }
      }

      // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
      CellInputListener listener = new CellInputListener();

      // [TODO 4] Adds this common listener to all editable cells
      for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {  // Use the constant for row iteration
         for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {  // Use the constant for column iteration
             if (cells[row][col].isEditable()) {  // Check if the cell is editable
                 cells[row][col].addActionListener(listener);   // Add the action listener to the editable cell
            }
         }
      }
      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
   }

   /**
    * Generate a new puzzle; and reset the game board of cells based on the puzzle.
    * You can call this method to start a new game.
    */
    public void newGame() {
         // Ask the player how many cells they want to guess
         int numCellsToGuess = 0;
   boolean validInput = false;

   while (!validInput) {
      // Ask the player how many cells they want to guess
      String input = JOptionPane.showInputDialog(this, "Enter the number of cells to guess (max 81):", "New Game", JOptionPane.QUESTION_MESSAGE);
      
      if (input == null) {
         // If the user cancels the input dialog, exit the method
         System.exit(0);
         return;
      }
      
      try {
         numCellsToGuess = Integer.parseInt(input);
         if (numCellsToGuess > 81) {
            JOptionPane.showMessageDialog(this, "The maximum number of cells to guess is 81. Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
         } else if (numCellsToGuess < 1) {
            JOptionPane.showMessageDialog(this, "The minimum number of cells to guess is 1. Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
         } else {
            validInput = true; // Valid input
         }
      } catch (NumberFormatException e) {
         JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
      }
   }

   puzzle.newPuzzle(numCellsToGuess);
   
      // Initialize all the 9x9 cells based on the puzzle
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }
   }
   

   /**
    * Return true if the puzzle is solved
    * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
    */
   public boolean isSolved() {
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
               return false;
            }
         }
      }
      return true;
   }

   public boolean checkAnswer(int row, int col, int numberIn) {
      return cells[row][col].number == numberIn; // Pastikan sel sesuai dengan angka yang benar
  }

   // [TODO 2] Define a Listener Inner Class for all the editable Cells
   private class CellInputListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // Get a reference of the JTextField that triggers this action event
         Cell sourceCell = (Cell)e.getSource();
		 
         // Retrieve the int entered
         int numberIn = Integer.parseInt(sourceCell.getText());
         // For debugging
         System.out.println("You entered " + numberIn);

         /*
          * [TODO 5] (later - after TODO 3 and 4)
          * Check the numberIn against sourceCell.number.
          * Update the cell status sourceCell.status,
          * and re-paint the cell via sourceCell.paint().
          */
          if (checkAnswer(sourceCell.row, sourceCell.col, numberIn)) {
            sourceCell.status = CellStatus.CORRECT_GUESS;
        } else {
            sourceCell.status = CellStatus.WRONG_GUESS;
        }

        sourceCell.paint(); // Perbarui tampilan status sel

         /*
          * [TODO 6] (later)
          * Check if the player has solved the puzzle after this move,
          *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
          */

          if (isSolved()) {
            JOptionPane.showMessageDialog(null, "Congratulations! You have solved the puzzle!", "Puzzle Solved", JOptionPane.INFORMATION_MESSAGE);
        }
      }
   }
}