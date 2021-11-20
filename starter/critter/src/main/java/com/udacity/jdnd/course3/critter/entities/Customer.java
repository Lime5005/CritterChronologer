package com.udacity.jdnd.course3.critter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(length = 50)
    private String name;

    private String phoneNumber;

    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner",
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Pet> pets;

    @ManyToMany(mappedBy = "customers")
    private List<Schedule> schedules;

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
    }
}
