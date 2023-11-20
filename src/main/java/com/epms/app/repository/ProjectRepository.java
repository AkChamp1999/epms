package com.epms.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.epms.app.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
