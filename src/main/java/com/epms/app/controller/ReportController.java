package com.epms.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epms.app.entity.Project;
import com.epms.app.entity.ProjectReview;
import com.epms.app.entity.ReportData;
import com.epms.app.repository.ProjectRepository;
import com.epms.app.repository.ProjectReviewRepository;

@Controller
public class ReportController
{

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private ProjectReviewRepository projectReviewRepository;

	@GetMapping("/generate-report")
	public String logout()
	{
		return "generate-report";
	}

	@GetMapping("/download-report")
	public ResponseEntity<byte[]> downloadReport(@RequestParam String course, @RequestParam String semester,
			@RequestParam String batchYear, @RequestParam(required = false) String division,
			@RequestParam(required = false) String status) throws IOException
	{
		System.out.println("Downloading report");

		List<Project> projects;
		if (division == null && status == null)
		{
			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemester(course, batchYear,
					semester);
		} else if (division == null)
		{
			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStatus(course, batchYear,
					semester, status);
		} else if (status == null)
		{
			projects = projectRepository.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_Division(course,
					batchYear, semester, division);
		} else
		{
			projects = projectRepository
					.findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_DivisionAndStatus(course, batchYear,
							semester, division, status);
		}

		if (projects.isEmpty())
		{
			// If no projects are found, return a response with the SweetAlert script
			String sweetAlertScript = "<script src=\"https://cdn.jsdelivr.net/npm/sweetalert2@11\"></script>"
					+ "<script>" + "document.addEventListener('DOMContentLoaded', function() {" + "   Swal.fire({"
					+ "       icon: 'error'," + "       title: 'No projects found',"
					+ "       text: 'There are no projects matching the selected criteria.',"
					+ "   }).then(function() {" + "       window.location.href = '/generate-report';" + "   });" + "});"
					+ "</script>";

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.TEXT_HTML);

			return new ResponseEntity<>(sweetAlertScript.getBytes(), headers, HttpStatus.OK);
		}

		List<ReportData> reportDataList = new ArrayList<>();

		for (Project p : projects)
		{
			ReportData reportData = new ReportData();

			reportData.setStudentRollNo(p.getStudent().getRollNo());
			reportData.setStudentFullName(p.getStudent().getFullName());
			reportData.setProjectName(p.getProjectName());
			reportData.setCourse(p.getStudent().getCourse());
			reportData.setDivision(p.getStudent().getDivision());
			reportData.setStatus(p.getStatus());
			if ("APPROVED".equals(p.getStatus()))
			{
				System.out.println("marks=" + p.getMarks());
				reportData.setMarks(Integer.toString(p.getMarks()));
			} else
			{
				reportData.setMarks("-");
			}

			String pattern = "MM/dd/yyyy";
			DateFormat df = new SimpleDateFormat(pattern);
			String submissionDateAsString = df.format(p.getSubmissionDate());

			reportData.setSubmissionDate(submissionDateAsString);

			if (!"PENDING_FOR_REVIEW".equals(p.getStatus()))
			{
				System.out.println("Project Id=" + p.getProjectId());

				ProjectReview projectReview = projectReviewRepository
						.findTopByProject_ProjectIdOrderByReviewDateTimeDesc(p.getProjectId());

				if (projectReview != null)
				{
					reportData.setReviewer(projectReview.getTeacher().getFullName());

					LocalDateTime localDateTime = projectReview.getReviewDateTime();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					reportData.setReviewDate(localDateTime.format(formatter));
				} else
				{
					reportData.setReviewer("-");
					reportData.setReviewDate("-");
				}
			} else
			{
				reportData.setReviewer("-");
				reportData.setReviewDate("-");
			}
			reportDataList.add(reportData);
		}

		Workbook workbook = createExcelWorkbook(reportDataList);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName = "epms_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
				+ ".xlsx";
		headers.setContentDispositionFormData("attachment", fileName);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);
		byte[] bytes = outputStream.toByteArray();

		return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
	}

	private Workbook createExcelWorkbook(List<ReportData> reportData)
	{
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Report");
		System.out.println("Size=" + reportData.size());

		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Student Roll No");
		headerRow.createCell(1).setCellValue("Student Name");
		headerRow.createCell(2).setCellValue("Project Name");
		headerRow.createCell(3).setCellValue("Course");
		headerRow.createCell(4).setCellValue("Division");
		headerRow.createCell(5).setCellValue("Status");
		headerRow.createCell(6).setCellValue("Marks");
		headerRow.createCell(7).setCellValue("Submission Date");
		headerRow.createCell(8).setCellValue("Reviewer");
		headerRow.createCell(9).setCellValue("Review Date");

		int rowNum = 1;
		for (ReportData data : reportData)
		{
			System.out.println("ReportData=" + data.toString());
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(data.getStudentRollNo());
			row.createCell(1).setCellValue(data.getStudentFullName());
			row.createCell(2).setCellValue(data.getProjectName());
			row.createCell(3).setCellValue(data.getCourse());
			row.createCell(4).setCellValue(data.getDivision());
			row.createCell(5).setCellValue(data.getStatus());
			row.createCell(6).setCellValue(data.getMarks());
			row.createCell(7).setCellValue(data.getSubmissionDate());
			row.createCell(8).setCellValue(data.getReviewer());
			row.createCell(9).setCellValue(data.getReviewDate());
		}

		return workbook;
	}
}
