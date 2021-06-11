package com.udacity.jdnd.course3.critter.domain.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByEmployees_Id(Long id);

    List<Schedule> findAllByPets_Id(Long id);
}
