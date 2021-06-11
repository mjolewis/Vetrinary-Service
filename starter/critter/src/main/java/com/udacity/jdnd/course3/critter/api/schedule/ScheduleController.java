package com.udacity.jdnd.course3.critter.api.schedule;

import com.udacity.jdnd.course3.critter.domain.schedule.Schedule;
import com.udacity.jdnd.course3.critter.service.pet.PetService;
import com.udacity.jdnd.course3.critter.service.schedule.ScheduleService;
import com.udacity.jdnd.course3.critter.service.user.employee.EmployeeService;
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
    private final EmployeeService employeeService;
    private final PetService petService;

    public ScheduleController(ScheduleService scheduleService, EmployeeService employeeService,
                              PetService petService) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDtoToSchedule(scheduleDTO);

        Schedule savedSchedule = scheduleService.save(schedule);

        return convertScheduleToScheduleDto(savedSchedule);
    }

    private Schedule convertScheduleDtoToSchedule(ScheduleDTO scheduleDto) {
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDto, schedule, "employeeIds", "petIds");

        // Assign employee ids to the schedule. The DTO contains IDs to avoid sending larger objects over the wire.
        List<Long> employeeIds = scheduleDto.getEmployeeIds();
        employeeIds.forEach(employeeId -> schedule.addEmployee(employeeService.findById(employeeId)));

        // Assign pet ids to the schedule. The DTO contains IDs to avoid sending larger objects over the wire.
        List<Long> petIds = scheduleDto.getPetIds();
        petIds.forEach(petId -> schedule.addPet(petService.findById(petId)));

        return schedule;
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

        List<Long> employeeIds = new ArrayList<>();
        schedule.getEmployees().forEach(employee -> employeeIds.add(employee.getId()));

        List<Long> petIds = new ArrayList<>();
        schedule.getPets().forEach(pet -> petIds.add(pet.getId()));

        BeanUtils.copyProperties(schedule, scheduleDto, "employeeIds", "petIds");

        scheduleDto.setEmployeeIds(employeeIds);
        scheduleDto.setPetIds(petIds);

        return scheduleDto;
    }

    private List<ScheduleDTO> convertSchedulesToSchedulesDto(List<Schedule> schedules) {
        List<ScheduleDTO> schedulesDto = new ArrayList<>();

        for (Schedule schedule : schedules) {
            schedulesDto.add(convertScheduleToScheduleDto(schedule));
        }

        return schedulesDto;
    }
}
