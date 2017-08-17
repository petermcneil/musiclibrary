package petermcneil.musiclibrary.services;

import java.util.List;

public interface CRUDService<T> {
    T get(Integer objectId);

    List<T> getList();

    Integer post(T object);

    void put(T object, Integer objectId);

    void delete(Integer objectId);
}
