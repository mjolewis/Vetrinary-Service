package com.udacity.jdnd.course3.critter.api.user.customer;

import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.service.user.customer.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Customers.
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer();

        BeanUtils.copyProperties(customerDTO, customer);

        customer = customerService.save(customer);

        return convertToCustomerDto(customer);
    }

    @GetMapping()
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.list();

        return converToCustomerDtos(customers);
    }

    @GetMapping("/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.findByPetId(petId);

        return convertToCustomerDto(customer);
    }

    private CustomerDTO convertToCustomerDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

    private List<CustomerDTO> converToCustomerDtos(List<Customer> customers) {
        List<CustomerDTO> customerDtos = new ArrayList<>();

        for (Customer customer : customers) {
            BeanUtils.copyProperties(customer, customerDtos);
        }

        return customerDtos;
    }
}
