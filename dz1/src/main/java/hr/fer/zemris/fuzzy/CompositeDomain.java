package hr.fer.zemris.fuzzy;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositeDomain extends Domain {

    private final SimpleDomain[] components;
    private final int cardinality;

    public CompositeDomain(SimpleDomain... components) {
        this.components = components;
        this.cardinality = Arrays.stream(components)
                .mapToInt(SimpleDomain::getCardinality)
                .reduce(1, (a, b) -> a * b);
    }

    @Override
    public int getCardinality() {
        return cardinality;
    }

    @Override
    public IDomain getComponent(int index) {
        return components[index];
    }

    @Override
    public int getNumberOfComponents() {
        return components.length;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return new CompositeDomainElementIterator(this);
    }

    private static class CompositeDomainElementIterator implements Iterator<DomainElement> {

        private final SimpleDomain[] components;
        private final int[] componentsIndexes;
        private final int cardinality;
        private int counter;

        private CompositeDomainElementIterator(CompositeDomain compositeDomain) {
            this.components = compositeDomain.components;
            this.componentsIndexes = new int[components.length];
            this.cardinality = compositeDomain.cardinality;
        }

        @Override
        public boolean hasNext() {
            return counter != cardinality;
        }

        @Override
        public DomainElement next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // There needs to be at least two components for Cartesian product.
            if (components.length < 2) {
                return null;
            }
            // Prepare values.
            int[] values = new int[components.length];
            for (int i = 0; i < components.length; i++) {
                // This is only supported for SimpleDomain so getComponentValue(0) is called!
                values[i] = components[i].elementForIndex(componentsIndexes[i]).getComponentValue(0);
            }
            // Increment certain index.
            for (int i = components.length - 1; i >= 0; i--) {
                SimpleDomain ithComponent = components[i];
                if (componentsIndexes[i] >= ithComponent.getCardinality() - 1) {
                    componentsIndexes[i] = 0;
                } else {
                    componentsIndexes[i]++;
                    break;
                }
            }
            counter++;
            return DomainElement.of(values);
        }

    }

}
