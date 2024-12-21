public abstract class AIPlayer {
    protected Board board; // the game board
    protected Seed mySeed; // computer's seed
    protected Seed oppSeed; // opponent's seed

    /** Constructor with reference to the board */
    public AIPlayer(Board board) {
        this.board = board;
    }

    /** Set/change the seed for the AI player */
    public void setSeed(Seed seed) {
        this.mySeed = seed;
        this.oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    /** Abstract method to get the next move */
    public abstract int[] move(); // to be implemented by subclasses

    /** Check if the game has a winning line */
    protected boolean hasWon(Seed seed, int row, int col) {
        return (board.cells[row][0].content == seed && board.cells[row][1].content == seed && board.cells[row][2].content == seed) || // row
               (board.cells[0][col].content == seed && board.cells[1][col].content == seed && board.cells[2][col].content == seed) || // column
               (row == col && board.cells[0][0].content == seed && board.cells[1][1].content == seed && board.cells[2][2].content == seed) || // diagonal
               (row + col == 2 && board.cells[0][2].content == seed && board.cells[1][1].content == seed && board.cells[2][0].content == seed); // opposite diagonal
    }

    /** Check if the board is full */
    protected boolean isDraw() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                if (board.cells[row][col].content == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }
}
