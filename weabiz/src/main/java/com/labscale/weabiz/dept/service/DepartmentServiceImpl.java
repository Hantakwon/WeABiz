package com.labscale.weabiz.dept.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.labscale.weabiz.dept.entities.Department;
import com.labscale.weabiz.dept.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;
	 
	@Override
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	@Override
	public Department findById(int id) {
		return departmentRepository.findById(id).get();
	}

	@Override
	public Department save(Department dept) {
		return departmentRepository.save(dept);
	}

	@Override
	public void deleteById(int id) {
		departmentRepository.deleteById(id);
	}

}
