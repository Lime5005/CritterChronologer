package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public void setDaysAvailable(Long id, Set<DayOfWeek> daysAvailable) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee;
        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        }
    }

    public Set<Employee> findAvailableEmployees(Set<EmployeeSkill> skills, DayOfWeek days) {
        return employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills, days);

    }

    public List<Schedule> findAllSchedulesByEmployeeId(Long id) {
        return employeeRepository.findAllSchedulesByEmployeeId(id);
    }

    public List<Employee> getEmployeesByScheduleId(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.map(Schedule::getEmployees).orElse(null);
    }




}
