package com.epms.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Status
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statusId;

	private String statusName;
}
