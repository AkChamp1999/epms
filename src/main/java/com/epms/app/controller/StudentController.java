package com.epms.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epms.app.entity.Project;
import com.epms.app.entity.Student;
import com.epms.app.repository.ProjectRepository;
import com.epms.app.repository.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class StudentController
{

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@GetMapping("/login-student")
	public String showStudentLoginForm()
	{
		return "login-student";
	}

	@PostMapping("/login-student")
	public String studentLogin(HttpSession session, @RequestParam String email, @RequestParam String password,
			Model model)
	{

		Student student = studentRepository.findByEmail(email);

		if (student != null && student.getPassword().equals(password))
		{
			session.setAttribute("student", student);
			model.addAttribute("student", student);
			return "redirect:/student-dashboard";
		} else
		{
			System.out.println("Invalid email or password");
			model.addAttribute("loginFailed", "Invalid email or password");
			return "login-student";
		}
	}

	@GetMapping("/register-student")
	public String showStudentRegistrationForm(Model model)
	{
		model.addAttribute("user", new Student());
		return "register-student";
	}

	@PostMapping("/register-student")
	public String registerStudent(@Valid Student user, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			bindingResult.getAllErrors().forEach(error ->
			{
				model.addAttribute("errorMessage", error.getDefaultMessage());
			});

			return "register-student";
		}

		Student existingStudentByRollNo = studentRepository.findByRollNo(user.getRollNo());
		Student existingStudentByEmail = studentRepository.findByEmail(user.getEmail());
		if (existingStudentByRollNo != null)
		{
			// Roll number is already registered, display an error message
			model.addAttribute("errorMessage", "Roll number is already registered.");
			return "register-student";
		}
		if (existingStudentByEmail != null)
		{
			// Email is already registered, display an error message
			model.addAttribute("errorMessage", "Email is already registered.");
			return "register-student";
		} else
		{
			studentRepository.save(user);
			model.addAttribute("successMessage", "Registration successful! You can now log in.");
			return "register-student";
		}

	}

	@GetMapping("/student-dashboard")
	public String showDashboard(HttpSession session, Model model)
	{
		Student student = (Student) session.getAttribute("student");

		if (student == null)
		{
			return "login-student";
		}

		List<Project> projects = projectRepository.findByStudentRollNo(student.getRollNo());

		System.out.println("Project Info=" + Arrays.asList(projects));

		model.addAttribute("projects", projects);

		return "student-dashboard";
	}

	@RequestMapping("/logout-student")
	public String logout(HttpSession session, HttpServletResponse response)
	{
		session.invalidate();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		return "redirect:/login-student";
	}

}
