package com.epms.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Teacher
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "teacher_id")
	private Long teacherId;
	@Column(name = "full_name")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full Name should contain only letters")
	private String fullName;
	@Column(unique = true)
	private String email;
	@Size(min = 4, message = "Password should have at least 4 characters")
	private String password;

}
