package com.crudactivity.projectsync.service;

import com.crudactivity.projectsync.entity.Status;
import com.crudactivity.projectsync.exception.NotFoundException;
import com.crudactivity.projectsync.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    private final StatusRepository repo;

    public StatusServiceImpl(StatusRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Status> getAll() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Status> getById(Long id) {
        Optional<Status> s = repo.findById(id);
        if (s.isEmpty()) {
            throw new NotFoundException("Status " + id + " not found");
        }
        return s;
    }

    @Override
    @Transactional(readOnly = true)
    public Status getByCode(String code) {
        return repo.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Status code " + code + " not found"));
    }

    @Override
    public Status save(Status s) {
        return repo.save(s);
    }

    @Override
    public Status update(Long id, Status s) {
        Status cur = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Status " + id + " not found"));
        cur.setCode(s.getCode());
        cur.setName(s.getName());
        cur.setDescription(s.getDescription());
        return repo.save(cur);
    }

    @Override
    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Status " + id + " not found");
        }
        repo.deleteById(id);
    }
}
