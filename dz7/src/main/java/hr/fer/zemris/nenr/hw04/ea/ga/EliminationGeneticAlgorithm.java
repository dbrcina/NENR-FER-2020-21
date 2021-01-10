package hr.fer.zemris.nenr.hw04.ea.ga;

import hr.fer.zemris.nenr.hw04.ea.EvolutionaryAlgorithm;
import hr.fer.zemris.nenr.hw04.ea.crossover.Crossover;
import hr.fer.zemris.nenr.hw04.ea.fitness.FitnessFunction;
import hr.fer.zemris.nenr.hw04.ea.initializer.PopulationInitializer;
import hr.fer.zemris.nenr.hw04.ea.mutation.Mutation;
import hr.fer.zemris.nenr.hw04.ea.selection.Selection;
import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of <i>generational genetic algorithm</i>.
 *
 * @author dbrcina
 */
public class EliminationGeneticAlgorithm<S extends Solution<?>> implements EvolutionaryAlgorithm<S> {

    private final Random random;
    private final int populationSize;
    private final int maxGenerations;
    private final double epsilon;
    private final PopulationInitializer<S> initializer;
    private final Selection<S> selection;
    private final Crossover<S>[] crossovers;
    private final Mutation<S>[] mutations;
    private final double[] probabilities;
    private final FitnessFunction<S> fitnessFunction;

    @SuppressWarnings("unchecked")
    public EliminationGeneticAlgorithm(
            Random random,
            int populationSize,
            int maxGenerations,
            double epsilon,
            PopulationInitializer<S> initializer,
            Selection<S> selection,
            List<Crossover<S>> crossovers,
            List<Mutation<S>> mutations,
            int[] mutationsDesirability,
            FitnessFunction<S> fitnessFunction) {
        this.random = random;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.epsilon = epsilon;
        this.initializer = initializer;
        this.selection = selection;
        this.crossovers = crossovers.toArray(Crossover[]::new);
        double sum = Arrays.stream(mutationsDesirability).sum();
        Map<Mutation<S>, Double> helperMap = new HashMap<>();
        for (int i = 0; i < mutations.size(); i++) {
            helperMap.put(mutations.get(i), mutationsDesirability[i] / sum);
        }
        List<Map.Entry<Mutation<S>, Double>> sortedMutations = helperMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());
        this.mutations = sortedMutations.stream()
                .map(Map.Entry::getKey)
                .toArray(Mutation[]::new);
        double prevProb = 0.0;
        probabilities = new double[mutations.size()];
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = prevProb + sortedMutations.get(i).getValue();
            prevProb += probabilities[i];
        }
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
        int generation = 0;
        for (int iterations = 0; iterations < maxGenerations * populationSize; iterations++) {

            if (iterations % populationSize == 0) {
                generation += 1;
            }

            // Find the best in the current iteration.
            S currentBest = Collections.max(population, Comparator.comparingDouble(Solution::getFitness));
            if (bestSolution == null || currentBest.getFitness() > bestSolution.getFitness()) {
                bestSolution = currentBest;
                if (iterations % populationSize == 0) {
                    System.out.printf(
                            "Generation %6d: found new best solution with fitness %e%n",
                            (generation), bestSolution.getFitness()
                    );
                }
            }
            if (Math.abs(bestSolution.getFitness()) <= epsilon) {
                break;
            }

            // Generate a new child.
            S p1 = selection.select(population);
            S p2 = selection.select(population);
            S child = crossovers[random.nextInt(crossovers.length)].crossover(p1, p2);
            child = chooseMutation().mutate(child);
            child.setFitness(fitnessFunction.calculateFitness(child));

            // Find the worst solution from the population and possibly replace it with a new child.
            int worstIndex = -1;
            double worstFitness = child.getFitness();
            for (int i = 0; i < populationSize; i++) {
                S solution = population.get(i);
                if (solution.getFitness() < worstFitness) {
                    worstIndex = i;
                    worstFitness = solution.getFitness();
                }
            }
            if (worstIndex != -1) {
                population.set(worstIndex, child);
            }
        }

        System.out.println("Algorithm finished!");
        return bestSolution;
    }

    private Mutation<S> chooseMutation() {
        double r = random.nextDouble();
        for (int i = 0; i < probabilities.length; i++) {
            if (r <= probabilities[i]) {
                return mutations[i];
            }
        }
        return mutations[mutations.length - 1];
    }

}
