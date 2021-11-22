package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.enums.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.getOne(id);
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

    public List<Employee> findAvailableEmployees(Set<EmployeeSkill> skills, DayOfWeek days) {
        List<Employee> employees = employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills, days);
        List<Employee> employeeList = new LinkedList<>();
        employees.forEach(thisEmployee -> {
            if(thisEmployee.getSkills().containsAll(skills)) {
                employeeList.add(thisEmployee);
            }
        });
        return employeeList;
    }

    public List<Employee> getEmployeesByScheduleId(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        return schedule.getEmployees();
    }

}
