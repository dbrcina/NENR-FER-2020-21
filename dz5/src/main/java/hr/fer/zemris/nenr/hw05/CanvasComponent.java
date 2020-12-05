package hr.fer.zemris.nenr.hw05;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Supplier;

public class CanvasComponent extends JComponent {

    private final Supplier<java.util.List<Point>> pointsSupplier;

    public CanvasComponent(Supplier<java.util.List<Point>> pointsSupplier, int width, int height) {
        this.pointsSupplier = pointsSupplier;
        setBorder(new TitledBorder(new EtchedBorder(), "Sample space"));
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());

        List<Point> points = pointsSupplier.get();
        if (points.isEmpty()) return;

        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            xPoints[i] = point.x;
            yPoints[i] = point.y;
        }
        g.drawPolyline(xPoints, yPoints, points.size());
    }

}
