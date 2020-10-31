package hr.fer.zemris.fuzzy.defuzzify;

import hr.fer.zemris.fuzzy.set.IFuzzySet;

public interface Defuzzifier {

    double defuzzify(IFuzzySet set);

}
