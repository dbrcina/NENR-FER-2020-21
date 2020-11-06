package hr.fer.zemris.nenr.hw04.fitness;

import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.Arrays;

/**
 * An implementation of {@link FitnessFunction} interface.
 *
 * @author dbrcina
 */
public class FitnessFunctionImpl implements FitnessFunction<Solution<Double>> {

    private final double[][] x;
    private final double[] y;

    public FitnessFunctionImpl(double[][] x, double[] y) {
        this.x = Arrays.stream(x).map(double[]::clone).toArray(double[][]::new);
        this.y = Arrays.copyOf(y, y.length);
    }

    @Override
    public double calculateFitness(Solution<Double> solution) {
        double beta0 = solution.getGeneAt(0);
        double beta1 = solution.getGeneAt(1);
        double beta2 = solution.getGeneAt(2);
        double beta3 = solution.getGeneAt(3);
        double beta4 = solution.getGeneAt(4);
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            double expected = y[i];
            double actual = f(x[i][0], x[i][1], beta0, beta1, beta2, beta3, beta4);
            sum += Math.pow(expected - actual, 2);
        }
        return sum / x.length;
    }

    private double f(double x1, double x2, double... betas) {
        return Math.sin(betas[0] + betas[1] * x1)
                + betas[2] * Math.cos(x1 * (betas[3] * x2))
                * 1 / (1 + Math.exp(Math.pow(x1 - betas[4], 2)));
    }

}
