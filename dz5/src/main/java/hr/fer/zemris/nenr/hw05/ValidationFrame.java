package hr.fer.zemris.nenr.hw05;

import hr.fer.zemris.bscthesis.ann.NeuralNetwork;
import hr.fer.zemris.bscthesis.classes.ClassType;
import org.apache.commons.math3.linear.RealVector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class ValidationFrame extends JFrame {

    private static final int N_FEATURES = 30;

    private final NeuralNetwork nn;
    private final List<Point> pointsForDrawing = new ArrayList<>();

    public ValidationFrame(NeuralNetwork nn) {
        this.nn = nn;
        setResizable(true);
        setTitle("Validation window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initGUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        Container pane = getContentPane();
        pane.add(initCanvas());
    }

    private JComponent initCanvas() {
        JComponent canvas = new CanvasComponent(() -> pointsForDrawing, 500, 500);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    pointsForDrawing.clear();
                    pointsForDrawing.add(e.getPoint());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                RealVector sample = Util.prepareSample(Util.divideIntoCoordinates(pointsForDrawing), N_FEATURES);
                double[] results = nn.feedForward(sample.toArray());
                ClassType classType = ClassType.determineFor(results);
                JOptionPane.showMessageDialog(ValidationFrame.this, classType.getId());
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    pointsForDrawing.add(e.getPoint());
                    repaint();
                }
            }
        });
        return canvas;
    }

}
