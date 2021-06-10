package com.udacity.jdnd.course3.critter.service.pet;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.pet.PetRepository;
import com.udacity.jdnd.course3.critter.service.pet.exception.PetNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Either creates or updates a pet, based on prior existence of pet
     * @param pet A pet object, which can be either new or existing
     * @return The new or updated pet stored in the repository
     */
    public Pet save(Pet pet) {
        if (pet.getId() != null) {
            return petRepository.findById(pet.getId())
                    .map(petToBeUpdated -> {
                        petToBeUpdated.setName(pet.getName());
                        petToBeUpdated.setType(pet.getType());
                        petToBeUpdated.setBirthDate(pet.getBirthDate());
                        petToBeUpdated.setNotes(pet.getNotes());
                        petToBeUpdated.setOwner(pet.getOwner());
                        return petRepository.save(petToBeUpdated);
                    }).orElseThrow(PetNotFoundException::new);
        }

        return petRepository.save(pet);
    }

    /**
     * Gets pet information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the pet to gather information on
     * @return the requested pet's information
     */
    public Pet findById(long id) {
        return petRepository.
                findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> list() {
        return petRepository.findAll();
    }

    public List<Pet> findByOwnerId(long id) {
        return petRepository.findPetsByOwner_Id(id);
    }
}
