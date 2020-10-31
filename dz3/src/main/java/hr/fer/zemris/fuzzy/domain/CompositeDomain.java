package hr.fer.zemris.fuzzy.domain;

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

        private final IDomain domain;
        private final int numberOfComponents;
        private final int[] componentsIndexes;
        private int counter;

        private CompositeDomainElementIterator(IDomain domain) {
            this.domain = domain;
            this.numberOfComponents = domain.getNumberOfComponents();
            this.componentsIndexes = new int[numberOfComponents];
        }

        @Override
        public boolean hasNext() {
            return counter != domain.getCardinality();
        }

        @Override
        public DomainElement next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // There needs to be at least two components for Cartesian product.
            if (numberOfComponents < 2) {
                return null;
            }
            // Prepare values.
            int[] values = new int[numberOfComponents];
            for (int i = 0; i < numberOfComponents; i++) {
                // This is only supported for SimpleDomain so getComponentValue(0) is called!
                values[i] = domain.getComponent(i).elementForIndex(componentsIndexes[i]).getComponentValue(0);
            }
            // Increment certain index.
            for (int i = numberOfComponents - 1; i >= 0; i--) {
                IDomain ithComponent = domain.getComponent(i);
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
