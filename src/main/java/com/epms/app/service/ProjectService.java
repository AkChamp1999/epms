package com.epms.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.epms.app.entity.Project;
import com.epms.app.repository.ProjectRepository;

@Service
public class ProjectService
{
	@Value("${epms.server.directory.path}")
	private String epmsServerPath;

	@Autowired
	private ProjectRepository projectRepository;

	public MediaType getMediaType(String fileExtension)
	{
		switch (fileExtension.toLowerCase())
		{
		case "pdf":
			return MediaType.APPLICATION_PDF;
		case "doc":
		case "docx":
			return MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		default:
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

	public Project updateProjectData(Long roll_no, Long projectId, String projectName, String description,
			MultipartFile synopsisDocument, MultipartFile demoVideo, String semester) throws IOException
	{
		String basePath = epmsServerPath + "/" + roll_no + "/" + projectName;

		Optional<Project> optionalProject = projectRepository.findById(projectId);

		if (optionalProject.isPresent())
		{

			Project project = optionalProject.get();

			// Update fields if provided
			if (projectName != null)
			{
				String oldFolderPath = epmsServerPath + "/" + roll_no + "/" + project.getProjectName();

				project.setProjectName(projectName);

				String newFolderPath = epmsServerPath + "/" + roll_no + "/" + project.getProjectName();

				Path oldPath = Paths.get(oldFolderPath);
				Path newPath = Paths.get(newFolderPath);

				try
				{
					Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
					System.out.println("Folder renamed successfully.");
				} catch (IOException e)
				{
					System.err.println("Failed to rename the folder: " + e.getMessage());
				}
			}
			if (description != null)
			{
				project.setDescription(description);
			}
			if (synopsisDocument != null && !synopsisDocument.isEmpty())
			{
				System.out.println("Updating synopsisDocument");
				this.deleteFile(basePath, "synopsisDocument");
				this.updateSynopsisDocument(synopsisDocument, basePath + "/" + "synopsisDocument");
				project.setSynopsisDocument(synopsisDocument.getOriginalFilename());
			}
			if (demoVideo != null && !demoVideo.isEmpty())
			{
				System.out.println("Updating demoVideo");
				this.deleteFile(basePath, "demoVideo");
				this.updateDemoVideo(demoVideo, basePath + "/" + "demoVideo");
				project.setDemoVideo(demoVideo.getOriginalFilename());
			}
			if (semester != null)
			{
				project.setSemester(semester);
			}

			return project;
		}

		return null;
	}

	private void deleteFile(String basePath, String folderName) throws IOException
	{
		FileUtils.cleanDirectory(new File(basePath + "/" + folderName));
	}

	private void updateSynopsisDocument(MultipartFile synopsisDocument, String synopsisDirectoryPath) throws IOException
	{
		String synopsisDocumentFileName = StringUtils
				.cleanPath(Objects.requireNonNull(synopsisDocument.getOriginalFilename()));

		Path uploadSynopsisDocumentPath = Paths.get(synopsisDirectoryPath);
		try (InputStream inputStream = synopsisDocument.getInputStream())
		{
			Files.copy(inputStream, uploadSynopsisDocumentPath.resolve(synopsisDocumentFileName),
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

	private void updateDemoVideo(MultipartFile demoVideo, String demoVideoDirectoryPath) throws IOException
	{
		String demoVideoFileName = StringUtils.cleanPath(Objects.requireNonNull(demoVideo.getOriginalFilename()));

		Path uploadDemoVideoPath = Paths.get(demoVideoDirectoryPath);
		try (InputStream inputStream = demoVideo.getInputStream())
		{
			Files.copy(inputStream, uploadDemoVideoPath.resolve(demoVideoFileName),
					StandardCopyOption.REPLACE_EXISTING);
		}
	}

}
