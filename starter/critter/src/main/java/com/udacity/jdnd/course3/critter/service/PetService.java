package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);
        Customer owner = savedPet.getOwner();
        List<Pet> pets = owner.getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(savedPet);
        owner.setPets(pets);
        customerRepository.save(owner);
        return savedPet;
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<Pet> getPetsByOwnerId(Long id) {
        return petRepository.getPetsByOwner_Id(id);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByScheduleId(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        return schedule.getPets();
    }
    public List<Pet> getPetsByOwner(Long ownerId){
        return petRepository.getPetsByOwner_Id(ownerId);
    }

    public Customer getOwnerByPetId(Long id) {
        return petRepository.getOwnerForPet(id);
    }
}
