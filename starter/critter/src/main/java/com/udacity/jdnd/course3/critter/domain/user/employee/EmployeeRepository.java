package com.udacity.jdnd.course3.critter.domain.user.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//    @Modifying
//    @Query("UPDATE Employee SET daysAvailable = :daysAvailable WHERE id = :id")
//    void saveEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long id);

    List<Employee> findEmployeeByDaysAvailableContaining(LocalDate date);
}
