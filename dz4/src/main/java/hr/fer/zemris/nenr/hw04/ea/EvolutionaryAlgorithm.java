package hr.fer.zemris.nenr.hw04.ea;

import hr.fer.zemris.nenr.hw04.ea.solution.Solution;

/**
 * Models an evolutionary algorithm that provides only {@link #run()} method.
 *
 * @param <S> solution type.
 * @author dbrcina
 */
public interface EvolutionaryAlgorithm<S extends Solution<?>> {

    /**
     * Runs this optimization algorithm.
     *
     * @return calculated solution.
     */
    S run();

}
