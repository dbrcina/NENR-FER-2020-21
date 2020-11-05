package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

public class KormiloFuzzySystem extends FuzzySystem {

    public KormiloFuzzySystem(
            Implication implication,
            IBinaryFunction tNorm,
            IBinaryFunction sNorm,
            Defuzzifier defuzzifier) {
        super(implication, tNorm, sNorm, defuzzifier);
    }

    @Override
    protected void init() {
        Rule r1 = new Rule(
                new IFuzzySet[]{BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null, null},
                BoatConstants.TURN_RIGHT,
                "AKO je L CLOSE_TO_SHORE I LK CLOSE_TO_SHORE ONDA kormilo TURN_RIGHT"
        );
        Rule r2 = new Rule(
                new IFuzzySet[]{null, BoatConstants.CLOSE_TO_SHORE, null, BoatConstants.CLOSE_TO_SHORE, null, null},
                BoatConstants.TURN_LEFT,
                "AKO je D CLOSE_TO_SHORE I DK CLOSE_TO_SHORE ONDA kormilo TURN_LEFT"
        );
        addRules(r1, r2);
    }

}
