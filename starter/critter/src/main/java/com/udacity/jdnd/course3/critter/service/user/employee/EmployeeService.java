package com.udacity.jdnd.course3.critter.service.user.employee;

import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeSkill;
import com.udacity.jdnd.course3.critter.service.user.employee.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
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
                        employeeToBeUpdated.setSchedules(employee.getSchedules());
                        return employeeToBeUpdated;
                    }).orElseThrow(EmployeeNotFoundException::new);
        }

        return employeeRepository.save(employee);
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow(EmployeeNotFoundException::new);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long id) {
        employeeRepository.findById(id)
                .ifPresent(employee ->
                        employee.setDaysAvailable(daysAvailable));
    }

    public List<Employee> findEmployeesForService(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills) {
        List<Employee> employees = employeeRepository.findEmployeesByDaysAvailableAndSkillsIn(dayOfWeek, skills);

        // Return a list of employees who are available on the date requested only if they also have ALL skills
        List<Employee> availableEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSkills().containsAll(skills)) {
                availableEmployees.add(employee);
            }
        }

        return availableEmployees;
    }
}
