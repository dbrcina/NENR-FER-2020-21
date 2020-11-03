package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.domain.Domain;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.set.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.util.StandardFuzzySets;

public class Util {

    /* -------------------- CONSTANTS -------------------- */
    public static final int DISTANCE_L = 0;
    public static final int DISTANCE_U = 200;
    public static final int VELOCITY_L = 0;
    public static final int VELOCITY_U = 50;
    public static final int ACCELERATION_L = -15;
    public static final int ACCELERATION_U = 15;
    public static final int ANGLES_L = -90;
    public static final int ANGLES_U = 90;

    /* -------------------- DOMAINS -------------------- */
    public static final IDomain DISTANCES_DOMAIN = Domain.intRange(DISTANCE_L, DISTANCE_U + 1);
    public static final IDomain VELOCITY_DOMAIN = Domain.intRange(VELOCITY_L, VELOCITY_U + 1);
    public static final IDomain ACCELERATION_DOMAIN = Domain.intRange(ACCELERATION_L, ACCELERATION_U + 1);
    public static final IDomain ANGLES_DOMAIN = Domain.intRange(ANGLES_L, ANGLES_U + 1);
    public static final IDomain DIRECTION_DOMAIN = Domain.intRange(0, 2);

    /* -------------------- FUZZY SETS -------------------- */
    public static IFuzzySet kriticnoBlizu() {
        return new CalculatedFuzzySet(DISTANCES_DOMAIN, StandardFuzzySets.lFunction(20, 50));
    }

    public static IFuzzySet blizu() {
        return new CalculatedFuzzySet(DISTANCES_DOMAIN, StandardFuzzySets.lFunction(50, 100));
    }

}
