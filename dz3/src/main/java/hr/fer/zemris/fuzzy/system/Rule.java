package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.implication.Implication;
import hr.fer.zemris.fuzzy.implication.Mamdani;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

public class Rule {

    private final IFuzzySet[] antecedent;
    private final IFuzzySet consequence;
    private final String description;

    public Rule(IFuzzySet[] antecedent, IFuzzySet consequence, String description) {
        this.antecedent = antecedent;
        this.consequence = consequence;
        this.description = description;
    }

    public IFuzzySet apply(double[] values, Implication implication, IBinaryFunction tNorm) {
        double mi = 1.0;
        for (int i = 0; i < antecedent.length; i++) {
            if (antecedent[i] != null) {
                mi = tNorm.valueAt(mi, antecedent[i].getValueAt(DomainElement.of((int) values[i])));
            }
        }
        return ((Mamdani) implication).isMin() ? consequence.cut(mi) : consequence.scale(mi);
    }

    public String getDescription() {
        return description;
    }

}
