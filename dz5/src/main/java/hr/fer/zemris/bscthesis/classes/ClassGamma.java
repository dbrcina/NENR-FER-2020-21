package hr.fer.zemris.bscthesis.classes;

import java.awt.*;

public class ClassGamma extends ClassType {

    public ClassGamma(double[] actualOutputs) {
        super("CLASS_GAMMA", actualOutputs, null);
    }

    public ClassGamma() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
