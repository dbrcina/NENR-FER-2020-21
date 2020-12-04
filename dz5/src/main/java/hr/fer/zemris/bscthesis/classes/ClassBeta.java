package hr.fer.zemris.bscthesis.classes;

import java.awt.*;

public class ClassBeta extends ClassType {

    public ClassBeta(double[] actualOutputs) {
        super("CLASS_BETA", actualOutputs, null);
    }

    public ClassBeta() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
