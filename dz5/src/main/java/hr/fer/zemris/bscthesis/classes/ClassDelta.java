package hr.fer.zemris.bscthesis.classes;

import java.awt.*;

public class ClassDelta extends ClassType {

    public ClassDelta(double[] actualOutputs) {
        super("CLASS_DELTA", actualOutputs, null);
    }

    public ClassDelta() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
