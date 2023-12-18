package com.epms.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epms.app.entity.Teacher;
import com.epms.app.repository.TeacherRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class TeacherController
{

	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping("/login-teacher")
	public String showTeacherLoginForm()
	{
		return "login-teacher";
	}

	@PostMapping("/login-teacher")
	public String teacherLogin(HttpSession session, @RequestParam String email, @RequestParam String password,
			Model model)
	{

		Teacher teacher = teacherRepository.findByEmail(email);

		if (teacher != null && teacher.getPassword().equals(password))
		{
			session.setAttribute("teacher", teacher);
			model.addAttribute("teacher", teacher);
			return "redirect:/teacher-dashboard";
		} else
		{
			System.out.println("Invalid email or password");
			model.addAttribute("loginFailed", "Invalid email or password");
			return "login-teacher";
		}
	}

	@GetMapping("/register-teacher")
	public String showTeacherRegistrationForm(Model model)
	{
		model.addAttribute("user", new Teacher());
		return "register-teacher";
	}

	@PostMapping("/register-teacher")
	public String registerTeacher(@Valid Teacher user, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors())
		{
			bindingResult.getAllErrors().forEach(error ->
			{
				model.addAttribute("errorMessage", error.getDefaultMessage());
			});

			return "register-teacher";
		}

		Teacher existingTeacherByEmail = teacherRepository.findByEmail(user.getEmail());

		if (existingTeacherByEmail != null)
		{
			// Email is already registered, display an error message
			model.addAttribute("errorMessage", "Email is already registered.");
			return "register-teacher";
		} else
		{
			teacherRepository.save(user);
			model.addAttribute("successMessage", "Registration successful! You can now log in.");
			return "register-teacher";
		}

	}

	@GetMapping("/teacher-dashboard")
	public String showDashboard(Model model)
	{
		return "teacher-dashboard";
	}

	@GetMapping("/logout-teacher")
	public String logout(HttpSession session, HttpServletResponse response)
	{
		session.invalidate();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		return "redirect:/login-teacher";
	}

}
