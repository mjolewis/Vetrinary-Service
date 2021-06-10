package com.udacity.jdnd.course3.critter.api.schedule;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.service.schedule.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);

        schedule = scheduleService.save(schedule);

        return convertScheduleToScheduleDto(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.list();

        return convertSchedulesToSchedulesDto(schedules);
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable Long petId) {
        List<Schedule> schedules = scheduleService.findByPetId(petId);

        return convertSchedulesToSchedulesDto(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable Long employeeId) {
        List<Schedule> schedules = scheduleService.findByEmployeeId(employeeId);

        return convertSchedulesToSchedulesDto(schedules);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable Long customerId) {
        List<Schedule> schedules = scheduleService.findByCustomerId(customerId);

        return convertSchedulesToSchedulesDto(schedules);
    }

    private ScheduleDTO convertScheduleToScheduleDto(Schedule schedule) {
        ScheduleDTO scheduleDto = new ScheduleDTO();

        BeanUtils.copyProperties(schedule, scheduleDto);

        return scheduleDto;
    }

    private List<ScheduleDTO> convertSchedulesToSchedulesDto(List<Schedule> schedules) {
        List<ScheduleDTO> schedulesDto = new ArrayList<>();

        for (Schedule schedule : schedules) {
            ScheduleDTO scheduleDto = new ScheduleDTO();
            BeanUtils.copyProperties(schedule, scheduleDto);
            schedulesDto.add(scheduleDto);
        }

        return schedulesDto;
    }
}
