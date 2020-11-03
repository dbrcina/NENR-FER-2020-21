package hr.fer.zemris.fuzzy.set;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.function.IIntUnaryFunction;

public class CalculatedFuzzySet implements IFuzzySet {

    private final IDomain domain;
    private final IIntUnaryFunction function;

    public CalculatedFuzzySet(IDomain domain, IIntUnaryFunction function) {
        this.domain = domain;
        this.function = function;
    }

    @Override
    public IDomain getDomain() {
        return domain;
    }

    @Override
    public double getValueAt(DomainElement element) {
        return function.valueAt(domain.indexOfElement(element));
    }

    @Override
    public IFuzzySet cut(double mi) {
        return new IFuzzySet() {
            @Override
            public IDomain getDomain() {
                return domain;
            }

            @Override
            public double getValueAt(DomainElement element) {
                return Math.min(mi, function.valueAt(domain.indexOfElement(element)));
            }

            @Override
            public IFuzzySet cut(double mi) {
                return null;
            }

            @Override
            public IFuzzySet scale(double mi) {
                return null;
            }
        };
    }

    @Override
    public IFuzzySet scale(double mi) {
        return new IFuzzySet() {
            @Override
            public IDomain getDomain() {
                return domain;
            }

            @Override
            public double getValueAt(DomainElement element) {
                return mi * function.valueAt(domain.indexOfElement(element));
            }

            @Override
            public IFuzzySet cut(double mi) {
                return null;
            }

            @Override
            public IFuzzySet scale(double mi) {
                return null;
            }
        };
    }

}
