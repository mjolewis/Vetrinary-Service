package com.udacity.jdnd.course3.critter.service.user.employee;

import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.user.employee.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Either creates or updates an employee, based on prior existence of employee
     * @param employee A employee object, which can be either new or existing
     * @return The new or updated employee stored in the repository
     */
    public Employee save(Employee employee) {
        if (employee.getId() != null) {
            return employeeRepository.findById(employee.getId())
                    .map(employeeToBeUpdated -> {
                        employeeToBeUpdated.setName(employee.getName());
                        employeeToBeUpdated.setDaysAvailable(employee.getDaysAvailable());
                        employeeToBeUpdated.setSkills(employee.getSkills());
                        employeeToBeUpdated.setSchedule(employee.getSchedule());
                        return employeeToBeUpdated;
                    }).orElseThrow(EmployeeNotFoundException::new);
        }

        return employeeRepository.save(employee);
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

//    public void setAvailability(Set<DayOfWeek> daysAvailable, long id) {
//        employeeRepository.
//    }
}
