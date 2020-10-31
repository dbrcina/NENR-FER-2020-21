package hr.fer.zemris.fuzzy.defuzzify;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

/* MeanOfMax */
public class MOMDefuzzifier implements Defuzzifier {

    @Override
    public double defuzzify(IFuzzySet set) {
        double sum = 0.0;
        int counter = 0;
        for (DomainElement element : set.getDomain()) {
            // Simple domain is expected.
            double xi = element.getComponentValue(0);
            double mi = set.getValueAt(element);
            if (1.0 - mi <= 1e-6) {
                sum += xi;
                counter++;
            }
        }
        return sum / counter;
    }

}
