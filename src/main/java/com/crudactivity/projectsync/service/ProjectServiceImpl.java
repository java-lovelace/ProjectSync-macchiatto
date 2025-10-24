package com.crudactivity.projectsync.service;

import com.crudactivity.projectsync.entity.Project;
import com.crudactivity.projectsync.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public Optional<Project> getById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project user) {
        return projectRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project update(Long id, Project project) {
        Optional<Project> optionalProject = projectRepository.findById(project.getId());
        if(optionalProject.isEmpty()){
            throw new RuntimeException();
        }
        return projectRepository.save(project);

    }
}
