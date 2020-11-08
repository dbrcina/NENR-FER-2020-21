package hr.fer.zemris.nenr.hw04.ea.ga;

import hr.fer.zemris.nenr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.nenr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.nenr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.nenr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.nenr.hw04.ea.selection.Selection;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * An implementation of <i>generational genetic algorithm</i>.
 *
 * @author dbrcina
 */
public class GenerationGeneticAlgorithm<S extends Solution<?>> implements EvolutionaryAlgorithm<S> {

    private final int ELITISM_SOLUTIONS = 1;

    private final int populationSize;
    private final int maxGenerations;
    private final double epsilon;
    private final boolean useElitism;
    private final PopulationInitializer<S> initializer;
    private final Selection<S> selection;
    private final Crossover<S> crossover;
    private final Mutation<S> mutation;
    private final FitnessFunction<S> fitnessFunction;

    public GenerationGeneticAlgorithm(
            int populationSize,
            int maxGenerations,
            double epsilon,
            boolean useElitism,
            PopulationInitializer<S> initializer,
            Selection<S> selection,
            Crossover<S> crossover,
            Mutation<S> mutation,
            FitnessFunction<S> fitnessFunction) {
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.epsilon = epsilon;
        this.useElitism = useElitism;
        this.initializer = initializer;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.fitnessFunction = fitnessFunction;
    }

    @Override
    public S run() {
        S bestSolution = null;

        System.out.println("Initializing the population...");
        List<S> population = initializer.generatePopulation(populationSize);
        System.out.println("Population initialized!");

        System.out.println("Starting generations...");
        for (int generation = 0; generation < maxGenerations; generation++) {

            // Evaluate population.
            for (S solution : population) {
                if (!solution.isEvaluated()) {
                    solution.setFitness(fitnessFunction.calculateFitness(solution));
                }
                if (bestSolution == null || solution.getFitness() > bestSolution.getFitness()) {
                    bestSolution = solution;
                    System.out.printf(
                            "Generation %6d: found new best solution with fitness %e and values %s%n",
                            generation + 1, bestSolution.getFitness(), bestSolution
                    );
                }
            }
            if (bestSolution != null && bestSolution.getFitness() >= epsilon) {
                break;
            }

            // Helper empty population.
            List<S> newPopulation = new ArrayList<>();

            // Elitism.
            if (useElitism) {
                // Insert ELITISM_SOLUTIONS best solutions.
                newPopulation.addAll(population.stream()
                        .sorted((s1, s2) -> Double.compare(s2.getFitness(), s1.getFitness()))
                        .limit(ELITISM_SOLUTIONS)
                        .collect(Collectors.toList()));
            }

            // Prepare next generation.
            while (newPopulation.size() < populationSize) {
                S p1 = selection.select(population);
                S p2 = selection.select(population);
                S child = crossover.crossover(p1, p2);
                child = mutation.mutate(child);
                newPopulation.add(child);
            }
            population = newPopulation;
        }

        System.out.println("Algorithm finished!");
        return bestSolution;
    }

}
