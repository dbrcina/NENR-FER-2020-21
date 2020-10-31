package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;

public class KormiloFuzzySystemProduct extends FuzzySystem {

    public KormiloFuzzySystemProduct(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public double conclude(double... values) {
        return 0;
    }

}
