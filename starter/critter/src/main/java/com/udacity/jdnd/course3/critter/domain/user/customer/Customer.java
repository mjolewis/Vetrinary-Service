package com.udacity.jdnd.course3.critter.domain.user.customer;

import com.sun.istack.NotNull;
import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Check(constraints = "LENGTH(TRIM(name)) > 0")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Nationalized
    @NotNull
    private String name;

    @NotNull
    @Column(length = 10)
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Pet> pets;

    private String notes;

    public Customer() {
    }

    public Customer(String name, String phoneNumber, List<Pet> pets, String notes) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pets = pets;
        this.notes = notes;
    }

    public Customer(Long id, String name, String phoneNumber, List<Pet> pets, String notes) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.pets = pets;
        this.notes = notes;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
