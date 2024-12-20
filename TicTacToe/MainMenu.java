import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JFrame frame;

    public MainMenu(JFrame frame) {
        this.frame = frame;

        // Set the layout for the panel
        setLayout(new FlowLayout());

        // Create buttons
        JButton vsHumanButton = new JButton("VS Human");
        JButton vsAIButton = new JButton("VS AI");
        JButton otherGamesButton = new JButton("Other Games");

        // Add action listeners to the buttons
        vsHumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle VS Human button click
                System.out.println("VS Human button clicked");
                // Add your code here to handle the action
                frame.setContentPane(new GameMain());
                frame.setSize(Board.CANVAS_WIDTH+15, Board.CANVAS_HEIGHT+70);
                frame.revalidate();
            }
        });

        vsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle VS AI button click
                System.out.println("VS AI button clicked");
                // Add your code here to handle the action
            }
        });

        otherGamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle Other Games button click
                System.out.println("Other Games button clicked");
                // Add your code here to handle the action
            }
        });

        // Add buttons to the panel
        add(vsHumanButton);
        add(vsAIButton);
        add(otherGamesButton);
    }
}