package dao;

import java.util.List;

public interface IDao<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T object);
    T update(T object);
    Boolean deleteById(ID id);
    Boolean delete(T object);
    Long  getId();
    void setId(Long id);
    List<T> findByKeyWord(String keyword);
}
