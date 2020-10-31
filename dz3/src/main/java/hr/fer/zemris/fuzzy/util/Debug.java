package hr.fer.zemris.fuzzy.util;

import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.set.IFuzzySet;

import java.util.Locale;

public class Debug {

    public static void print(IDomain domain, String headingText) {
        if (headingText != null) {
            System.out.println(headingText);
        }
        for (DomainElement e : domain) {
            System.out.println("Element domene: " + e);
        }
        System.out.println("Kardinalitet domene je: " + domain.getCardinality());
        System.out.println();
    }

    public static void print(IFuzzySet set, String headingText) {
        if (headingText != null) {
            System.out.println(headingText);
        }
        IDomain domain = set.getDomain();
        for (DomainElement e : domain) {
            System.out.printf(Locale.US, "d(%s)=%f%n", e, set.getValueAt(e));
        }
        System.out.println();
    }

}
