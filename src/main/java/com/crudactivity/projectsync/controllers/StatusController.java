package com.crudactivity.projectsync.controllers;

import com.crudactivity.projectsync.entity.Status;
import com.crudactivity.projectsync.exception.NotFoundException;
import com.crudactivity.projectsync.service.StatusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService service;

    public StatusController(StatusService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> all() { return ResponseEntity.ok(service.getAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id) {
        Status s = service.getById(id).orElseThrow(() -> new NotFoundException("Status " + id + " not found"));
        return ResponseEntity.ok(s);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Status s) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(s));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Status s) {
        return ResponseEntity.ok(service.update(id, s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
