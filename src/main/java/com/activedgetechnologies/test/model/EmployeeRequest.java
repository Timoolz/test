package com.activedgetechnologies.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Data
public class EmployeeRequest {

    @JsonProperty(value = "employee_id")
    @NotBlank(groups = {EmployeeRequest.Update.class, EmployeeRequest.Delete.class})
    private String employeeId;


    @Column(nullable = false)
    @NotBlank
    @JsonProperty(value = "first_name")
    private String firstName;


    @Column(nullable = false)
    @NotBlank
    @JsonProperty(value = "last_name")
    private String lastName;


    @Column(nullable = false)
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull
    @Min(1)
    private Integer age;


    public interface Update extends Default {
    }

    public interface Delete {
    }

    public Employee buildEmployee(boolean create) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(this, employee);
        if (create)
            employee.setEmployeeId(null);
        return employee;
    }
}
