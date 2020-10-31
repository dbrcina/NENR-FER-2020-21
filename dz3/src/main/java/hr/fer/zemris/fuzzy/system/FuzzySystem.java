package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;

public abstract class FuzzySystem {

    private final Defuzzifier defuzzifier;

    protected FuzzySystem(Defuzzifier defuzzifier) {
        this.defuzzifier = defuzzifier;
    }

    public abstract double conclude(double... values);

}
