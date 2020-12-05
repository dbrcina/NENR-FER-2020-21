package hr.fer.zemris.nenr.hw05;

import hr.fer.zemris.bscthesis.classes.ClassType;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.util.Pair;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingFrame extends JFrame {

    private static final int N_FEATURES = 30;

    private final List<ClassType> classTypes = new ArrayList<>(ClassType.allClassTypes());
    private ClassType currentClassType = classTypes.get(0);

    private final List<Pair<RealVector[], ClassType>> pointsHistory = new ArrayList<>();
    private final List<Point> pointsForDrawing = new ArrayList<>();

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
                    pointsHistory.add(new Pair<>(Util.divideIntoCoordinates(pointsForDrawing), currentClassType));
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
        prepareSaveBtn();
        footerPanel.add(lblClassType);
        footerPanel.add(fldClassType);
        footerPanel.add(btnSave);
        return footerPanel;
    }

    private void prepareSaveBtn() {
        btnSave.addActionListener(e -> {
            PrintWriter wr = null;
            try {
                wr = new PrintWriter("samples.txt");
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            for (Pair<RealVector[], ClassType> pair : pointsHistory) {
                RealVector sample = Util.prepareSample(pair.getFirst(), N_FEATURES);
                wr.write(Arrays.stream(sample.toArray())
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(","))
                );
                wr.write(" ");
                wr.println(Arrays.stream(pair.getSecond().getDesiredOutputs())
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(","))
                );
            }
            wr.flush();
        });
    }

    public static void main(String[] args) {
        ClassType.init();
        SwingUtilities.invokeLater(() -> new TrainingFrame().setVisible(true));
    }

}
