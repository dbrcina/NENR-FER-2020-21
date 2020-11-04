package hr.fer.zemris.fuzzy.implication;

import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.util.Operations;

public class Mamdani implements Implication {

    private final boolean min;
    private final IBinaryFunction function;

    public Mamdani(boolean min) {
        this.min = min;
        function = min ? Operations.zadehAnd() : Operations.product();
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
