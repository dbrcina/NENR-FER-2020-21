package hr.fer.zemris.nenr.hw06;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author dbrcina
 */
public class Demo {

    public static void main(String[] args) {
        double[][] samples = generateSamples();
        ANFIS anfis = ANFIS.builder()
                .init(Integer.parseInt(args[0]), uniformInitialization(new Random(), -1, 1))
                .setStochastic(true)
                .setEpochs((int) 1e6)
                .setTol(0.02)
                .setEtaXY(1e-3)
                .setEtaZ(1e-4);
        anfis.fit(samples);
        double[][] data = Arrays.stream(samples)
                .map(double[]::clone)
                .toArray(double[][]::new);
        for (int i = 0; i < data.length; i++) {
            double x = data[i][0];
            double y = data[i][1];
            double f = anfis.calculateOutput(x, y);
            data[i][2] = f;
        }
        writeToCSV(new String[]{"x", "y", "f"}, data, "data.csv");
    }

    private static Consumer<double[]> uniformInitialization(Random random, double lb, double ub) {
        return array -> {
            for (int i = 0; i < array.length; i++) {
                array[i] = lb + random.nextDouble() * (ub - lb);
            }
        };
    }

    private static double[][] generateSamples() {
        int[] xRange = IntStream.rangeClosed(-4, 4).toArray();
        int[] yRange = IntStream.rangeClosed(-4, 4).toArray();
        double[][] samples = new double[xRange.length * yRange.length][3];
        int i = 0;
        for (int y : yRange) {
            for (int x : xRange) {
                double f = (Math.pow(x - 1, 2) + Math.pow(y + 2, 2) - 5 * x * y + 3)
                        * Math.pow(Math.cos(x / 5.0), 2);
                samples[i][0] = x;
                samples[i][1] = y;
                samples[i][2] = f;
                i += 1;
            }
        }
        return samples;
    }

    private static void writeToCSV(String[] header, double[][] data, String file) {
        try (PrintWriter pwr = new PrintWriter(Files.newOutputStream(Paths.get(file)))) {
            pwr.println(String.join(",", header));
            Arrays.stream(data).forEach(row -> pwr.println(Arrays.stream(row)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(","))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
