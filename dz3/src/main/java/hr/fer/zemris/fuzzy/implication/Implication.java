package hr.fer.zemris.fuzzy.implication;

public interface Implication {

    double apply(double a, double b);

    boolean hasLocalSemantics();

}
