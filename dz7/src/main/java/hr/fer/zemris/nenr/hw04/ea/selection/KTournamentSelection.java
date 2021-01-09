package hr.fer.zemris.nenr.hw04.ea.selection;

import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

import java.util.*;

/**
 * An implementation of {@link Selection} interface which provides <i>k-tournament selection</i>.
 *
 * @author dbrcina
 */
public class KTournamentSelection<S extends Solution<?>> implements Selection<S> {

    private final Random random;
    private final int k;

    public KTournamentSelection(Random random, int k) {
        if (k < 2) {
            throw new IllegalArgumentException("K needs to be >= 2!");
        }
        this.random = random;
        this.k = k;
    }

    @Override
    public S select(List<S> population) {
        Set<Integer> usedIndexes = new HashSet<>();
        List<S> selected = new ArrayList<>();
        while (true) {
            int index = random.nextInt(population.size());
            if (usedIndexes.contains(index)) continue;
            selected.add(population.get(index));
            usedIndexes.add(index);
            if (selected.size() == k) break;
        }
        return Collections.max(selected, Comparator.comparingDouble(Solution::getFitness));
    }

}
