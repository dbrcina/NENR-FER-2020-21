package hr.fer.zemris.nenr.hw04;

import hr.fer.zemris.nenr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.nenr.hw04.ea.crossover.BLXACrossover;
import hr.fer.zemris.nenr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.nenr.hw04.ea.crossover.DiscreteDoubleUniformRecombination;
import hr.fer.zemris.nenr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.nenr.hw04.ea.fitness.FitnessFunctionImpl;
import hr.fer.zemris.nenr.hw04.ea.ga.GenerationGeneticAlgorithm;
import hr.fer.zemris.nenr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.initializer.RandomDoublePopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.mutation.GaussMutation;
import hr.fer.zemris.nenr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.nenr.hw04.ea.selection.KTournamentSelection;
import hr.fer.zemris.nenr.hw04.ea.selection.Selection;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author dbrcina
 */
public class Demo1 {

    public static void main(String[] args) throws IOException {
        String file = "src/main/resources/zad4-dataset2.txt";
        int populationSize = 50;
        int maxGenerations = 10_000;
        double epsilon = -1e-6;
        boolean useElitism = false;
        Random random = new Random();
        int unitSize = 5;
        double minValue = -4;
        double maxValue = 4;
        double sigma = 0.001;
        PopulationInitializer<Solution<Double>> initializer =
                new RandomDoublePopulationInitializer(random, unitSize, minValue, maxValue);
        Selection<Solution<Double>> selection = new KTournamentSelection<>(random, 3);
        Crossover<Solution<Double>> crossover = new BLXACrossover(random, 0.5, minValue, maxValue);
        Mutation<Solution<Double>> mutation = new GaussMutation(random, sigma, minValue, maxValue);
        FitnessFunction<Solution<Double>> fitnessFunction = parse(file);

        EvolutionaryAlgorithm<Solution<Double>> ga = new GenerationGeneticAlgorithm<>(
                populationSize,
                maxGenerations,
                epsilon,
                useElitism,
                initializer,
                selection,
                crossover,
                mutation,
                fitnessFunction
        );

        Solution<Double> solution = ga.run();
        System.out.println("Error = " + fitnessFunction.calculateFitness(solution));
        System.out.println("Solution = " + solution);
    }

    private static FitnessFunction<Solution<Double>> parse(String file) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        double[][] x = new double[lines.size()][2];
        double[] y = new double[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            double[] parts = Arrays.stream(lines.get(i).split("\\s+"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            x[i][0] = parts[0];
            x[i][1] = parts[1];
            y[i] = parts[2];
        }
        return new FitnessFunctionImpl(x, y);
    }

}
