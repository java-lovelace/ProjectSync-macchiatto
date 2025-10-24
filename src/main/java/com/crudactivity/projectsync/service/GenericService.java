package com.crudactivity.projectsync.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    T save(T object);
    void deleteById(Long id);
    T update(Long id,T object);
}
