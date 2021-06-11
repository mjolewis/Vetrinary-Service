package com.udacity.jdnd.course3.critter.api.pet;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.service.pet.PetService;
import com.udacity.jdnd.course3.critter.service.user.customer.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO, pet, "owner");

        Customer owner = customerService.findById(petDTO.getOwnerId());
        pet.setOwner(owner);

        Pet savedPet = petService.save(pet);

        return convertPetToPetDto(savedPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        Pet pet = petService.findById(petId);

        return convertPetToPetDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets;

        pets = petService.list();

        return convertPetsToPetsDto(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {
        List<Pet> pets = petService.findByOwnerId(ownerId);

        return convertPetsToPetsDto(pets);
    }

    private PetDTO convertPetToPetDto(Pet pet) {
        PetDTO petDto = new PetDTO();

        BeanUtils.copyProperties(pet, petDto, "owner");
        petDto.setOwnerId(pet.getOwner().getId());

        return petDto;
    }

    private List<PetDTO> convertPetsToPetsDto(List<Pet> pets) {
        List<PetDTO> petsDto = new ArrayList<>();

        for (Pet pet : pets) {
            petsDto.add(convertPetToPetDto(pet));
        }

        return petsDto;
    }
}
