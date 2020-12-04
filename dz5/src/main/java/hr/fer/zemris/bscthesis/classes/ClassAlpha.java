package hr.fer.zemris.bscthesis.classes;

import java.awt.*;

public class ClassAlpha extends ClassType {

    public ClassAlpha(double[] actualOutputs) {
        super("CLASS_ALPHA", actualOutputs, null);
    }

    public ClassAlpha() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
