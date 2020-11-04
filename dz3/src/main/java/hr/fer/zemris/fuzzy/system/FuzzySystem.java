package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.util.Operations;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class FuzzySystem {

    private final Implication implication;
    private final IBinaryFunction tNormFunction;
    private final IBinaryFunction sNormFunction;
    private final Defuzzifier defuzzifier;

    private final Map<IFuzzySet[], IFuzzySet> rules = new LinkedHashMap<>();

    protected FuzzySystem(
            Implication implication,
            IBinaryFunction tNormFunction,
            IBinaryFunction sNormFunction,
            Defuzzifier defuzzifier) {
        this.implication = implication;
        this.tNormFunction = tNormFunction;
        this.sNormFunction = sNormFunction;
        this.defuzzifier = defuzzifier;
        init();
    }

    protected void addRule(IFuzzySet[] antecedent, IFuzzySet consequence) {
        rules.put(antecedent, consequence);
    }

    public Map<IFuzzySet[], IFuzzySet> getRules() {
        return Collections.unmodifiableMap(rules);
    }

    public double conclude(double... values) {
        return defuzzifier.defuzzify(fuzzifiedConclusion(values));
    }

    public IFuzzySet fuzzifiedConclusion(double... values) {
        // Apply rules.
        IFuzzySet[] consequences = rules.entrySet().stream()
                .map(rule -> applyRule(rule, values))
                .toArray(IFuzzySet[]::new);
        // Depending on local/global semantics, generate result.
        IFuzzySet result = consequences[0];
        for (int i = 1; i < consequences.length; i++) {
            result = Operations.binaryOperation(result, consequences[i],
                    implication.hasLocalSemantics() ? sNormFunction : tNormFunction);
        }
        return result;
    }

    public IFuzzySet applyRule(Map.Entry<IFuzzySet[], IFuzzySet> rule, double... values) {
        IFuzzySet[] antecedent = rule.getKey();
        IFuzzySet consequence = rule.getValue();
        double mi = 1.0;
        for (int i = 0; i < antecedent.length; i++) {
            if (antecedent[i] != null) {
                mi = tNormFunction.valueAt(mi, antecedent[i].getValueAt(DomainElement.of((int) values[i])));
            }
        }
        return ((Mamdani) implication).isMin() ? consequence.cut(mi) : consequence.scale(mi);
    }

    protected abstract void init();

}
