package com.epms.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.epms.app.service.ProjectService;

@Controller
public class DownloadController
{

	@Autowired
	private ProjectService projectService;

	@GetMapping("/download-synopsis/{rollNo}/{projectName}/{synopsisDocument}")
	public ResponseEntity<byte[]> downloadSynopsis(@PathVariable String rollNo, @PathVariable String projectName,
			@PathVariable String synopsisDocument) throws IOException
	{
		String synopsisFilePath = "./src/main/resources/epms_server/" + rollNo + "/" + projectName
				+ "/synopsisDocument/" + synopsisDocument;
		Path synopsisPath = Paths.get(synopsisFilePath);

		// Read the file content into a byte array
		byte[] synopsisBytes = Files.readAllBytes(synopsisPath);

		HttpHeaders headers = new HttpHeaders();

		// Determine the media type based on the file extension
		String fileExtension = StringUtils.getFilenameExtension(synopsisPath.toString());
		MediaType mediaType = projectService.getMediaType(fileExtension);

		headers.setContentType(mediaType);
		headers.setContentDispositionFormData("attachment",
				StringUtils.stripFilenameExtension(synopsisDocument) + "." + fileExtension);

		return new ResponseEntity<>(synopsisBytes, headers, HttpStatus.OK);
	}

	@GetMapping("/download-demo-video/{rollNo}/{projectName}/{demoVideo}")
	public ResponseEntity<byte[]> downloadDemoVideo(@PathVariable String rollNo, @PathVariable String projectName,
			@PathVariable String demoVideo) throws IOException
	{
		String demoVideoFilePath = "./src/main/resources/epms_server/" + rollNo + "/" + projectName + "/demoVideo/"
				+ demoVideo;
		Path demoVideoPath = Paths.get(demoVideoFilePath);

		// Read the file content into a byte array
		byte[] demoVideoBytes = Files.readAllBytes(demoVideoPath);

		HttpHeaders headers = new HttpHeaders();

		// Determine the media type based on the file extension
		String fileExtension = StringUtils.getFilenameExtension(demoVideoPath.toString());
		MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

		headers.setContentType(mediaType);
		headers.setContentDispositionFormData("attachment",
				StringUtils.stripFilenameExtension(demoVideo) + "." + fileExtension);

		return new ResponseEntity<>(demoVideoBytes, headers, HttpStatus.OK);
	}

}
