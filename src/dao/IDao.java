package dao;

import java.util.List;

public interface IDao<T, ID> {
    List<T> findall();
    T findById(ID id);
    T save(T object);
    T update(T object);
    Boolean deleteById(ID id);
    Boolean delete(T object);
}
