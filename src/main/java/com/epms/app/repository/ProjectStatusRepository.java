package com.epms.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epms.app.entity.Status;

public interface ProjectStatusRepository extends JpaRepository<Status, Integer>
{

}
