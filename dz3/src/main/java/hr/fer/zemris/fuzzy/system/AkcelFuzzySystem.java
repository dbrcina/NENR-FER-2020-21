package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

public class AkcelFuzzySystem extends FuzzySystem {

    public AkcelFuzzySystem(
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
                BoatConstants.SLOW_DOWN
        );
        addRule(
                new IFuzzySet[]{null, BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null},
                BoatConstants.SLOW_DOWN
        );
        addRule(
                new IFuzzySet[]{null, null, null, null, BoatConstants.HIGH_VELOCITY, null},
                BoatConstants.SLOW_DOWN
        );
        addRule(
                new IFuzzySet[]{null, null, BoatConstants.FAR_FROM_SHORE, null, null, null},
                BoatConstants.SPEED_UP
        );
        addRule(
                new IFuzzySet[]{null, null, null, BoatConstants.FAR_FROM_SHORE, null, null},
                BoatConstants.SPEED_UP
        );
        addRule(
                new IFuzzySet[]{null, null, null, null, BoatConstants.LOW_VELOCITY, null},
                BoatConstants.SPEED_UP
        );
        addRule(
                new IFuzzySet[]{BoatConstants.CLOSE_TO_SHORE, BoatConstants.CLOSE_TO_SHORE, null, null, null, null},
                BoatConstants.SPEED_UP
        );
    }

}
