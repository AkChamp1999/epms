package com.epms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epms.app.entity.ProjectReview;

public interface ProjectReviewRepository extends JpaRepository<ProjectReview, Long>
{
	ProjectReview findTopByProject_ProjectIdOrderByReviewDateTimeDesc(Long projectId);
}
