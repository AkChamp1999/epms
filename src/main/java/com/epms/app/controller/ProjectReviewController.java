package com.epms.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.epms.app.entity.Project;
import com.epms.app.entity.ProjectReview;
import com.epms.app.entity.Teacher;
import com.epms.app.repository.ProjectRepository;
import com.epms.app.repository.ProjectReviewRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProjectReviewController
{
	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectReviewRepository projectReviewRepository;

	private long projectId = 0;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		// This will allow empty strings to be treated as null for integer fields
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
	}

	@GetMapping("/project-review")
	public String showProjectReview(@RequestParam Long projectId, Model model) throws IOException
	{
		@SuppressWarnings("deprecation")
		Project project = projectRepository.getById(projectId);

		this.projectId = projectId;

		model.addAttribute("project", project);

		return "project-review";
	}

	@PostMapping("/project-review")
	public String addProjectReview(HttpSession session, @Valid Project p, BindingResult result,
			@RequestParam String remarks, Model model, RedirectAttributes redirectAttributes) throws IOException
	{
		@SuppressWarnings("deprecation")
		Project project = projectRepository.getById(projectId);

		System.out.println("project=" + project.toString());

		model.addAttribute("project", project);
		System.out.println("Before");
		if (result.hasErrors())
		{
			result.getAllErrors().forEach(error ->
			{
				System.out.println("Error=" + error);
				model.addAttribute("errorMessage", error.getDefaultMessage());
			});
			System.out.println("Inside");
			return "project-review";
		}
		System.out.println("After");
		project.setStatus(p.getStatus());

		if (p.getStatus().equals("APPROVED"))
		{
			project.setMarks(p.getMarks());
		} else
		{
			project.setMarks(0);
		}

		Teacher teacher = (Teacher) session.getAttribute("teacher");
		ProjectReview projectReview = new ProjectReview();
		projectReview.setProject(project);
		projectReview.setTeacher(teacher);
		projectReview.setStatus(project.getStatus());
		projectReview.setRemarks(remarks);
		projectReview.setReviewDateTime(LocalDateTime.now());

		System.out.println("projectReview=" + projectReview);

		projectRepository.save(project);
		projectReviewRepository.save(projectReview);

		redirectAttributes.addFlashAttribute("reviewSuccess", "Review successfully submitted");

		StringBuilder url = new StringBuilder();
		url = url.append("?").append("batchYear=").append(project.getStudent().getBatchYear()).append("&")
				.append("course=").append(project.getStudent().getCourse()).append("&").append("division=")
				.append(project.getStudent().getDivision()).append("&").append("semester=")
				.append(project.getSemester());

		return "redirect:/get-projects" + url;
	}
}
