/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #9
 * 1 - 5026231010 - Daniel Setiawan Yulius Putra
 * 2 - 5026231160 - Muhammad Gandhi Taqi Utomo
 * 3 - 5026231184 - Dzaky Ahmad
 */
import java.util.ArrayList;
import java.util.List;

/**
 * AI player with Minimax algorithm.
 */
public class AIPlayerMinimax extends AIPlayer {

    /** Constructor */
    public AIPlayerMinimax(Board board) {
        super(board);
    }

    /**
     * Get the best move using Minimax algorithm.
     * 
     * @return int[2] of {row, col}
     */
    @Override
    public int[] move() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        
        // Evaluate all possible moves
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    // Try this move
                    board.cells[row][col].content = mySeed;
                    int score = minimax(0, false);
                    // Undo the move
                    board.cells[row][col].content = Seed.NO_SEED;
                    
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }
        return bestMove;
    }

    /**
     * Minimax recursive function.
     * 
     * @param depth   Current depth of recursion.
     * @param isMaximizing True if maximizing player, else minimizing.
     * @return Score of the board state.
     */
    private int minimax(int depth, boolean isMaximizing) {
        State currentState = evaluateState();
        if (currentState == State.CROSS_WON) {
            return (mySeed == Seed.CROSS) ? 10 - depth : depth - 10;
        } else if (currentState == State.NOUGHT_WON) {
            return (mySeed == Seed.NOUGHT) ? 10 - depth : depth - 10;
        } else if (currentState == State.DRAW) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < Board.ROWS; ++row) {
                for (int col = 0; col < Board.COLS; ++col) {
                    if (board.cells[row][col].content == Seed.NO_SEED) {
                        board.cells[row][col].content = mySeed;
                        bestScore = Math.max(bestScore, minimax(depth + 1, false));
                        board.cells[row][col].content = Seed.NO_SEED;
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < Board.ROWS; ++row) {
                for (int col = 0; col < Board.COLS; ++col) {
                    if (board.cells[row][col].content == Seed.NO_SEED) {
                        board.cells[row][col].content = oppSeed;
                        bestScore = Math.min(bestScore, minimax(depth + 1, true));
                        board.cells[row][col].content = Seed.NO_SEED;
                    }
                }
            }
            return bestScore;
        }
    }

    /**
     * Evaluate the current state of the board.
     * 
     * @return Current state (PLAYING, CROSS_WON, NOUGHT_WON, DRAW).
     */
    private State evaluateState() {
        for (int row = 0; row < Board.ROWS; ++row) {
            if (board.cells[row][0].content != Seed.NO_SEED 
                && board.cells[row][0].content == board.cells[row][1].content
                && board.cells[row][1].content == board.cells[row][2].content) {
                return (board.cells[row][0].content == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
            }
        }
        for (int col = 0; col < Board.COLS; ++col) {
            if (board.cells[0][col].content != Seed.NO_SEED 
                && board.cells[0][col].content == board.cells[1][col].content
                && board.cells[1][col].content == board.cells[2][col].content) {
                return (board.cells[0][col].content == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
            }
        }
        if (board.cells[0][0].content != Seed.NO_SEED 
            && board.cells[0][0].content == board.cells[1][1].content
            && board.cells[1][1].content == board.cells[2][2].content) {
            return (board.cells[0][0].content == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }
        if (board.cells[0][2].content != Seed.NO_SEED 
            && board.cells[0][2].content == board.cells[1][1].content
            && board.cells[1][1].content == board.cells[2][0].content) {
            return (board.cells[0][2].content == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        }
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    return State.PLAYING;
                }
            }
        }
        return State.DRAW;
    }
}
