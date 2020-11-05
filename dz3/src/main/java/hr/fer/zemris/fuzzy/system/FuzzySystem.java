package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.util.Operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class FuzzySystem {

    private final Implication implication;
    private final IBinaryFunction tNorm;
    private final IBinaryFunction sNorm;
    private final Defuzzifier defuzzifier;

    private final List<Rule> rules = new ArrayList<>();

    protected FuzzySystem(
            Implication implication,
            IBinaryFunction tNorm,
            IBinaryFunction sNorm,
            Defuzzifier defuzzifier) {
        this.implication = implication;
        this.tNorm = tNorm;
        this.sNorm = sNorm;
        this.defuzzifier = defuzzifier;
        init();
    }

    protected void addRules(Rule... rules) {
        this.rules.addAll(Arrays.asList(rules));
    }

    public List<Rule> getRules() {
        return Collections.unmodifiableList(rules);
    }

    public double conclude(double... values) {
        return defuzzifier.defuzzify(fuzzifiedConclusion(values));
    }

    public IFuzzySet fuzzifiedConclusion(double... values) {
        // Apply rules.
        IFuzzySet[] consequences = rules.stream()
                .map(rule -> rule.apply(values, implication, tNorm))
                .toArray(IFuzzySet[]::new);
        // Depending on local/global semantics, generate result.
        IFuzzySet result = consequences[0];
        for (int i = 1; i < consequences.length; i++) {
            result = Operations.binaryOperation(result, consequences[i],
                    implication.hasLocalSemantics() ? sNorm : tNorm);
        }
        return result;
    }

    protected abstract void init();

}
