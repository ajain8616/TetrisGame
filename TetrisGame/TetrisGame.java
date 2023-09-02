package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TetrisGame extends JPanel implements ActionListener, KeyListener {
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    private static final int CELL_SIZE = 30;
    private static final int DELAY = 300;

    private Timer timer;
    private boolean[][] grid = new boolean[HEIGHT][WIDTH];
    private int currentX, currentY;
    private int[][] currentShape;
    private Random random = new Random();

    public TetrisGame() {
        timer = new Timer(DELAY, this);
        timer.start();
        setPreferredSize(new Dimension(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE));
        setBackground(Color.WHITE); // Change background color
        addKeyListener(this);
        setFocusable(true);
        initGame();
    }

    private void initGame() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = false;
            }
        }
        spawnShape();
    }

    private void spawnShape() {
        currentX = WIDTH / 2;
        currentY = 0;

        // Define different shapes
        int shapeType = random.nextInt(7);
        switch (shapeType) {
            case 0:
                currentShape = new int[][]{{1, 1, 1, 1}};
                break;
            case 1:
                currentShape = new int[][]{{1, 1}, {1, 1}};
                break;
            case 2:
                currentShape = new int[][]{{1, 1, 1}, {0, 0, 1}};
                break;
            case 3:
                currentShape = new int[][]{{1, 1, 1}, {1, 0, 0}};
                break;
            case 4:
                currentShape = new int[][]{{1, 1, 1}, {0, 1, 0}};
                break;
            case 5:
                currentShape = new int[][]{{1, 1, 1}, {0, 0, 1}};
                break;
            case 6:
                currentShape = new int[][]{{1, 1, 1}, {0, 1, 1}};
                break;
        }
    }

    private void moveDown() {
        if (isValidMove(currentShape, currentX, currentY + 1)) {
            currentY++;
        } else {
            placeShape();
            clearRows();
            spawnShape();
        }
    }

    private void moveLeft() {
        if (isValidMove(currentShape, currentX - 1, currentY)) {
            currentX--;
        }
    }

    private void moveRight() {
        if (isValidMove(currentShape, currentX + 1, currentY)) {
            currentX++;
        }
    }

    private void rotate() {
        int[][] rotatedShape = new int[currentShape[0].length][currentShape.length];
        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[0].length; j++) {
                rotatedShape[j][currentShape.length - 1 - i] = currentShape[i][j];
            }
        }
        if (isValidMove(rotatedShape, currentX, currentY)) {
            currentShape = rotatedShape;
        }
    }

    private boolean isValidMove(int[][] shape, int x, int y) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    int newX = x + j;
                    int newY = y + i;
                    if (newX < 0 || newX >= WIDTH || newY >= HEIGHT || grid[newY][newX]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void placeShape() {
        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[0].length; j++) {
                if (currentShape[i][j] == 1) {
                    int x = currentX + j;
                    int y = currentY + i;
                    grid[y][x] = true;
                }
            }
        }
    }

    private void clearRows() {
        for (int i = HEIGHT - 1; i >= 0; i--) {
            boolean rowFull = true;
            for (int j = 0; j < WIDTH; j++) {
                if (!grid[i][j]) {
                    rowFull = false;
                    break;
                }
            }
            if (rowFull) {
                for (int k = i; k > 0; k--) {
                    for (int j = 0; j < WIDTH; j++) {
                        grid[k][j] = grid[k - 1][j];
                    }
                }
                i++; // Check the same row again after shifting
            }
        }
    }

    private void drawBlock(Graphics2D g, int x, int y) {
        g.setColor(Color.BLACK); // Monochromatic color
        g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        g.setColor(Color.GRAY);
        g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the grid
        g2d.setColor(Color.GRAY); // Grid color
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                g2d.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Draw the filled cells
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (grid[i][j]) {
                    drawBlock(g2d, j, i);
                }
            }
        }

        // Draw the current shape
        for (int i = 0; i < currentShape.length; i++) {
            for (int j = 0; j < currentShape[0].length; j++) {
                if (currentShape[i][j] == 1) {
                    drawBlock(g2d, currentX + j, currentY + i);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        moveDown();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                moveRight();
                break;
            case KeyEvent.VK_DOWN:
                moveDown();
                break;
            case KeyEvent.VK_UP:
                rotate();
                break;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tetris Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            TetrisGame tetrisGame = new TetrisGame();
            frame.add(tetrisGame);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
