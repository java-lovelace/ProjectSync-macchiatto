package com.crudactivity.projectsync.service;

import com.crudactivity.projectsync.entity.Project;
import com.crudactivity.projectsync.entity.Status;
import com.crudactivity.projectsync.entity.User;
import com.crudactivity.projectsync.exception.NotFoundException;
import com.crudactivity.projectsync.repository.ProjectRepository;
import com.crudactivity.projectsync.repository.StatusRepository;
import com.crudactivity.projectsync.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service // It is necessary for you to register the bean
@Transactional // Default
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository,
                              StatusRepository statusRepository,
                              UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Project> getById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new NotFoundException("Project with id " + id + " not found");
        }
        return project;
    }

    @Override
    public Project save(Project project) {
        attachRelations(project);
        return projectRepository.save(project);
    }

    @Override
    public void deleteById(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new NotFoundException("Project with id " + id + " not found");
        }
        projectRepository.deleteById(id);
    }

    @Override
    public Project update(Long id, Project project) {
        Project current = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id " + id + " not found"));


        project.setId(id);
        project.setCreateAt(current.getCreateAt());


        attachRelations(project);

        return projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Project getByCode(String code) {
        return projectRepository.findProjectByCode(code)
                .orElseThrow(() -> new NotFoundException("Project code " + code + " not found"));
    }

    // -------------------- helpers --------------------

    private void attachRelations(Project p) {
        if (p.getStatusId() != null) {
            Status st = statusRepository.findById(p.getStatusId())
                    .orElseThrow(() -> new NotFoundException("Status " + p.getStatusId() + " not found"));
            p.setStatus(st);
        }
        if (p.getUserId() != null) {
            User u = userRepository.findById(p.getUserId())
                    .orElseThrow(() -> new NotFoundException("User " + p.getUserId() + " not found"));
            p.setUser(u);
        }
    }

}
