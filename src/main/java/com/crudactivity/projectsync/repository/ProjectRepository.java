package com.crudactivity.projectsync.repository;

import com.crudactivity.projectsync.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
