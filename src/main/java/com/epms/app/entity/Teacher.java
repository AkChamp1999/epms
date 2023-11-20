package com.epms.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Teacher {

	@Id
	@Column(name = "Teacher_Id")
	private Long teacher_id;
	@Column(name = "Full_Name")
	private String fullName;
	@Column(unique = true)
	private String username;
	private String password;

}
