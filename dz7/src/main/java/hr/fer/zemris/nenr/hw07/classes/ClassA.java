package hr.fer.zemris.nenr.hw07.classes;

import hr.fer.zemris.bscthesis.classes.ClassType;

import java.awt.*;

public class ClassA extends ClassType {

    public ClassA(double[] actualOutputs) {
        super("CLASS_A", actualOutputs, null);
    }

    public ClassA() {
        this(null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
