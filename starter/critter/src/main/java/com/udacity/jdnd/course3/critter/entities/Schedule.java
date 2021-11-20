package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.entities.enums.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schudele_employee",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schudele_customer",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")}
    )
    private List<Customer> customers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_id")}
    )
    private List<Pet> pets;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> employeeSkills;

}
