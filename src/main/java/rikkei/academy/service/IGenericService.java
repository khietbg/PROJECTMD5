package rikkei.academy.service;

import java.util.List;

public interface IGenericService <T,E>{
   Iterable<T> findAll();
   T findById(E id);
   T save(T t);
   void deleteById(E id);
}
