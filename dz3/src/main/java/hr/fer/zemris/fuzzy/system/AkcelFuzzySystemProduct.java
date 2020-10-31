package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;

public class AkcelFuzzySystemProduct extends FuzzySystem {

    public AkcelFuzzySystemProduct(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public double conclude(double... values) {
        return 0;
    }

}
