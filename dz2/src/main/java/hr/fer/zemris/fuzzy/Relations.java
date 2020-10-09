package hr.fer.zemris.fuzzy;

import java.util.Arrays;

public class Relations {

    public static boolean isUTimesURelation(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        IDomain u1 = domain.getComponent(0);
        IDomain u2 = domain.getComponent(1);
        IDomain u1Xu2 = Domain.combine(u1, u2);
        for (int i = 0; i < domain.getCardinality(); i++) {
            if (!domain.elementForIndex(i).equals(u1Xu2.elementForIndex(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        for (DomainElement e : relation.getDomain()) {
            // e -> (a,b)
            // r -> (b,a)
            DomainElement r = DomainElement.of(e.getComponentValue(1), e.getComponentValue(0));
            if (relation.getValueAt(e) != relation.getValueAt(r)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        int dim = (int) Math.sqrt(domain.getCardinality());
        for (int i = 0; i < domain.getCardinality(); i += dim + 1) {
            // look only on diagonal
            if (relation.getValueAt(domain.elementForIndex(i)) != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        IDomain u1 = domain.getComponent(0);
        IDomain u2 = domain.getComponent(1);
        for (int i = 0; i < u1.getCardinality(); i++) {
            int x = u1.elementForIndex(i).getComponentValue(0);
            for (int j = 0; j < u1.getCardinality(); j++) {
                int z = u2.elementForIndex(j).getComponentValue(0);
                double miXZ = relation.getValueAt(DomainElement.of(x, z));
                double[] minMiValues = new double[u1.getCardinality()];
                int index = 0;
                for (DomainElement e : u1) {
                    int y = e.getComponentValue(0);
                    DomainElement xy = DomainElement.of(x, y);
                    DomainElement yz = DomainElement.of(y, z);
                    minMiValues[index++] = Math.min(relation.getValueAt(xy), relation.getValueAt(yz));
                }
                if (miXZ < Arrays.stream(minMiValues).max().getAsDouble()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {
        IDomain d1 = r1.getDomain();
        IDomain d2 = r2.getDomain();
        IDomain u = d1.getComponent(0);
        IDomain v = d1.getComponent(1);
        IDomain w = d2.getComponent(1);
        MutableFuzzySet result = new MutableFuzzySet(Domain.combine(u, w));
        for (int i = 0; i < u.getCardinality(); i++) {
            int uIthElement = u.elementForIndex(i).getComponentValue(0);
            for (int j = 0; j < w.getCardinality(); j++) {
                int wJthElement = w.elementForIndex(j).getComponentValue(0);
                double[] minMiValues = new double[v.getCardinality()];
                int index = 0;
                for (int k = 0; k < v.getCardinality(); k++) {
                    int vKthElement = v.elementForIndex(k).getComponentValue(0);
                    DomainElement uvIKElement = DomainElement.of(uIthElement, vKthElement);
                    DomainElement vwKJElement = DomainElement.of(vKthElement, wJthElement);
                    minMiValues[index++] = Math.min(r1.getValueAt(uvIKElement), r2.getValueAt(vwKJElement));
                }
                DomainElement uwIJElement = DomainElement.of(uIthElement, wJthElement);
                double miMax = Arrays.stream(minMiValues).max().getAsDouble();
                result.set(uwIJElement, miMax);
            }
        }
        return result;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet relation) {
        return isReflexive(relation) && isSymmetric(relation) && isMaxMinTransitive(relation);
    }

}
