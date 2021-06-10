package com.udacity.jdnd.course3.critter.api.pet;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.service.pet.PetService;
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

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();

        BeanUtils.copyProperties(petDTO, pet);

        pet = petService.save(pet);

        return convertToPetDto(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.findById(petId);

        return convertToPetDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets;

        pets = petService.list();

        return convertToPetDtos(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<Pet> pets = petService.findByOwnerId(ownerId);

        return convertToPetDtos(pets);
    }

    private PetDTO convertToPetDto(Pet pet) {
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet, petDTO);

        return petDTO;
    }

    private List<PetDTO> convertToPetDtos(List<Pet> pets) {
        List<PetDTO> petDtos = new ArrayList<>();

        for (Pet pet : pets) {
            BeanUtils.copyProperties(pet, petDtos);
        }

        return petDtos;
    }
}
