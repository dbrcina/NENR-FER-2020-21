package hr.fer.zemris.fuzzy;

import java.util.Arrays;
import java.util.StringJoiner;

public class DomainElement {

    private final int[] values;
    private final String stringRepresentation;

    public DomainElement(int[] values) {
        this.values = Arrays.copyOf(values, values.length);
        if (values.length == 1) {
            this.stringRepresentation = String.valueOf(values[0]);
        } else {
            StringJoiner sj = new StringJoiner(",", "(", ")");
            Arrays.stream(values)
                    .mapToObj(String::valueOf)
                    .forEach(sj::add);
            this.stringRepresentation = sj.toString();
        }
    }

    public int getNumberOfComponents() {
        return values.length;
    }

    public int getComponentValue(int index) {
        return values[index];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DomainElement)) return false;
        DomainElement that = (DomainElement) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }

    public static DomainElement of(int... values) {
        return new DomainElement(values);
    }

}
