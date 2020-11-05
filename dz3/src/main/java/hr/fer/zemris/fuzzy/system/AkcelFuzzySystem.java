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
        Rule r1 = new Rule(
                new IFuzzySet[]{BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null, null},
                BoatConstants.SLOW_DOWN,
                "AKO je L CLOSE_TO_SHORE I LK CLOSE_TO_SHORE ONDA akceleracija SLOW_DOWN"
        );
        Rule r2 = new Rule(
                new IFuzzySet[]{null, BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null},
                BoatConstants.SLOW_DOWN,
                "AKO je D CLOSE_TO_SHORE I DK CLOSE_TO_SHORE ONDA akceleracija SLOW_DOWN"
        );
        Rule r3 = new Rule(
                new IFuzzySet[]{null, null, null, null, BoatConstants.HIGH_VELOCITY, null},
                BoatConstants.SLOW_DOWN,
                "AKO je V HIGH_VELOCITY ONDA akceleracija SLOW_DOWN"
        );
        Rule r4 = new Rule(
                new IFuzzySet[]{BoatConstants.FAR_FROM_SHORE, null, BoatConstants.FAR_FROM_SHORE, null, null, null},
                BoatConstants.SPEED_UP,
                "AKO je L FAR_FROM_SHORE I LK FAR_FROM_SHORE ONDA akceleracija SPEED_UP"
        );
        Rule r5 = new Rule(
                new IFuzzySet[]{null, BoatConstants.FAR_FROM_SHORE, null, BoatConstants.FAR_FROM_SHORE, null, null},
                BoatConstants.SPEED_UP,
                "AKO je D FAR_FROM_SHORE I DK FAR_FROM_SHORE ONDA akceleracija SPEED_UP"
        );
        Rule r6 = new Rule(
                new IFuzzySet[]{null, null, null, null, BoatConstants.LOW_VELOCITY, null},
                BoatConstants.SPEED_UP,
                "AKO je V LOW_VELOCITY ONDA akceleracija SPEED_UP"
        );
        Rule r7 = new Rule(
                new IFuzzySet[]{BoatConstants.CLOSE_TO_SHORE, BoatConstants.CLOSE_TO_SHORE, null, null, null, null},
                BoatConstants.SPEED_UP,
                "AKO je L CLOSE_TO_SHORE I D CLOSE_TO_SHORE ONDA akceleracija SPEED_UP"
        );
        addRules(r1, r2, r3, r4, r5, r6, r7);
    }

}
