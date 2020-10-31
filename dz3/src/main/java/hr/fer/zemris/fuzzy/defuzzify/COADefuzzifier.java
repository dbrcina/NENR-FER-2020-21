package hr.fer.zemris.fuzzy.defuzzify;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

/* CenterOfArea: sum(miA(xi) * xi) / sum(miA(xi))  */
public class COADefuzzifier implements Defuzzifier {

    @Override
    public double defuzzify(IFuzzySet set) {
        double nominator = 0.0;
        double denominator = 0.0;
        for (DomainElement element : set.getDomain()) {
            // Simple domain is expected.
            double xi = element.getComponentValue(0);
            double mi = set.getValueAt(element);
            nominator += mi * xi;
            denominator += mi;
        }
        return nominator / denominator;
    }

}
