package hr.fer.zemris.fuzzy.system;

import hr.fer.zemris.fuzzy.domain.Domain;
import hr.fer.zemris.fuzzy.domain.DomainElement;
import hr.fer.zemris.fuzzy.domain.IDomain;
import hr.fer.zemris.fuzzy.set.CalculatedFuzzySet;
import hr.fer.zemris.fuzzy.set.IFuzzySet;
import hr.fer.zemris.fuzzy.set.MutableFuzzySet;
import hr.fer.zemris.fuzzy.util.StandardFuzzySets;

public class BoatConstants {

    /* -------------------- DOMAINS -------------------- */
    public static final IDomain DISTANCES_DOMAIN = Domain.intRange(0, 1301);
    public static final IDomain VELOCITY_DOMAIN = Domain.intRange(0, 121);
    public static final IDomain ACCELERATION_DOMAIN = Domain.intRange(-45, 46);
    public static final IDomain ANGLES_DOMAIN = Domain.intRange(-90, 91);
    public static final IDomain DIRECTION_DOMAIN = Domain.intRange(0, 2);

    /* -------------------- FUZZY SETS ---------------------- */

    /* -------------------- ACCELERATION -------------------- */
    public static final IFuzzySet SPEED_UP = new CalculatedFuzzySet(
            ACCELERATION_DOMAIN,
            StandardFuzzySets.gammaFunction(
                    ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(20)),
                    ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(30)))
    );
    public static final IFuzzySet SLOW_DOWN = new CalculatedFuzzySet(
            ACCELERATION_DOMAIN,
            StandardFuzzySets.lFunction(
                    ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-20)),
                    ACCELERATION_DOMAIN.indexOfElement(DomainElement.of(-10)))
    );

    /* -------------------- KORMILO -------------------- */
    public static final IFuzzySet TURN_LEFT = new CalculatedFuzzySet(
            ANGLES_DOMAIN,
            StandardFuzzySets.gammaFunction(150, 180)
    );
    public static final IFuzzySet TURN_RIGHT = new CalculatedFuzzySet(
            ANGLES_DOMAIN,
            StandardFuzzySets.lFunction(0, 30)
    );

    /* -------------------- SHIP POSITION -------------------- */
    public static final IFuzzySet CLOSE_TO_SHORE = new CalculatedFuzzySet(
            DISTANCES_DOMAIN,
            StandardFuzzySets.lFunction(40, 70)
    );
    public static final IFuzzySet FAR_FROM_SHORE = new CalculatedFuzzySet(
            DISTANCES_DOMAIN,
            StandardFuzzySets.gammaFunction(70, 100)
    );

    /* -------------------- SHIP VELOCITY -------------------- */
    public static final IFuzzySet LOW_VELOCITY = new CalculatedFuzzySet(
            VELOCITY_DOMAIN,
            StandardFuzzySets.lFunction(40, 70)
    );
    public static final IFuzzySet HIGH_VELOCITY = new CalculatedFuzzySet(
            VELOCITY_DOMAIN,
            StandardFuzzySets.gammaFunction(70, 100)
    );

    /* -------------------- SHIP DIRECTION -------------------- */
    public static final IFuzzySet GOOD_DIRECTION = new MutableFuzzySet(DIRECTION_DOMAIN).set(DomainElement.of(1), 1.0);

}
