import javax.swing.*;
import java.awt.*;

public class MainMenu {
    private JPanel panel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("myFrame");
        frame.setContentPane(new MainMenu().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setMinimumSize(new Dimension(600,600));
        frame.setVisible(true);
    }
}
