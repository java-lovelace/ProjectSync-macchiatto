package com.crudactivity.projectsync.controllers;

import com.crudactivity.projectsync.entity.Project;
import com.crudactivity.projectsync.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController                               // Exposes REST endpoints
@RequestMapping("/api/projects")               // Base path for all project routes
public class ProjectController {

    // Inject the service that handles business logic + DB access
    private final ProjectService projectService;

    // Constructor injection
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // GET /api/projects returns all projects
    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectService.getAll());
    }

    // GET /api/projects/id returns one project by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    // POST /api/projects creates a new project
    // @Valid triggers bean validation on the request body
    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.save(project));
    }

    // PUT /api/projects/id updates an existing project
    // Uses the path id as the source of truth
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @Valid @RequestBody Project project) {
        return ResponseEntity.ok(projectService.update(id, project));
    }

    // DELETE /api/projects/id delete a project by id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        projectService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
