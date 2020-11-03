package hr.fer.zemris.fuzzy.util;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.function.IBinaryFunction;
import hr.fer.zemris.fuzzy.function.IUnaryFunction;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.set.MutableFuzzySet;

public class Operations {

    public static IFuzzySet unaryOperation(IFuzzySet set, IUnaryFunction function) {
        MutableFuzzySet result = new MutableFuzzySet(set.getDomain());
        for (DomainElement e : set.getDomain()) {
            result.set(e, function.valueAt(set.getValueAt(e)));
        }
        return result;
    }

    public static IFuzzySet binaryOperation(IFuzzySet set1, IFuzzySet set2, IBinaryFunction function) {
        MutableFuzzySet result = new MutableFuzzySet(set1.getDomain());
        for (DomainElement e : set1.getDomain()) {
            result.set(e, function.valueAt(set1.getValueAt(e), set2.getValueAt(e)));
        }
        return result;
    }

    public static IUnaryFunction zadehNot() {
        return x -> 1 - x;
    }

    public static IBinaryFunction zadehAnd() {
        return Math::min;
    }

    public static IBinaryFunction zadehOr() {
        return Math::max;
    }

    public static IBinaryFunction product() {
        return (a, b) -> a * b;
    }

    public static IBinaryFunction hamacherTNorm(double v) {
        return (a, b) -> {
            double ab = a * b;
            return ab / (v + (1 - v) * (a + b - ab));
        };
    }

    public static IBinaryFunction hamacherSNorm(double v) {
        return (a, b) -> {
            double ab = a * b;
            return (a + b - (2 - v) * ab) / (1 - (1 - v) * ab);
        };
    }

}
