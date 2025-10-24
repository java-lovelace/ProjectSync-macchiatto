package com.crudactivity.projectsync.service;

import com.crudactivity.projectsync.entity.Project;
import com.crudactivity.projectsync.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service // It is necessary for you to register the bean
@Transactional // Default
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> getById(Long id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project save(Project project) { // rename for clarity
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public Project update(Long id, Project project) {

        if (!projectRepository.existsById(id)) {
            throw new RuntimeException(); // ideally use a NotFoundException
        }

        //We ensure that you update the correct record
        return projectRepository.save(project);
    }
}
