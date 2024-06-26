package tqs.homework.busbook.util;
import java.util.Objects;

public class Pair<K, V>{
    
    private K value1;
    private V value2;

    public Pair() {
    }

    public Pair(K value1, V value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public K getValue1() {
        return this.value1;
    }

    public void setValue1(K value1) {
        this.value1 = value1;
    }

    public V getValue2() {
        return this.value2;
    }

    public void setValue2(V value2) {
        this.value2 = value2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pair<?, ?>)) {
            return false;
        }
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(value1, pair.value1) && Objects.equals(value2, pair.value2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value1, value2);
    }

    @Override
    public String toString() {
        return "{" +
            " value1='" + getValue1() + "'" +
            ", value2='" + getValue2() + "'" +
            "}";
    }

}
