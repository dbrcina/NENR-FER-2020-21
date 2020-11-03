package hr.fer.zemris.fuzzy.set;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;

public class MutableFuzzySet implements IFuzzySet {

    private final IDomain domain;
    private final double[] memberships;

    public MutableFuzzySet(IDomain domain) {
        this.domain = domain;
        memberships = new double[domain.getCardinality()];
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return memberships[domain.indexOfElement(element)];
    }

    @Override
    public IFuzzySet cut(double mi) {
        MutableFuzzySet result = new MutableFuzzySet(domain);
        for (int i = 0; i < memberships.length; i++) {
            result.memberships[i] = Math.min(mi, memberships[i]);
        }
        return result;
    }

    @Override
    public IFuzzySet scale(double mi) {
        MutableFuzzySet result = new MutableFuzzySet(domain);
        for (int i = 0; i < memberships.length; i++) {
            result.memberships[i] = memberships[i] * mi;
        }
        return result;
    }

    public MutableFuzzySet set(DomainElement element, double value) {
        memberships[domain.indexOfElement(element)] = value;
        return this;
    }

}
