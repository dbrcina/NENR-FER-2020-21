package hr.fer.zemris.fuzzy;

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
