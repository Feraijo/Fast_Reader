import javax.swing.*;
import java.awt.*;

/**
 * Created by Feraijo on 07.11.2016.
 */

public class MainFrame {
    private static void createAndShowGUI() {
        int width = 600, height = 200;

        JFrame frame = new JFrame("FastReader");
        frame.setSize(new Dimension(width, height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(new MainPanel(new BorderLayout()));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
