package com.udacity.jdnd.course3.critter.service.pet;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.pet.PetRepository;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.pet.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.service.user.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
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

        // Ensure entity state remains consistent when child gets updated.
        Pet savedPet = petRepository.save(pet);
        Customer customer = pet.getOwner();
        customer.addPet(savedPet);
        customerRepository.save(customer);
        return savedPet;
    }

    /**
     * Gets pet information by ID (or throws exception if non-existent)
     *
     * @param id the ID number of the pet to gather information on
     * @return the requested pet's information
     */
    public Pet findById(Long id) {
        return petRepository.
                findById(id)
                .orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> list() {
        return petRepository.findAll();
    }

    public List<Pet> findByOwnerId(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);

        return customer.getPets();
    }
}
