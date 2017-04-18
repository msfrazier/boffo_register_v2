package bundles;

import java.util.Objects;

public class Pair<T> {

    private T element;
    private int number;

    Pair(T element, int number) {
        this.element = element;
        this.number = number;
    }

    public T getElement() {
        return element;
    }

    public int getNumber() {
        return number;
    }

    public int add(int adder) {
        this.number += adder;
        return this.number;
    }

    public int subtract(int subtracter) {
        this.number -= subtracter;
        return Math.max(0, number);
    }

    public int increment() {
        return add(+1);
    }

    public int decrement() {
        return add(-1);
    }

    @Override
    public String toString() {
        return "(" + element + ", " + number + ')';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair<?> other = (Pair<?>) obj;
        if (!Objects.equals(this.element, other.element)) {
            return false;
        }
        return true;
    }

    @Override
    public Pair<T> clone() {
        return new Pair(element, number);
    }

}
