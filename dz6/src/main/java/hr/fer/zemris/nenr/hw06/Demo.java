package hr.fer.zemris.nenr.hw06;

import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author dbrcina
 */
public class Demo {

    public static void main(String[] args) {
        ANFIS anfis = ANFIS.builder()
                .init(6, uniformInitialization(new Random(), -1, 1));
        anfis.fit(generateSamples());
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

}
