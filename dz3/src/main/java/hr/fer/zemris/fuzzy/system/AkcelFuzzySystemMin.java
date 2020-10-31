package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;

public class AkcelFuzzySystemMin extends FuzzySystem {

    public AkcelFuzzySystemMin(Defuzzifier defuzzifier) {
        super(defuzzifier);
    }

    @Override
    public double conclude(double... values) {
        return 0;
    }

}
