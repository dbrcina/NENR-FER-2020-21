package hr.fer.zemris.nenr.hw04.ea.selection;

import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * An implementation of {@link Selection} interface which provides <i>roulette-wheel selection</i>.
 *
 * @author dbrcina
 */
public class RouletteWheelSelection<S extends Solution<?>> implements Selection<S> {

    private final Random random;

    public RouletteWheelSelection(Random random) {
        this.random = random;
    }

    @Override
    public S select(List<S> population) {
        // Sort by fitness values in ascending order, so first solution will be the worst.
        List<S> sorted = new ArrayList<>();
        population.forEach(s -> sorted.add((S) s.copy()));
        sorted.sort(Comparator.comparingDouble(Solution::getFitness));
        // Subtract fitness values by the lowest fitness value to get rid of the negative fitness.
        double theWorstFitness = sorted.get(0).getFitness();
        sorted.forEach(s -> s.setFitness(s.getFitness() - theWorstFitness));

        // Prepare probabilities for each solution.
        double fitnessSum = sorted.stream()
                .mapToDouble(Solution::getFitness)
                .sum();
        double[] probabilityOffsets = new double[sorted.size()];
        probabilityOffsets[0] = 0.0;
        for (int i = 1; i < probabilityOffsets.length; i++) {
            probabilityOffsets[i] = probabilityOffsets[i - 1] + sorted.get(i).getFitness() / fitnessSum;
        }

        for (int i = 0; i < probabilityOffsets.length; i++) {
            if (random.nextDouble() < probabilityOffsets[i]) {
                return sorted.get(i);
            }
        }
        return sorted.get(sorted.size() - 1);
    }

}
