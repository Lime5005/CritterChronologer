package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.dto.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = PetDTO.toPet(petDTO);
        return PetDTO.fromPet(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return PetDTO.fromPet(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets().stream()
                .map(PetDTO::fromPet)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwnerId(ownerId).stream()
                .map(PetDTO::fromPet)
                .collect(Collectors.toList());
    }
}
