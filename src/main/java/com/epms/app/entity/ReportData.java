package com.epms.app.entity;

import lombok.Data;

@Data
public class ReportData
{
	private long studentRollNo;
	private String studentFullName;
	private String projectName;
	private String course;
	private String division;
	private String status;
	private String marks;
	private String submissionDate;
	private String reviewDate;
	private String reviewer;
}
