package TetrisGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Shapes extends JPanel {
    private static final int CELL_SIZE = 30;
    private static final int SHAPE_MARGIN = 5;

    private static final Color[] SHAPE_COLORS = {
        Color.YELLOW, Color.CYAN, Color.MAGENTA,
        Color.GREEN, Color.LIGHT_GRAY, Color.BLUE, Color.RED
    };
    private static final int[][][] SHAPES = {
        // O Shape
        {{1, 1},
         {1, 1}},

        // I Shape
        {{0, 0, 0, 0},
         {1, 1, 1, 1},
         {0, 0, 0, 0},
         {0, 0, 0, 0}},
         
        // T Shape
        {{0, 1, 0},
         {1, 1, 1},
         {0, 0, 0}},

        // L Shape
        {{1, 0, 0},
         {1, 1, 1},
         {0, 0, 0}},

        // J Shape
        {{0, 0, 1},
         {1, 1, 1},
         {0, 0, 0}},

        // S Shape
        {{0, 1, 1},
         {1, 1, 0},
         {0, 0, 0}},

        // Z Shape
        {{1, 1, 0},
         {0, 1, 1},
         {0, 0, 0}},
    };

    public Shapes() {
        JButton rotateButton = new JButton("Rotate");
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rotateShapesClockwise();
                repaint();
            }
        });
        add(rotateButton);
    }
    
    private void rotateShapesClockwise() {
        for (int shapeIdx = 0; shapeIdx < SHAPES.length; shapeIdx++) {
            int[][] shape = SHAPES[shapeIdx];
            int[][] rotatedShape = new int[shape.length][shape[0].length];
            
            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    rotatedShape[col][shape.length - 1 - row] = shape[row][col];
                }
            }
            
            SHAPES[shapeIdx] = rotatedShape;
        }
    } 

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int shapeIdx = 0; shapeIdx < SHAPES.length; shapeIdx++) {
            int startX = (shapeIdx % 4) * (CELL_SIZE * 5 + SHAPE_MARGIN) + 50;
            int startY = (shapeIdx / 4) * (CELL_SIZE * 5 + SHAPE_MARGIN) + 50;

            g.setColor(SHAPE_COLORS[shapeIdx]);
            int[][] shape = SHAPES[shapeIdx];

            for (int row = 0; row < shape.length; row++) {
                for (int col = 0; col < shape[row].length; col++) {
                    if (shape[row][col] == 1) {
                        g.fillRect(startX + col * CELL_SIZE, startY + row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        
                        g.setColor(Color.BLACK);
                        String index = row + "," + col;
                        FontMetrics fm = g.getFontMetrics();
                        int textWidth = fm.stringWidth(index);
                        int textHeight = fm.getHeight();
                        int textX = startX + col * CELL_SIZE + (CELL_SIZE - textWidth) / 2;
                        int textY = startY + row * CELL_SIZE + (CELL_SIZE - textHeight) / 2 + fm.getAscent();
                        g.drawString(index, textX, textY);
                        
                        g.setColor(SHAPE_COLORS[shapeIdx]);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Shapes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Shapes shapesPanel = new Shapes();
        frame.add(shapesPanel);
        
        frame.setSize(700, 550);
        frame.setVisible(true);
    }
}
