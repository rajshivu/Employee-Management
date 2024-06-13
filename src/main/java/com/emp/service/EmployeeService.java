package com.emp.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.entity.Address;
import com.emp.entity.Education;
import com.emp.entity.Employee;
import com.emp.exception.InvalidDataException;
import com.emp.exception.ResourceNotFoundException;
import com.emp.repo.AddressRepository;
import com.emp.repo.EducationRepo;
import com.emp.repo.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EducationRepo educationDetailRepository;

	@Transactional
	public Employee saveEmployee(Employee employee) {
		try {
			if (employee.getPermanentAddress() != null && employee.getPermanentAddress().getId() == null) {
				Address permanentAddress = addressRepository.save(employee.getPermanentAddress());
				System.out.println("Saved permanent address");
				employee.setPermanentAddress(permanentAddress);
			}

			if (employee.getPresentAddress() != null && employee.getPresentAddress().getId() == null) {
				Address presentAddress = addressRepository.save(employee.getPresentAddress());
				System.out.println("Saved present address");
				employee.setPresentAddress(presentAddress);
			}

			if (employee.getEducation1() != null) {
				// System.out.println("Education object is not null");
				if (employee.getEducation1().getId() == null) {
					// System.out.println("Education ID is null. Saving Education: " +
					// employee.getEducation1());
					Education edu = educationDetailRepository.save(employee.getEducation1());
					// System.out.println("Saved Education: " + edu);
					employee.setEducation1(edu);
				} else {
					System.out.println("Education ID is not null: " + employee.getEducation1().getId());
				}
			} else {
				System.out.println("Education object is null");
			}

			return employeeRepository.save(employee);
		} catch (Exception e) {
			throw new InvalidDataException("Failed to save employee data: " + e.getMessage());
		}
	}

	public List<Employee> getAllActiveEmployees() {
		try {
			return employeeRepository.findByActiveStatus(true);
		} catch (Exception e) {
			throw new InvalidDataException("Failed to fetch active employees: " + e.getMessage());
		}
	}

	public Optional<Employee> getEmployeeById(Long id) {
		try {
			return Optional.of(employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id)));
		} catch (Exception e) {
			throw new InvalidDataException("Failed to fetch employee by id: " + e.getMessage());
		}
	}

	public void deleteEmployee(Long id) {
		try {
			employeeRepository.findById(id).ifPresent(employee -> {
				employee.setActiveStatus(false);
				employeeRepository.save(employee);
			});
		} catch (Exception e) {
			throw new InvalidDataException("Failed to delete employee: " + e.getMessage());
		}
	}

	@Transactional
	public void deactivateEmployee(Long id) {
		try {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

			employee.setActiveStatus(false);
			employeeRepository.save(employee);
		} catch (NoSuchElementException e) {
			throw new ResourceNotFoundException("Employee not found with id: " + id);
		} catch (Exception e) {
			throw new InvalidDataException("Failed to deactivate employee: " + e.getMessage());
		}
	}
}
