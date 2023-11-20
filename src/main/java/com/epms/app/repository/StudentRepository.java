package com.epms.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epms.app.entity.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

	Student findByEmail(String email);

	Student findByRollNo(Long rollNo);

}
