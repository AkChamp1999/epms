package com.epms.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.epms.app.entity.Project;
import com.epms.app.entity.ProjectReview;
import com.epms.app.entity.Student;
import com.epms.app.entity.Teacher;
import com.epms.app.repository.ProjectRepository;
import com.epms.app.repository.ProjectReviewRepository;
import com.epms.app.repository.TeacherRepository;
import com.epms.app.service.ProjectService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
@Controller
public class ProjectController
{

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ProjectReviewRepository projectReviewRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@GetMapping("/add-project")
	public String showAddProject(Model model)
	{
		model.addAttribute("project_details", new Project());
		return "add-project";
	}

	@PostMapping("/add-project")
	public String addProject(HttpSession session, @RequestParam("projectName") String projectName,
			@RequestParam("description") String description,
			@RequestParam("synopsisDocument") MultipartFile synopsisDocument,
			@RequestParam("demoVideo") MultipartFile demoVideo, @RequestParam("semester") String semester, Model model)
			throws IOException, ServletException
	{

		Student student = (Student) session.getAttribute("student");

		if (student == null)
		{
			return "login-student";
		}

		if (description.length() > 1000)
		{
			model.addAttribute("projectSubmissionFailed", "Description should be less than 1000 characters");
			return "add-project";
		}

		Project project = new Project();

		project.setStudent(student);

		projectName = projectName.trim();

		try
		{
			// Create a base directory path
			String baseDirectory = "./src/main/resources/epms_server/";

			// Create a directory path for synopsis documents
			String synopsisDirectoryPath = baseDirectory + project.getStudent().getRollNo() + "/" + projectName
					+ "/synopsisDocument/";
			createDirectory(synopsisDirectoryPath);

			// Create a directory path for demo videos
			String demoVideoDirectoryPath = baseDirectory + project.getStudent().getRollNo() + "/" + projectName
					+ "/demoVideo/";
			createDirectory(demoVideoDirectoryPath);

			System.out.println("Folders created successfully.");

			String synopsisDocumentFileName = StringUtils
					.cleanPath(Objects.requireNonNull(synopsisDocument.getOriginalFilename()));

			Path uploadSynopsisDocumentPath = Paths.get(synopsisDirectoryPath);
			try (InputStream inputStream = synopsisDocument.getInputStream())
			{
				Files.copy(inputStream, uploadSynopsisDocumentPath.resolve(synopsisDocumentFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}

			String demoVideoFileName = StringUtils.cleanPath(Objects.requireNonNull(demoVideo.getOriginalFilename()));

			Path uploadDemoVideoPath = Paths.get(demoVideoDirectoryPath);
			try (InputStream inputStream = demoVideo.getInputStream())
			{
				Files.copy(inputStream, uploadDemoVideoPath.resolve(demoVideoFileName),
						StandardCopyOption.REPLACE_EXISTING);
			}

			project.setProjectName(projectName);
			project.setDescription(description);
			project.setSynopsisDocument(synopsisDocument.getOriginalFilename());
			project.setDemoVideo(demoVideo.getOriginalFilename());
			project.setSemester(semester);
			project.setSubmissionDate(new Date());
			project.setStatus("PENDING_FOR_REVIEW");

			List<Project> projects = projectRepository.findByStudentRollNo(student.getRollNo());

			for (Project p : projects)
			{
				if (p.getSemester().equalsIgnoreCase(project.getSemester()))
				{
					model.addAttribute("projectSubmissionFailed",
							"Project is already submitted for " + project.getSemester());
					return "add-project";
				}
			}

			projectRepository.save(project);
			model.addAttribute("projects", projects);
			model.addAttribute("projectSubmissionSuccess", "Project added successfully!");

		} catch (IOException e)
		{
			// Handle the exception
			e.printStackTrace();
		}

		return "add-project";

	}

	@GetMapping("/view-project")
	public String showViewProject(@RequestParam Long projectId, Model model) throws IOException
	{
		@SuppressWarnings("deprecation")
		Project project = projectRepository.getById(projectId);

		ProjectReview latestReview = projectReviewRepository
				.findTopByProject_ProjectIdOrderByReviewDateTimeDesc(projectId);

		if (latestReview != null)
		{
			@SuppressWarnings("deprecation")
			Teacher teacher = teacherRepository.getById(latestReview.getTeacher().getTeacherId());
			model.addAttribute("teacher", teacher);
		}

		model.addAttribute("latestReview", latestReview);
		model.addAttribute("project", project);

		return "view-project";
	}

	@GetMapping("/update-project")
	public String showUpdateProject(@RequestParam Long projectId, Model model)
	{
		@SuppressWarnings("deprecation")
		Project project = projectRepository.getById(projectId);
		System.out.println("project from get update=" + project.toString());
		model.addAttribute("project", project);
		return "update-project";
	}

	@PostMapping("/update-project/{projectId}")
	public String updateProject(HttpSession session, @PathVariable Long projectId,
			@RequestParam(value = "projectName", required = false) String projectName,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "synopsisDocument", required = false) MultipartFile synopsisDocument,
			@RequestParam(value = "demoVideo", required = false) MultipartFile demoVideo,
			@RequestParam(value = "semester", required = false) String semester, Model model)
			throws IOException, ServletException
	{
		System.out.println("projectId=" + projectId);
		Student student = (Student) session.getAttribute("student");

		if (student == null)
		{
			return "login-student";
		}

		if (description != null && description.length() > 1000)
		{
			System.out.println("came in ");
			model.addAttribute("projectUpdateFailed", "Description should be less than 1000 characters");
			return "redirect:/update-project?projectId=" + projectId;
		}

		Project project = projectService.updateProjectData(student.getRollNo(), projectId, projectName, description,
				synopsisDocument, demoVideo, semester);

		if (project != null)
		{
			model.addAttribute("projectUpdateSuccess", "Project updated successfully");
			projectRepository.save(project);
		} else
		{
			model.addAttribute("projectUpdateFailed", "Update Failed");
		}

		return "redirect:/view-project?projectId=" + projectId;

	}

	@GetMapping("/get-projects")
	public String getProjects(Project project, Student student, Model model)
	{
		List<Project> projects = null;

		if (student.getDivision() == null && project.getStatus() == null)
		{
			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemester(student.getCourse(),
					student.getBatchYear(), project.getSemester());
		} else if (student.getDivision() == null)
		{
			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStatus(
					student.getCourse(), student.getBatchYear(), project.getSemester(), project.getStatus());
		} else if (project.getStatus() == null)
		{
			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_Division(
					student.getCourse(), student.getBatchYear(), project.getSemester(), student.getDivision());
		} else
		{
			System.out.println("else");
			projects = projectRepository
					.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_DivisionAndStatus(
							student.getCourse(), student.getBatchYear(), project.getSemester(), student.getDivision(),
							project.getStatus());
		}

		if (projects.size() == 0)
		{
			model.addAttribute("errorMessage", "Projects not found");
			return "teacher-dashboard";
		}

		System.out.println(Arrays.asList(projects));

		model.addAttribute("projects", projects);

		return "teacher-dashboard";
	}

//	@GetMapping("/get-existed-projects")
//	public String getExistedProjects(Model model)
//	{
//		List<Project> projects = null;
//
//		if (division == null)
//		{
//			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemester(course, batchYear,
//					semster);
//		} else
//		{
//			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_Division(course,
//					batchYear, semster, division);
//		}
//
//		if (projects.size() == 0)
//		{
//			model.addAttribute("errorMessage", "Projects not found");
//			return "teacher-dashboard";
//		}
//
//		System.out.println(Arrays.asList(projects));
//
//		model.addAttribute("projects", projects);
//
//		return "teacher-dashboard";
//	}

	private static void createDirectory(String directoryPath) throws IOException
	{
		// Convert the directory path to a Path object
		Path directory = Paths.get(directoryPath);

		// Create the directory if it doesn't exist
		if (!Files.exists(directory))
		{
			Files.createDirectories(directory);
			System.out.println("Folder created successfully: " + directory);
		} else
		{
			System.out.println("Folder already exists: " + directory);
		}
	}

}
