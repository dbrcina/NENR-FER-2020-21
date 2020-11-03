package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;

public class KormiloFuzzySystem extends FuzzySystem {

    public KormiloFuzzySystem(
            Implication implication,
            IBinaryFunction tNormFunction,
            IBinaryFunction sNormFunction,
            Defuzzifier defuzzifier) {
        super(implication, tNormFunction, sNormFunction, defuzzifier);
    }

    @Override
    protected void init() {

    }

}
