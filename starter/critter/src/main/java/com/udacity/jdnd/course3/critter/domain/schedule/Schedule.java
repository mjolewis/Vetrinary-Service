package com.udacity.jdnd.course3.critter.domain.schedule;

import com.sun.istack.NotNull;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.employee.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Pet> pets;

    @NotNull
    private LocalDate date;

    public Schedule() {
    }

    public Schedule(List<Employee> employees, List<Pet> pets, LocalDate date) {
        this.employees = employees;
        this.pets = pets;
        this.date = date;
    }

    public Schedule(Long id, List<Employee> employees, List<Pet> pets, LocalDate date) {
        this.id = id;
        this.employees = employees;
        this.pets = pets;
        this.date = date;
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

    public void setEmployees(List<Employee> employeeIds) {
        this.employees = employeeIds;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> petIds) {
        this.pets = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}