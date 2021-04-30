package com.activedgetechnologies.test.api;

import com.activedgetechnologies.test.exception.EntityNotFoundException;
import com.activedgetechnologies.test.model.*;
import com.activedgetechnologies.test.model.user.UserLogin;
import com.activedgetechnologies.test.model.user.UserResult;
import com.activedgetechnologies.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/employee")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee createEmployee(@Validated @RequestBody EmployeeRequest request) throws Exception {
        return employeeService.createOrUpdateEmployee(request.buildEmployee(true));
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateEmployee(@Validated(value = EmployeeRequest.Update.class) @RequestBody EmployeeRequest request) throws Exception {
        employeeService.getEmployee(request.getEmployeeId());
        employeeService.createOrUpdateEmployee(request.buildEmployee(false));
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEmployee(@Validated(value = EmployeeRequest.Delete.class) @RequestBody EmployeeRequest request) throws Exception {
        employeeService.getEmployee(request.getEmployeeId());
        employeeService.delete(request.buildEmployee(false));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(@RequestParam String employee_id) throws EntityNotFoundException {

        return employeeService.getEmployee(employee_id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeResponse getAllEmployee() {

        return employeeService.getAllEmployee();
    }

}
