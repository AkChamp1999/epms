package com.epms.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epms.app.entity.Project;
import com.epms.app.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public Project saveProject(Project project) {

		return projectRepository.save(project);
	}

}
