package hr.fer.zemris.fuzzy;

public abstract class Domain implements IDomain {

    @Override
    public int indexOfElement(DomainElement element) {
        int index = 0;
        for (DomainElement e : this) {
            if (e.equals(element)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public DomainElement elementForIndex(int index) {
        int temp = 0;
        for (DomainElement e : this) {
            if (temp == index) {
                return e;
            }
            temp++;
        }
        return null;
    }

    public static IDomain intRange(int first, int last) {
        return new SimpleDomain(first, last);
    }

    public static IDomain combine(IDomain d1, IDomain d2) {
        return null;
    }

}
