package com.udacity.jdnd.course3.critter.service.schedule;

import com.udacity.jdnd.course3.critter.domain.pet.Pet;
import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.domain.user.customer.Customer;
import com.udacity.jdnd.course3.critter.domain.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.service.schedule.exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.service.user.customer.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Either creates or updates an schedule, based on prior existence of schedule
     *
     * @param schedule A schedule object, which can be either new or existing
     * @return The new or updated schedule stored in the repository
     */
    public Schedule save(Schedule schedule) {
        if (schedule.getId() != null) {
            return scheduleRepository.findById(schedule.getId())
                    .map(scheduleToBeUpdated -> {
                        scheduleToBeUpdated.setEmployees(schedule.getEmployees());
                        scheduleToBeUpdated.setPets(schedule.getPets());
                        scheduleToBeUpdated.setDate(schedule.getDate());
                        scheduleToBeUpdated.setActivities(schedule.getActivities());
                        return scheduleToBeUpdated;
                    }).orElseThrow(ScheduleNotFoundException::new);
        }

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByCustomerId(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);

        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();
        pets.forEach(pet -> schedules.addAll(scheduleRepository.findAllByPets_Id(pet.getId())));

        return schedules;
    }

    public List<Schedule> findByEmployeeId(Long id) {
        return scheduleRepository.findAllByEmployees_Id(id);
    }

    public List<Schedule> findByPetId(Long id) {
        return scheduleRepository.findAllByPets_Id(id);
    }
}
