package com.epms.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epms.app.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>
{
	List<Project> findByStudentRollNo(long roll_no);

	List<Project> findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_Division(String course,
			String batchYear, String semester, String division);

	List<Project> findByStudent_CourseAndStudent_BatchYearAndSemesterAndStatus(String course, String batchYear,
			String semester, String status);

	List<Project> findByStudent_CourseAndStudent_BatchYearAndSemester(String course, String batchYear, String semester);

	List<Project> findByStudent_CourseAndStudent_BatchYearAndSemesterAndStudent_DivisionAndStatus(String course,
			String batchYear, String semester, String division, String status);
}
