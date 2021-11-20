package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForPet(Long id) {
        return scheduleRepository.getSchedulesByPets_Id(id);
    }

    public List<Schedule> getSchedulesForCustomer(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        return optionalPet.map(pet -> scheduleRepository.getSchedulesByPetsContains(pet)).orElse(null);
    }

    public List<Schedule> getSchedulesForEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(employee -> scheduleRepository.getSchedulesByEmployeesContains(employee)).orElse(null);
    }

}
