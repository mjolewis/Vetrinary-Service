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

        return convertCustomerToCustomerDto(customer);
    }

    @GetMapping()
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.list();

        return convertCustomersToCustomersDto(customers);
    }

    @GetMapping("/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable Long petId){
        Customer customer = customerService.findByPetId(petId);

        return convertCustomerToCustomerDto(customer);
    }

    private CustomerDTO convertCustomerToCustomerDto(Customer customer) {
        CustomerDTO customerDto = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();

        customer.getPets().forEach(pet -> petIds.add(pet.getId()));

        customerDto.setPetIds(petIds);

        BeanUtils.copyProperties(customer, customerDto);

        return customerDto;
    }

    private List<CustomerDTO> convertCustomersToCustomersDto(List<Customer> customers) {
        List<CustomerDTO> customersDto = new ArrayList<>();

        for (Customer customer : customers) {
            customersDto.add(convertCustomerToCustomerDto(customer));
        }

        return customersDto;
    }
}
