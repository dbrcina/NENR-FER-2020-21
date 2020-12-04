package hr.fer.zemris.bscthesis.classes;

import java.awt.*;

public class ClassNone extends ClassType {

    public ClassNone(double[] actualOutputs) {
        super("CLASS_NONE", actualOutputs, null);
    }

    @Override
    public Shape createShape(Rectangle rect) {
        return null;
    }

}
