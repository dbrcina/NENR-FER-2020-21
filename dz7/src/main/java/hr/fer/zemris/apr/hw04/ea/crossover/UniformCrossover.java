package hr.fer.zemris.apr.hw04.ea.crossover;

import hr.fer.zemris.apr.hw04.ea.solution.Solution;

import java.util.Random;

/**
 * @author dbrcina
 */
public class UniformCrossover implements Crossover<Solution<Double>> {

    private final Random random;

    public UniformCrossover(Random random) {
        this.random = random;
    }

    @Override
    public Solution<Double> crossover(Solution<Double> parent1, Solution<Double> parent2) {
        Solution<Double> child = parent1.copy();
        for (int i = 0; i < child.getNumberOfGenes(); i++) {
            double g1 = parent1.getGene(i);
            double g2 = parent2.getGene(i);
            double gene = random.nextDouble() < 0.5 ? g1 : g2;
            child.setGene(gene, i);
        }
        return child;
    }

}
