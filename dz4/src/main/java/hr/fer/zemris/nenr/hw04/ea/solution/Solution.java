package hr.fer.zemris.nenr.hw04.ea.solution;

/**
 * Models a solution of evolutionary algorithm.
 *
 * @param <G> gene type.
 * @author dbrcina
 */
public abstract class Solution<G> implements Comparable<Solution<G>> {

    private double fitness;
    private boolean evaluated;

    /**
     * @return solution's fitness.
     * @throws IllegalStateException if solution hasn't been evaluated.
     */
    public double getFitness() {
        if (!isEvaluated()) {
            throw new IllegalStateException("Solution has not been evaluated.");
        }
        return fitness;
    }

    /**
     * Setts solution's fitness.
     *
     * @param fitness new fitness.
     */
    public void setFitness(double fitness) {
        this.fitness = fitness;
        evaluated = true;
    }

    /**
     * @return <code>true</code> if solution has been evaluated.
     */
    public boolean isEvaluated() {
        return evaluated;
    }

    /**
     * @return a number of solution's genes.
     */
    public abstract int getNumberOfGenes();

    /**
     * @param index index.
     * @return gene at the provided {@code index}.
     * @throws IndexOutOfBoundsException if the provided {@code index} is invalid.
     */
    public abstract G getGeneAt(int index);

    /**
     * Setts the provided {@code gene} at the provided {@code index}.
     *
     * @param gene  gene to be added.
     * @param index index.
     * @throws IndexOutOfBoundsException if the provided {@code index} is invalid.
     */
    public void setGeneAt(G gene, int index) {
        setGeneInternal(gene, index);
        evaluated = false;
    }

    /**
     * Derived solutions need to implement this method. It gets called by {@link #setGeneAt(Object, int)} method.
     */
    protected abstract void setGeneInternal(G gene, int index);

    /**
     * @return a copy of this solution.
     */
    public Solution<G> copy() {
        Solution<G> copied = copyInternal();
        copied.setFitness(getFitness());
        return copied;
    }

    /**
     * Derived solutions need to implement this method. It gets called by {@link #copy()} method.
     */
    protected abstract Solution<G> copyInternal();

    /**
     * Helper method used for index validation.
     */
    protected void checkIndexOutOfBounds(int index, int length) {
        if (index < 0 || index > length - 1) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index should be from [%d, %d] and %d was provided!",
                    0, length - 1, index)
            );
        }
    }

}
