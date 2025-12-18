package com.practice.employee_management_system.controller;

import com.practice.employee_management_system.model.Employee;
import com.practice.employee_management_system.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    // ----------------------------------------------------
    // 1. READ ALL (GET)
    // GET http://localhost:8080/api/v1/employees
    // ----------------------------------------------------

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
@GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    // ----------------------------------------------------
    // 3. CREATE (POST)
    // POST http://localhost:8080/api/v1/employees
    // ----------------------------------------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Sets the HTTP status code to 201 CREATED for a successful resource creation
    public Employee createEmployee(@RequestBody Employee employee) {
        // @RequestBody maps the incoming JSON body to the Employee Java object
        return employeeService.saveEmployee(employee);
    }

    // ----------------------------------------------------
    // 4. UPDATE (PUT)
    // PUT http://localhost:8080/api/v1/employees/{id}
    // ----------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id, // Extracts the ID from the URL path
            @RequestBody Employee employeeDetails) { // Extracts the update data from the request body

        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Apply updates from the request body
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        // Save and return the updated entity with 200 OK
        Employee updatedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // ----------------------------------------------------
    // 5. DELETE (DELETE)
    // DELETE http://localhost:8080/api/v1/employees/{id}
    // ----------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        // Returning 204 No Content is a common REST practice for a successful deletion
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
