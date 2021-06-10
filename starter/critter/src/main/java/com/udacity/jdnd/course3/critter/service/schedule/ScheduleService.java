package com.udacity.jdnd.course3.critter.service.schedule;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.domain.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.schedule.exception.ScheduleNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * Either creates or updates an schedule, based on prior existence of schedule
     * @param schedule A schedule object, which can be either new or existing
     * @return The new or updated schedule stored in the repository
     */
    public Schedule save(Schedule schedule) {
        if (schedule.getId() != null) {
            return scheduleRepository.findById(schedule.getId())
                    .map(scheduleToBeUpdated -> {
                        scheduleToBeUpdated.setEmployeeIds(schedule.getEmployeeIds());
                        scheduleToBeUpdated.setPetIds(schedule.getPetIds());
                        scheduleToBeUpdated.setDate(schedule.getDate());
                        return scheduleToBeUpdated;
                    }).orElseThrow(ScheduleNotFoundException::new);
        }

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPetId(long id) {
        return scheduleRepository.findScheduleByPetIds(id);
    }

    public List<Schedule> findByEmployeeId(long id) {
        return scheduleRepository.findScheduleByEmployeeIds(id);
    }

    public List<Schedule> findByCustomerId(long id) {
        return scheduleRepository.findScheduleByCustomerIds(id);
    }
}
