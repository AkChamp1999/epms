package com.epms.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Student
{

	@Id
	@Column(name = "roll_no")
	@Positive(message = "Roll No should be a positive whole number")
	private Long rollNo;
	@Column(name = "full_name")
	@Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full Name should contain only letters")
	private String fullName;
	@Column(unique = true)
	private String email;
	@Size(min = 4, message = "Password should have at least 4 characters")
	private String password;
	private String batchYear;
	private String course;
	private String division;

}
