package hr.fer.zemris.nenr.hw04.ea.ga;

import hr.fer.zemris.nenr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.nenr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.nenr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.nenr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.nenr.hw04.ea.selection.Selection;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * An implementation of <i>generational genetic algorithm</i>.
 *
 * @author dbrcina
 */
public class EliminationGeneticAlgorithm<S extends Solution<?>> implements EvolutionaryAlgorithm<S> {

    private final int populationSize;
    private final int maxGenerations;
    private final double epsilon;
    private final PopulationInitializer<S> initializer;
    private final Selection<S> selection;
    private final Crossover<S> crossover;
    private final Mutation<S> mutation;
    private final FitnessFunction<S> fitnessFunction;

    public EliminationGeneticAlgorithm(
            int populationSize,
            int maxGenerations,
            double epsilon,
            PopulationInitializer<S> initializer,
            Selection<S> selection,
            Crossover<S> crossover,
            Mutation<S> mutation,
            FitnessFunction<S> fitnessFunction) {
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.epsilon = epsilon;
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
        population.forEach(s -> s.setFitness(fitnessFunction.calculateFitness(s)));
        System.out.println("Population initialized!");

        System.out.println("Starting generations...");
        for (int generation = 0; generation < maxGenerations; generation++) {

            // Find the best in current generation.
            S currentBest = Collections.max(population, Comparator.naturalOrder());
            if (bestSolution == null || currentBest.getFitness() > bestSolution.getFitness()) {
                bestSolution = currentBest;
                System.out.printf(
                        "Generation %6d: found new best solution with fitness %e and values %s%n",
                        generation + 1, bestSolution.getFitness(), bestSolution
                );
            }
            if (bestSolution.getFitness() >= epsilon) {
                break;
            }

            // Generate a new child.
            List<S> selected = selection.select(population);
            S child = crossover.crossover(selected.get(0), selected.get(1));
            child = mutation.mutate(child);
            child.setFitness(fitnessFunction.calculateFitness(child));

            // Replace the worst selected one with the child.
            population.set(population.indexOf(selected.get(selected.size() - 1)), child);
        }

        System.out.println("Algorithm finished!");
        return bestSolution;
    }

}
