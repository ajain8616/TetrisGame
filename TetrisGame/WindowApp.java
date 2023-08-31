package TetrisGame;
import javax.swing.*;

import java.awt.*;

public class WindowApp {
    public WindowApp() {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TetrisGrid());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
