package TetrisGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class TetrisGrid extends JPanel {
    private  int[][] iShapeCoordinates = {
        {0, 2},  // Row 1
        {0, 3}, // Row 2
        {0, 4},   // Row 3
        {0, 5},  // Row 4
    };   
    public TetrisGrid() {
        setLayout(new BorderLayout());
        JPanel buttonPanelJPanel = new JPanel(new GridLayout(0, 1)); // Vertical layout        buttonPanelRightJPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Adding padding
        JButton leftButton = new JButton("Left Move");
        JButton endButton = new JButton("Right Move");
        JButton bottomButton = new JButton("Bottom Move");
        JButton addShapeButton = new JButton("Add Shapes");
        JButton rotatedShapes=new JButton("Rotated Shapes");

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Activate the first shape
                    moveIShapeLeft(); 
                }
        });

        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// Activate the second shape
                    moveIShapeEnd();
            }
        });

        bottomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    moveIShapeBottom();
            }
        });

    addShapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             addIShape();     

            }
        });
        rotatedShapes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             rotatedShapes();     

            }
        });

        // Adding some spaces between buttons
        buttonPanelJPanel.add(leftButton);
        buttonPanelJPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space
        buttonPanelJPanel.add(endButton);
        buttonPanelJPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space
        buttonPanelJPanel.add(bottomButton);
        buttonPanelJPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space
        buttonPanelJPanel.add(addShapeButton);
        buttonPanelJPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space
        buttonPanelJPanel.add(rotatedShapes);
        add(buttonPanelJPanel, BorderLayout.EAST); // Place the button panel on the right
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        int size = Math.min(getWidth() - 4, getHeight() - 4) / 10;
        int height = (getHeight() - (size * 10)) / 2;
        for (int col = 0; col < 10; col++) {
            int width= (getWidth() - (size * 10)) / 2;
            for (int row = 0; row < 8; row++) {
                g.drawRect(width, height, size, size);

                // Calculate and draw the grid coordinates
                String coordinate = col + "," + row;
                FontMetrics fm = g2d.getFontMetrics();
                int textWidth = fm.stringWidth(coordinate);
                int textHeight = fm.getHeight();
                int textX = width + (size - textWidth) / 2;
                int textY = height + (size - textHeight) / 2 + fm.getAscent();
                g2d.drawString(coordinate, textX, textY);

                width+= size;
            }
            height += size;
        }

        // Draw I-shaped block
        drawIShape(g2d, size);

        g2d.dispose();
    }
     

    private void drawIShape(Graphics2D g2d, int size) {
        g2d.setColor(Color.RED);  // Set the color for the I-shaped block
        for (int[] coord : iShapeCoordinates) {
            int x = coord[1] * size + (getWidth() - (size * 10)) / 2;
            int y = coord[0] * size + (getHeight() - (size * 10)) / 2;
            g2d.fillRect(x, y, size, size);
        }
    }

    // Function to move the I-shaped block to the left
    private void moveIShapeLeft() {
        boolean canMoveLeft = true;
        // Check if any part of the I-shaped block is already at the leftmost limit (column 0)
        for (int[] coord : iShapeCoordinates) {
            if (coord[1] == 0) {
                canMoveLeft = false;
                break;
            }
        }
    
        if (canMoveLeft) {
            for (int[] coord : iShapeCoordinates) {
                coord[1]--;  // Decrement the column value to move left
            }
            repaint();  // Redraw the panel to reflect the changes
        }
    }
    private void moveIShapeEnd() {
        boolean canMoveRight = true;
    
        // Check if any part of the I-shaped block is already at the rightmost limit (column 7)
        for (int[] coord : iShapeCoordinates) {
            if (coord[1] ==7) {
                canMoveRight = false;
                break;
            }
        }
    
        if (canMoveRight) {
            for (int[] coord : iShapeCoordinates) {
                coord[1]++;  // Increment the column value to move right
            }
            repaint();  // Redraw the panel to reflect the changes
        }
    }
    
    private void moveIShapeBottom() {
        boolean canMoveDown = true;
    
        // Check if any part of the I-shaped block is already at the bottom limit (row 9)
        for (int[] coord : iShapeCoordinates) {
            if (coord[0] == 9) {
                canMoveDown = false;
                break;
            }
        }
    
        if (canMoveDown) {
            for (int[] coord : iShapeCoordinates) {
                coord[0]++;  // Increment the row value to move down
            }
            repaint();  // Redraw the panel to reflect the changes
        }
    }
    private void addIShape() {
        
    }
    
    private void rotatedShapes() {
        
    }
}
