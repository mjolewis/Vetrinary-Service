package com.udacity.jdnd.course3.critter.domain.user.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Customer findCustomerByPetIdsContains(Long id);

    Customer findCustomerByPetsContains(Long id);

    @Query("SELECT c FROM Customer c JOIN c.pets o WHERE o.id = :id")
    Customer findOwnerByPet(Long id);
}
