/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #9
 * 1 - 5026231010 - Daniel Setiawan Yulius Putra
 * 2 - 5026231160 - Muhammad Gandhi Taqi Utomo
 * 3 - 5026231184 - Dzaky Ahmad
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Connect Four: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class C4GameMain extends JPanel {
    private static final long serialVersionUID = 1L;

    // Define named constants for the drawing graphics
    public static final String TITLE = "Connect Four";
    public static final Color COLOR_BG = Color.WHITE;
    public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static final Color COLOR_CROSS = new Color(239, 105, 80); // Red #EF6950
    public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue #409AE1
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    public static final int ROWS = 6; // Updated grid size for Connect Four
    public static final int COLS = 7; // Updated grid size for Connect Four

    // Define game objects
    private C4Board board; // the game board
    private State currentState; // the current state of the game
    private Seed currentPlayer; // the current player
    private JLabel statusBar; // for displaying status message
    private SoundManager soundManager;

    /** Constructor to setup the UI and game components */
    public C4GameMain() {

        // This JPanel fires MouseEvent
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int col = mouseX / Cell.SIZE;

                // Check for the lowest available row in the selected column
                int row = -1;
                for (int r = C4Board.ROWS - 1; r >= 0; --r) {
                    if (board.cells[r][col].content == Seed.NO_SEED) {
                        row = r;
                        break;
                    }
                }

                if (row >= 0 && currentState == State.PLAYING) {
                    currentState = board.stepGame(currentPlayer, row, col);
                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                }

                // Refresh the drawing canvas
                repaint(); // Callback paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(400, 40));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
        super.setPreferredSize(new Dimension(C4Board.CANVAS_WIDTH, C4Board.CANVAS_HEIGHT + 30));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Set up Game
        initGame();
        newGame();
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new C4Board(); // allocate the game-board
        soundManager = new SoundManager();
        soundManager.playBackgroundMusic("audio/Bg.wav");
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < C4Board.ROWS; ++row) {
            for (int col = 0; col < C4Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS; // cross plays first
        currentState = State.PLAYING; // ready to play
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(COLOR_BG); // set its background color

        board.paint(g); // ask the game board to paint itself

        // Print status-bar message
        if (currentState == State.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (currentState == State.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
            soundManager.stopBackgroundMusic();
            soundManager.playBackgroundMusic("audio/Draw.wav");
        } else if (currentState == State.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
            soundManager.stopBackgroundMusic();
            soundManager.playBackgroundMusic("audio/xwin.wav");
        } else if (currentState == State.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
            soundManager.stopBackgroundMusic();
            soundManager.playBackgroundMusic("audio/WIN.wav");
        }
    }

    /** The entry "main" method */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new C4GameMain());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true); // show it
            }
        });
    }
}
