package hr.fer.zemris.fuzzy;

import java.util.*;

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

        private final CompositeDomain compositeDomain;
        private final Iterator<DomainElement>[] iterators;
        private int counter;
        private DomainElement currentElement;

        private CompositeDomainElementIterator(CompositeDomain compositeDomain) {
            this.compositeDomain = compositeDomain;
            iterators = (Iterator<DomainElement>[]) new Object[compositeDomain.components.length];
            for (int i = 0; i < iterators.length; i++) {
                iterators[i] = compositeDomain.components[i].iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return counter != compositeDomain.cardinality;
        }

        @Override
        public DomainElement next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            // There needs to be at least two components for Cartesian product.
            if (compositeDomain.components.length < 2) {
                return null;
            }

            if (currentElement == null) {
                currentElement = iterators[0].next();
            }
            List<Integer> values = new ArrayList<>();
            for (int i = 1; i < compositeDomain.components.length; i++) {
                SimpleDomain component = compositeDomain.components[i];
                component.g
            }
        }

    }
