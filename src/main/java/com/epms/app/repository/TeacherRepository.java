package com.epms.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epms.app.entity.Teacher;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {

	Teacher findByUsername(String username);

}
