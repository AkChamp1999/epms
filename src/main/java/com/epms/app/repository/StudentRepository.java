package com.epms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epms.app.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{

	Student findByEmail(String email);

	Student findByRollNo(Long rollNo);

}
