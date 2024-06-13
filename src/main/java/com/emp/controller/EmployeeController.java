package com.emp.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.entity.Education;
import com.emp.entity.Employee;
import com.emp.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/home")
	public String homePage() {
		return "Home";
	}
	@GetMapping("/dash")
	public String dash() {
		return "dashboard";
	}
	

	@GetMapping("/create")
	public String showCreateForm(Model model) {
		model.addAttribute("shivaraj", new Employee());
		return "Emp";
	}

	@PostMapping("/create")
	public String createEmployee(Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees/active";
	}

	@GetMapping("/active")
	public String viewEmployeesPage(Model model) {
		List<Employee> employees = employeeService.getAllActiveEmployees();
		model.addAttribute("employees", employees);
		return "All";
	}

	@GetMapping("/{id}/update")
	public String showUpdateForm(@PathVariable Long id, Model model) {
		employeeService.getEmployeeById(id).ifPresent(employee -> model.addAttribute("employee", employee));
		return "update";
	}

	@PostMapping("/{id}/update")
	public String updateEmployee(@PathVariable Long id, Employee updatedEmployee) {
		updatedEmployee.setId(id);
		employeeService.saveEmployee(updatedEmployee);
		return "redirect:/employees/active";
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		return employeeService.getEmployeeById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee1(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
		return employeeService.getEmployeeById(id).map(employee -> {
			updatedEmployee.setId(id);
			return ResponseEntity.ok(employeeService.saveEmployee(updatedEmployee));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployee(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{id}/deactivate")
	public ResponseEntity<Void> deactivateEmployee(@PathVariable Long id) {
		employeeService.deactivateEmployee(id);
		return ResponseEntity.noContent().build();
	}

}
