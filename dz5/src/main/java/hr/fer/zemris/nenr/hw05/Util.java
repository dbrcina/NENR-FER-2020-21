package hr.fer.zemris.nenr.hw05;

import hr.fer.zemris.bscthesis.classes.ClassType;
import hr.fer.zemris.bscthesis.dataset.Dataset;
import hr.fer.zemris.bscthesis.dataset.Sample;
import org.apache.commons.math3.analysis.function.Abs;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Util {

    private static final double DELTA = 1e-6;

    public static RealVector[] divideIntoCoordinates(List<Point> points) {
        int n = points.size();
        RealVector[] coordinates = {new ArrayRealVector(n), new ArrayRealVector(n)};
        for (int i = 0; i < n; i++) {
            Point point = points.get(i);
            coordinates[0].setEntry(i, point.x);
            coordinates[1].setEntry(i, point.y);
        }
        return coordinates;
    }

    public static RealVector prepareSample(RealVector[] points, int nFeatures) {
        RealVector xCoordinates = points[0];
        RealVector yCoordinates = points[1];
        // Find the center.
        double xCenter = 0.0, yCenter = 0.0;
        for (int i = 0; i < xCoordinates.getDimension(); i++) {
            xCenter += xCoordinates.getEntry(i);
            yCenter += yCoordinates.getEntry(i);
        }
        xCenter /= xCoordinates.getDimension();
        yCenter /= yCoordinates.getDimension();
        // Subtract the center from points.
        xCoordinates.mapSubtractToSelf(xCenter);
        yCoordinates.mapSubtractToSelf(yCenter);
        // Find the maximum coordinate.
        double m = Math.max(
                xCoordinates.map(new Abs()).getMaxValue(),
                yCoordinates.map(new Abs()).getMaxValue()
        );
        // Scale points with m into [-1,1].
        xCoordinates.mapDivideToSelf(m);
        yCoordinates.mapDivideToSelf(m);
        // Find the gesture size.
        double D = 0.0;
        for (int i = 0; i < xCoordinates.getDimension() - 1; i++) {
            D += Point.distance(
                    xCoordinates.getEntry(i), yCoordinates.getEntry(i),
                    xCoordinates.getEntry(i + 1), yCoordinates.getEntry(i + 1)
            );
        }
        RealVector sample = new ArrayRealVector(2 * nFeatures);
        sample.setEntry(0, xCoordinates.getEntry(0));
        sample.setEntry(1, yCoordinates.getEntry(0));
        for (int k = 1; k < nFeatures; k++) {
            double x = 0, y = 0;
            double desiredDistance = k * D / (nFeatures - 1.0);
            boolean foundPoint = false;
            for (int i = 1; i < xCoordinates.getDimension(); i++) {
                double currentDistance = Point.distance(
                        xCoordinates.getEntry(0), yCoordinates.getEntry(0),
                        xCoordinates.getEntry(i), yCoordinates.getEntry(i)
                );
                if (Math.abs(desiredDistance - currentDistance) <= DELTA) {
                    x = xCoordinates.getEntry(i);
                    y = yCoordinates.getEntry(i);
                    foundPoint = true;
                    break;
                }
            }
            if (!foundPoint) {
                x = xCoordinates.getEntry(xCoordinates.getDimension() - 1);
                y = yCoordinates.getEntry(yCoordinates.getDimension() - 1);
            }
            sample.setEntry(2 * k, x);
            sample.setEntry(2 * k + 1, y);
        }
        return sample;
    }

    public static Dataset prepareSimpleDataset(String file) throws Exception {
        Dataset dataset = new Dataset() {
            private List<Sample> samples;

            @Override
            public void setSamples(List<Sample> samples) {
                this.samples = samples;
            }

            @Override
            public int numberOfSamples() {
                return samples.size();
            }

            @Override
            public List<Sample> shuffleSamples() {
                List<Sample> shuffled = new ArrayList<>(samples);
                Collections.shuffle(shuffled);
                return shuffled;
            }

            @Override
            public void loadDataset(Path file) throws Exception {
                List<String> lines = Files.readAllLines(file);
                samples = lines.stream()
                        .map(line -> {
                            String[] parts = line.split("\\s+");
                            double[] inputs = Arrays.stream(parts[0].split(","))
                                    .mapToDouble(Double::parseDouble)
                                    .toArray();
                            double[] outputs = Arrays.stream(parts[1].split(","))
                                    .mapToDouble(Double::parseDouble)
                                    .toArray();
                            return new Sample(inputs, outputs, ClassType.determineFor(outputs));
                        })
                        .collect(Collectors.toList());
            }

            @Override
            public Iterator<Sample> iterator() {
                return samples.iterator();
            }
        };
        dataset.loadDataset(Paths.get(file));
        return dataset;
    }

}
