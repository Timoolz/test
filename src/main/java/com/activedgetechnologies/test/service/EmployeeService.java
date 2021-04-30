package com.activedgetechnologies.test.service;

import com.activedgetechnologies.test.dao.EmployeeRepository;
import com.activedgetechnologies.test.exception.EntityNotFoundException;
import com.activedgetechnologies.test.model.Employee;
import com.activedgetechnologies.test.model.EmployeeResponse;
import com.activedgetechnologies.test.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee createOrUpdateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employeeRepository.delete(employee);
    }

    public Employee getEmployee(String employeeId) throws EntityNotFoundException {
        return employeeRepository.findById(employeeId).orElseThrow(EntityNotFoundException::new);
    }

    public EmployeeResponse getAllEmployee() {

        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return new EmployeeResponse(employees);

    }
}
