package com.epms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epms.app.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
	Teacher findByEmail(String email);
}
