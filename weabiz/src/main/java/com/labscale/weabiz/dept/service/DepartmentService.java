package com.labscale.weabiz.dept.service;

import java.util.List;

import com.labscale.weabiz.dept.entities.Department;

public interface DepartmentService {
    List<Department> findAll();
    Department findById(int id);
    Department save(Department dept);
    void deleteById(int id);
}
