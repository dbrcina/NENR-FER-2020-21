package hr.fer.zemris.nenr.hw06;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author dbrcina
 */
public class ANFIS {

    private static final Function<double[], Double> MEMBERSHIP_FUNCTION = array -> {
        double x = array[0];
        double a = array[1];
        double b = array[2];
        return 1 / (1 + Math.exp(b * (x - a)));
    };

    private int numberOfRules;
    private double[] a;
    private double[] b;
    private double[] c;
    private double[] d;
    private double[] p;
    private double[] q;
    private double[] r;
    private boolean stochastic = true;
    private int epochs = (int) 1e6;
    private double tol = 1e-3;
    private double etaXY = 0.01;
    private double etaZ = 0.1;

    private ANFIS() {
    }

    public ANFIS fit(double[][] samples) {

        return this;
    }

    public ANFIS init(int numberOfRules, Consumer<double[]> action) {
        this.numberOfRules = numberOfRules;
        a = new double[numberOfRules];
        b = new double[numberOfRules];
        c = new double[numberOfRules];
        d = new double[numberOfRules];
        p = new double[numberOfRules];
        q = new double[numberOfRules];
        r = new double[numberOfRules];
        if (action != null) {
            action.accept(a);
            action.accept(b);
            action.accept(c);
            action.accept(d);
            action.accept(p);
            action.accept(q);
            action.accept(r);
        }
        return this;
    }

    public ANFIS setStochastic(boolean stochastic) {
        this.stochastic = stochastic;
        return this;
    }

    public ANFIS setEpochs(int epochs) {
        this.epochs = epochs;
        return this;
    }

    public ANFIS setTol(double tol) {
        this.tol = tol;
        return this;
    }

    public ANFIS setEtaXY(double etaXY) {
        this.etaXY = etaXY;
        return this;
    }

    public ANFIS setEtaZ(double etaZ) {
        this.etaZ = etaZ;
        return this;
    }

    public static ANFIS builder() {
        return new ANFIS();
    }

}
