package com.udacity.jdnd.course3.critter.entities.dto;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.enums.PetType;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import lombok.*;

import java.time.LocalDate;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
@Data
@NoArgsConstructor
@Builder
public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String notes;

    public static PetDTO fromPet(Pet pet) {
        if (pet == null) {
            return null;
        }
        Long ownerId = pet.getOwner().getId();

        return PetDTO.builder()
                .id(pet.getId())
                .type(pet.getPetType())
                .ownerId(ownerId)
                .name(pet.getName())
                .birthDate(pet.getBirthDate())
                .notes(pet.getNotes())
                .build();
    }

    public static Pet toPet(PetDTO petDTO) {
        CustomerService customerService = new CustomerService();
        if (petDTO == null) {
            return null;
        }
        long ownerId = petDTO.getOwnerId();
        Customer owner = customerService.getCustomerById(ownerId);
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setPetType(PetType.valueOf(petDTO.getType().name()));
        pet.setOwner(owner);
        pet.setNotes(petDTO.getNotes());
        pet.setBirthDate(petDTO.getBirthDate());
        return pet;
    }

}
