package hr.fer.zemris.fuzzy.implication;

import hr.fer.zemris.fuzzy.function.IBinaryFunction;

public class Mamdani implements Implication {

    private final IBinaryFunction function;
    private final boolean min;

    public Mamdani(IBinaryFunction function, boolean min) {
        this.function = function;
        this.min = min;
    }

    @Override
    public double apply(double a, double b) {
        return function.valueAt(a, b);
    }

    @Override
    public boolean hasLocalSemantics() {
        return true;
    }

    public boolean isMin() {
        return min;
    }

}
