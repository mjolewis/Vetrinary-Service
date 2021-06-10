package com.udacity.jdnd.course3.critter.service.user.customer;

import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.user.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Either creates or updates a customer, based on prior existence of customer
     * @param customer A customer object, which can be either new or existing
     * @return The new or updated customer stored in the repository
     */
    public Customer save(Customer customer) {
        if (customer.getId() != null) {
            return customerRepository.findById(customer.getId())
                    .map(customerToBeUpdated -> {
                        customerToBeUpdated.setName(customer.getName());
                        customerToBeUpdated.setNotes(customer.getNotes());
                        customerToBeUpdated.setPets(customer.getPets());
                        customerToBeUpdated.setPhoneNumber(customer.getPhoneNumber());
                        return customerToBeUpdated;
                    }).orElseThrow(CustomerNotFoundException::new);
        }

        return customerRepository.save(customer);
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer findByPetId(Long id) {
        //return customerRepository.findCustomerByPetsContains(id);

        return customerRepository.findOwnerByPet(id);
    }
}
