package hr.fer.zemris.bscthesis.classes;

import java.awt.*;

public class ClassEpsilon extends ClassType {

    public ClassEpsilon(double[] actualOutputs) {
        super("CLASS_EPSILON", actualOutputs, null);
    }

    public ClassEpsilon() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
