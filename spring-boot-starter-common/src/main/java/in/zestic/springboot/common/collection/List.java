package in.zestic.springboot.common.collection;

public interface List<T> {

    void add(T value);
    void remove(T value);
    int count();
}