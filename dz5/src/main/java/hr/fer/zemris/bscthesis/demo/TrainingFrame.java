package hr.fer.zemris.bscthesis.demo;

import hr.fer.zemris.bscthesis.classes.ClassType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class TrainingFrame extends JFrame {

    private final List<ClassType> classTypes = new ArrayList<>(ClassType.allClassTypes());
    private ClassType currentClassType = classTypes.get(0);

    private final Collection<PointsClassTypePair> pointsHistory = new ArrayList<>();
    private final Collection<Point> pointsForDrawing = new ArrayList<>();

    private final JTextField fldClassType = new JTextField(10);
    private final JButton btnSave = new JButton("Save");

    public TrainingFrame() {
        setResizable(true);
        setTitle("Training window");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initGUI();
        pack();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        Container pane = getContentPane();
        pane.add(initCanvas());
        pane.add(initFooterPanel(), BorderLayout.PAGE_END);
    }

    private JComponent initCanvas() {
        JComponent canvas = new CanvasComponent(() -> pointsForDrawing, 500, 500);
        canvas.addMouseListener(new MouseAdapter() {
            private int classTypeIndex = 0;

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    classTypeIndex = (classTypeIndex + 1) % classTypes.size();
                    currentClassType = classTypes.get(classTypeIndex);
                    fldClassType.setText(currentClassType.getId());
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    pointsForDrawing.add(e.getPoint());
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    pointsHistory.add(new PointsClassTypePair(new ArrayList<>(pointsForDrawing), currentClassType));
                    pointsForDrawing.clear();
                }
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

    private JComponent initFooterPanel() {
        JPanel footerPanel = new JPanel();
        JLabel lblClassType = new JLabel("Class type:");
        fldClassType.setText(currentClassType.getId());
        fldClassType.setEditable(false);
        fldClassType.setBorder(new EmptyBorder(0, 0, 0, 0));
        btnSave.setFocusPainted(false);
        prepareData();
        footerPanel.add(lblClassType);
        footerPanel.add(fldClassType);
        footerPanel.add(btnSave);
        return footerPanel;
    }

    private void prepareData() {
        btnSave.addActionListener(e -> {
            // Logika...
            System.out.println("Logika");
        });
    }

    private static class CanvasComponent extends JComponent {
        private final Supplier<Collection<Point>> pointsSupplier;

        private CanvasComponent(Supplier<Collection<Point>> pointsSupplier, int width, int height) {
            this.pointsSupplier = pointsSupplier;
            setBorder(new TitledBorder(new EtchedBorder(), "Sample space"));
            setPreferredSize(new Dimension(width, height));
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.clearRect(0, 0, getWidth(), getHeight());

            Collection<Point> points = pointsSupplier.get();
            if (points.isEmpty()) return;

            int[] xPoints = new int[points.size()];
            int[] yPoints = new int[points.size()];
            int index = 0;
            for (Point point : points) {
                xPoints[index] = point.x;
                yPoints[index] = point.y;
                index++;
            }
            g.drawPolyline(xPoints, yPoints, points.size());
        }
    }

    private static class PointsClassTypePair {
        private final Collection<Point> points;
        private final ClassType classType;

        private PointsClassTypePair(Collection<Point> points, ClassType classType) {
            this.points = points;
            this.classType = classType;
        }
    }

    public static void main(String[] args) {
        ClassType.init();
        SwingUtilities.invokeLater(() -> new TrainingFrame().setVisible(true));
    }

}
