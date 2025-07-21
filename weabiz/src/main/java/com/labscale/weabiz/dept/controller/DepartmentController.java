package com.labscale.weabiz.dept.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.labscale.weabiz.dept.entities.Department;
import com.labscale.weabiz.dept.service.DepartmentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService deptService;

    @GetMapping
    public String listDepartments(Model model) {
        List<Department> list = deptService.findAll();
        model.addAttribute("departments", list);
        return "departmentList";
    }

    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        model.addAttribute("department", new Department());
        return "departmentInsert";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Department department) {
        deptService.save(department);
        return "redirect:/department";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        deptService.deleteById(id);
        return "redirect:/department";
    }
}
