package com.epms.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Student {

	@Id
	@Column(name = "Roll_No")
	private Long rollNo;
	@Column(name = "Full_Name")
	private String fullName;
	@Column(unique = true)
	private String email;
	private String password;
	private String course;
	private String division;

}
