package com.udacity.jdnd.course3.critter.domain.pet;

import com.sun.istack.NotNull;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Check(constraints = "length(trim(name)) > 0")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private Customer owner;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = {@JoinColumn(name = "pet_id")},
            inverseJoinColumns = {@JoinColumn(name = "schedule_id")}
    )
    private List<Schedule> schedules;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Nationalized
    @NotNull
    private String name;

    @NotNull
    private LocalDate birthDate;

    private String notes;

    public Pet() {
    }

    public Pet(Customer owner, List<Schedule> schedules, PetType type, String name, LocalDate birthDate, String notes) {
        this.owner = owner;
        this.schedules = schedules;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Pet(Long id, Customer owner, List<Schedule> schedules, PetType type, String name, LocalDate birthDate, String notes) {
        this.id = id;
        this.owner = owner;
        this.schedules = schedules;
        this.type = type;
        this.name = name;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public void addSchedule(Schedule schedule) {
        if (schedules == null) {
            schedules = new ArrayList<>();
        }

        schedules.add(schedule);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedule) {
        this.schedules = schedule;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
