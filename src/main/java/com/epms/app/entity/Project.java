package com.epms.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String projectName;

	@Column(nullable = false, length = 1000)
	private String description;

	@Column(nullable = false)
	private String synopsisDocument;

	@Column(nullable = false)
	private String demoVideo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ProjectStatus projectStatus;

	@ManyToOne
	@JoinColumn(name = "Roll_No", nullable = false)
	private Student student;

	public Project() {
		// Default constructor
	}

	public Project(String projectName, String description, String synopsisDocument, String demoVideo, Student student,
			ProjectStatus projectStatus) {
		this.projectName = projectName;
		this.description = description;
		this.synopsisDocument = synopsisDocument;
		this.demoVideo = demoVideo;
		this.projectStatus = projectStatus;
		this.student = student;
	}

}
