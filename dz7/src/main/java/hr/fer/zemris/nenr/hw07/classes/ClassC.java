package hr.fer.zemris.nenr.hw07.classes;

import hr.fer.zemris.bscthesis.classes.ClassType;

import java.awt.*;

public class ClassC extends ClassType {

    public ClassC(double[] actualOutputs) {
        super("CLASS_C", actualOutputs, null);
    }

    public ClassC() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
