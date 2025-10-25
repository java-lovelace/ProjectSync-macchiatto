package com.crudactivity.projectsync.service;

import com.crudactivity.projectsync.entity.Status;

public interface StatusService extends GenericService<Status> {
    Status getByCode(String code);
}
