package com.udacity.jdnd.course3.critter.domain.schedule;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection(targetClass = Long.class, fetch = FetchType.LAZY)
    private List<Long> employeeIds = new ArrayList<>();

    @ElementCollection(targetClass = Long.class, fetch = FetchType.LAZY)
    private List<Long> customerIds = new ArrayList<>();

    @ElementCollection(targetClass = Long.class, fetch = FetchType.LAZY)
    private List<Long> petIds = new ArrayList<>();

    @NotNull
    private LocalDate date;

    public Schedule() {
    }

    public Schedule(List<Long> employeeIds, List<Long> customerIds, List<Long> petIds, LocalDate date) {
        this.employeeIds = employeeIds;
        this.customerIds = customerIds;
        this.petIds = petIds;
        this.date = date;
    }

    public Schedule(Long id, List<Long> employeeIds, List<Long> customerIds, List<Long> petIds, LocalDate date) {
        this.id = id;
        this.employeeIds = employeeIds;
        this.customerIds = customerIds;
        this.petIds = petIds;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Long> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}