package com.labscale.weabiz.dept.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.labscale.weabiz.dept.entities.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
