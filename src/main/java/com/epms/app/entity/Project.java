package com.epms.app.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Project
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private Long projectId;

	private String projectName;

	@Column(length = 1000)
	private String description;

	private String synopsisDocument;

	private String demoVideo;

	private String semester;

	@Range(min = 0, max = 100, message = "Marks should be between 0 and 100")
	private int marks;

	@Temporal(TemporalType.DATE)
	private Date submissionDate;

	@ManyToOne
	@JoinColumn(name = "Roll_No", nullable = false)
	private Student student;

	private String status;

	public Project()
	{
		// Default constructor
	}

	public Project(String projectName, String description, String synopsisDocument, String demoVideo, Student student)
	{
		this.projectName = projectName;
		this.description = description;
		this.synopsisDocument = synopsisDocument;
		this.demoVideo = demoVideo;
		this.student = student;
		this.submissionDate = new Date();
	}

}
