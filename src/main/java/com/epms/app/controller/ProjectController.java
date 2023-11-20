package com.epms.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.epms.app.entity.Project;
import com.epms.app.entity.Student;
import com.epms.app.service.ProjectService;
import com.epms.app.service.StudentService;

@Controller
public class ProjectController
{

	@Autowired
	private ProjectService projectService;

	@Autowired
	private StudentService studentService;

	@GetMapping("/add-project")
	public String showAddProject(Model model)
	{
		model.addAttribute("project_details", new Project());
		return "add-project";
	}

	@PostMapping("/add-project")
	public String addProject(Project project, Model model)
	{

		Student student = getCurrentStudent();

		if (student == null)
		{
			return "redirect:/login";
		}

		project.setStudent(student);

		projectService.saveProject(project);

		model.addAttribute("successMessage", "Project added successfully!");

		return "redirect:/student/dashboard";
	}

	private Student getCurrentStudent()
	{
		return null;
//		return studentService.getCurrentStudent();
	}

}
