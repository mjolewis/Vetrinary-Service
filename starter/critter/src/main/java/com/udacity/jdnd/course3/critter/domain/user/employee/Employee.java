package com.udacity.jdnd.course3.critter.domain.user.employee;

import com.sun.istack.NotNull;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Entity
@Check(constraints = "LENGTH(TRIM(name)) > 0")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nationalized
    @NotNull
    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class, fetch = FetchType.LAZY)
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection(targetClass = DayOfWeek.class, fetch = FetchType.LAZY)
    private Set<DayOfWeek> daysAvailable = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    public Employee() {
    }

    public Employee(String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, Schedule schedule) {
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
        this.schedule = schedule;
    }

    public Employee(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, Schedule schedule) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
