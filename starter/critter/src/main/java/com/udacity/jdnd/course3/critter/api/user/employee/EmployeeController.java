package com.udacity.jdnd.course3.critter.api.user.employee;

import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.service.user.employee.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Customers.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping()
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        BeanUtils.copyProperties(employeeDTO, employee);

        employee = employeeService.save(employee);

        return convertToEmployeeDto(employee);
    }

    @PostMapping("/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        return convertToEmployeeDto(employee);
    }

    @PutMapping("/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private EmployeeDTO convertToEmployeeDto(Employee employee) {
        EmployeeDTO employeeDto = new EmployeeDTO();

        BeanUtils.copyProperties(employee, employeeDto);

        return employeeDto;
    }
}
