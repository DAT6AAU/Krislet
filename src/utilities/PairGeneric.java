package utilities;

/** Generic class that performs like a 2-tuple or pair of a given type. */
public class PairGeneric<T, E> {
    private T first;
    private E second;

    public PairGeneric(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public E getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(E second) {
        this.second = second;
    }
}
