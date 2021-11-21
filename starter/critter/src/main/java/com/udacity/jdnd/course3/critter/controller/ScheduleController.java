package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.dto.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final PetService petService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ScheduleService scheduleService;

    public ScheduleController(PetService petService, CustomerService customerService,
                              EmployeeService employeeService, ScheduleService scheduleService) {
        this.petService = petService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return fromSchedule(scheduleService.saveSchedule(toSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(this::fromSchedule)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getSchedulesForPet(petId)
                .stream()
                .map(this::fromSchedule)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getSchedulesForEmployee(employeeId)
                .stream()
                .map(this::fromSchedule)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        List<Pet> pets = customer.getPets();

        ArrayList<Schedule> schedules = new ArrayList<>();

        for(Pet pet: pets){
            schedules.addAll(scheduleService.getSchedulesForCustomer(pet.getId()));
        }
        return schedules.stream().map(this::fromSchedule).collect(Collectors.toList());
    }

    private ScheduleDTO fromSchedule(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        scheduleDTO.setActivities(schedule.getEmployeeSkills());

        scheduleDTO.setPetIds(petService.getPetsByScheduleId(
                        schedule.getId())
                .stream()
                .map(Pet::getId)
                .collect(Collectors.toList()));

        scheduleDTO.setEmployeeIds(
                employeeService.getEmployeesByScheduleId(
                                schedule.getId())
                        .stream().map(Employee::getId)
                        .collect(Collectors.toList()));

        return scheduleDTO;
    }

    private Schedule toSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        schedule.setEmployeeSkills(scheduleDTO.getActivities());

        List<Employee> employees = new LinkedList<>();
        List<Pet> pets = new LinkedList<>();

        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            employees.add(employeeService.getEmployeeById(employeeId));
        }
        schedule.setEmployees(employees);

        for (Long petId : scheduleDTO.getPetIds()) {
            pets.add(petService.findById(petId));
        }
        schedule.setPets(pets);

        return schedule;
    }
}
