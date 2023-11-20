package com.epms.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epms.app.entity.Student;
import com.epms.app.repository.StudentRepository;
import com.epms.app.service.ProjectService;

@Controller
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ProjectService projectService;

	@GetMapping("/login-student")
	public String showStudentLoginForm() {
		return "login-student";
	}

	@PostMapping("/login-student")
	public String studentLogin(@RequestParam String email, @RequestParam String password, Model model) {

		Student user = studentRepository.findByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			// Store user in session for authentication
			model.addAttribute("user", user);
			return "redirect:/dashboard";
		} else {
			System.out.println("Invalid username or password");
			model.addAttribute("loginFailed", "Invalid username or password");
			return "login-student";
		}
	}

	@GetMapping("/register-student")
	public String showStudentRegistrationForm(Model model) {
		model.addAttribute("user", new Student());
		return "register-student";
	}

	@PostMapping("/register-student")
	public String registerStudent(Student user, Model model) {
		Student existingStudentByRollNo = studentRepository.findByRollNo(user.getRollNo());
		Student existingStudentByEmail = studentRepository.findByEmail(user.getEmail());
		if (existingStudentByRollNo != null) {
			// Roll number is already registered, display an error message
			model.addAttribute("errorMessage", "Roll number is already registered.");
			return "register-student";
		}
		if (existingStudentByEmail != null) {
			// Email is already registered, display an error message
			model.addAttribute("errorMessage", "Email is already registered.");
			return "register-student";
		} else {
			studentRepository.save(user);
			model.addAttribute("successMessage", "Registration successful! You can now log in.");
			return "register-student";
		}

	}

}
