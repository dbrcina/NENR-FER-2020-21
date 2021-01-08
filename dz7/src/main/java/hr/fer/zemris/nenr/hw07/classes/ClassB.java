package hr.fer.zemris.nenr.hw07.classes;

import hr.fer.zemris.bscthesis.classes.ClassType;

import java.awt.*;

public class ClassB extends ClassType {

    public ClassB(double[] actualOutputs) {
        super("CLASS_B", actualOutputs, null);
    }

    public ClassB() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
