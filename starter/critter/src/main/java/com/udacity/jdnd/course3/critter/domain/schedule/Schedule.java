package com.udacity.jdnd.course3.critter.domain.schedule;

import com.sun.istack.NotNull;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;
import com.udacity.jdnd.course3.critter.domain.user.employee.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_employee",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_id")}
    )
    private List<Pet> pets = new ArrayList<>();

    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> activities = new HashSet<>();

    @NotNull
    private LocalDate date;

    public Schedule() {
    }

    public Schedule(List<Employee> employees, List<Pet> pets, Set<EmployeeSkill> activities, LocalDate date) {
        this.employees = employees;
        this.pets = pets;
        this.activities = activities;
        this.date = date;
    }

    public Schedule(Long id, List<Employee> employees, List<Pet> pets, Set<EmployeeSkill> activities, LocalDate date) {
        this.id = id;
        this.employees = employees;
        this.pets = pets;
        this.activities = activities;
        this.date = date;
    }

    /**
     * Create a list of pets if one doesn't exist. Then add a pet to the schedule.
     *
     * @param pet A pet object that gets added to the schedule.
     */
    public void addPet(Pet pet) {
        if (pets == null) {
            pets = new ArrayList<>();
        }

        pets.add(pet);
    }

    /**
     * Create a list of employees if one doesn't exist. Then add an employee to the schedule.
     *
     * @param employee A employee object that gets added to the schedule.
     */
    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }

        employees.add(employee);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> employeeSkills) {
        this.activities = employeeSkills;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}