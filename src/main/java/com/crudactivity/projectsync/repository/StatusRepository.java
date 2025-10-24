package com.crudactivity.projectsync.repository;

import com.crudactivity.projectsync.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByCode(String code);
}
