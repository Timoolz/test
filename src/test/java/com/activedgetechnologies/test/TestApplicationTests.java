package com.activedgetechnologies.test;

import com.activedgetechnologies.test.api.EmployeeController;
import com.activedgetechnologies.test.model.Employee;
import com.activedgetechnologies.test.model.EmployeeRequest;
import com.activedgetechnologies.test.service.EmployeeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestApplicationTests {



	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeController employeeController;

	EmployeeRequest employeeRequest = new EmployeeRequest();



	@Test
	@Order(1)
	void contextLoads() {
	}



	@Test
	@Order(2)
	public void createEmployeeTest() throws Exception {

		String firstName = RandomStringUtils.randomAlphabetic(10);
		String lastName = RandomStringUtils.randomAlphabetic(10);
		Integer age = Integer.valueOf(RandomStringUtils.randomNumeric(2));

		employeeRequest.setFirstName(firstName);
		employeeRequest.setLastName(lastName);
		employeeRequest.setAge(age);
		Employee employee = employeeController.createEmployee(employeeRequest);

		assertNotNull(employee);
		assertEquals(age, employee.getAge());
		assertEquals(firstName, employee.getFirstName());
		assertEquals(lastName, employee.getLastName());

	}

	@Test
	@Order(3)
	public void updateEmployeeTest() throws Exception {
		employeeRequest.setEmployeeId("E00001");

		Employee employee = employeeController.getEmployee(employeeRequest.getEmployeeId());

		String firstName = RandomStringUtils.randomAlphabetic(10);
		String lastName = RandomStringUtils.randomAlphabetic(10);
		Integer age = Integer.valueOf(RandomStringUtils.randomNumeric(2));

		employeeRequest.setFirstName(firstName);
		employeeRequest.setLastName(lastName);
		employeeRequest.setAge(age);

		employeeController.updateEmployee(employeeRequest);
		Employee updatedEmployee = employeeController.getEmployee(employeeRequest.getEmployeeId());

		assertNotNull(updatedEmployee);
		assertEquals(age, updatedEmployee.getAge());
		assertEquals(firstName, updatedEmployee.getFirstName());
		assertEquals(lastName, updatedEmployee.getLastName());

	}

	@Test
	@Order(4)
	public void getAllEmployeesTest() throws Exception {
		createEmployeeTest();

		List<Employee> employeesList = employeeController.getAllEmployee().getEmployees();
		assertEquals(employeesList.size(), 2);
		assertEquals(employeesList.get(0).getEmployeeId(), "E00001");
		assertEquals(employeesList.get(1).getEmployeeId(), "E00002");


	}



}
