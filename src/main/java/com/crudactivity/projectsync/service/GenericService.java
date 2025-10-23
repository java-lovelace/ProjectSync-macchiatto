package com.crudactivity.projectsync.service;

import com.crudactivity.projectsync.entity.User;

import java.util.List;
import java.util.Optional;

public interface GenericService<T> {
    List<T> getAll();
    Optional<T> getById(Long id);
    T save(T object);
    void deleteById(Long id);
    T update(T object);
}
