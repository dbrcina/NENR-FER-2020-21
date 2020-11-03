package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.defuzzify.Defuzzifier;
import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.util.Operations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class FuzzySystem {

    private final Implication implication;
    private final IBinaryFunction tNormFunction;
    private final IBinaryFunction sNormFunction;
    private final Defuzzifier defuzzifier;

    private final Map<IFuzzySet[], IFuzzySet> rules = new HashMap<>();

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
        IFuzzySet[] consequences = new IFuzzySet[rules.size()];
        int index = 0;
        for (Map.Entry<IFuzzySet[], IFuzzySet> rule : rules.entrySet()) {
            IFuzzySet[] antecedent = rule.getKey();
            IFuzzySet consequence = rule.getValue();
            double mi = 1.0;
            for (int i = 0; i < antecedent.length; i++) {
                if (antecedent[i] != null) {
                    mi = tNormFunction.valueAt(values[i], antecedent[i].getValueAt(DomainElement.of((int) values[i])));
                }
            }
            consequences[index++] = ((Mamdani) implication).isMin() ? consequence.cut(mi) : consequence.scale(mi);
        }
        // Depending on local/global semantics, generate result.
        IFuzzySet union = consequences[0];
        for (int i = 1; i < consequences.length; i++) {
            union = Operations.binaryOperation(union, consequences[1],
                    implication.hasLocalSemantics() ? sNormFunction : tNormFunction);
        }
        return defuzzifier.defuzzify(union);
    }

    protected abstract void init();

}
