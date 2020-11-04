package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

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
        addRule(
                new IFuzzySet[]{BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null, null},
                BoatConstants.TURN_RIGHT
        );
        addRule(
                new IFuzzySet[]{null, BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null},
                BoatConstants.TURN_LEFT
        );
    }

}
