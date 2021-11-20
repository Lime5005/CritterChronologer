package com.udacity.jdnd.course3.critter.entities;
import com.udacity.jdnd.course3.critter.entities.enums.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(length = 50)
    private String name;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass = DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "employees")
    private List<Schedule> schedules;

}
