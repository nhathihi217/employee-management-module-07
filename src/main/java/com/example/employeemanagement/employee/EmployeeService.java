package com.example.employeemanagement.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll(String name) {
        if (name != null && !name.isBlank()) {
            log.debug("Searching employees by name query='{}'", name.trim());
            return employeeRepository.findByNameContainingIgnoreCase(name.trim());
        }
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee create(EmployeeRequest request) {
        Employee saved = employeeRepository.save(new Employee(request.name().trim(), request.email().trim()));
        log.info("Created employee id={} email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = findById(id);
        employee.setName(request.name().trim());
        employee.setEmail(request.email().trim());
        Employee saved = employeeRepository.save(employee);
        log.info("Updated employee id={} email={}", saved.getId(), saved.getEmail());
        return saved;
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
        log.info("Deleted employee id={} email={}", id, employee.getEmail());
    }
}
