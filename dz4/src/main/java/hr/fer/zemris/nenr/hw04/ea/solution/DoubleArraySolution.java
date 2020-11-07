package hr.fer.zemris.nenr.hw04.ea.solution;

import java.util.Arrays;

/**
 * An implementation of {@link Solution} abstract class that uses double array for storing solution's genes.
 *
 * @author dbrcina
 */
public class DoubleArraySolution extends Solution<Double> {

    private final double[] genes;

    public DoubleArraySolution(double[] genes) {
        this.genes = Arrays.copyOf(genes, genes.length);
    }

    @Override
    public int getNumberOfGenes() {
        return genes.length;
    }

    @Override
    public Double getGeneAt(int index) {
        checkIndexOutOfBounds(index, getNumberOfGenes());
        return genes[index];
    }

    @Override
    protected void setGeneInternal(Double gene, int index) {
        checkIndexOutOfBounds(index, getNumberOfGenes());
        genes[index] = gene;
    }

    @Override
    protected Solution<Double> copyInternal() {
        return new DoubleArraySolution(genes);
    }

    @Override
    public String toString() {
        return Arrays.toString(genes);
    }

}
