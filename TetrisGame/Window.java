package TetrisGame;
import javax.swing.*;
public class Window {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new WindowApp();
        });
    }
}
