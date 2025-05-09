package com.komsos.kosmos.repositoy;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.komsos.kosmos.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

    List<Appointment> findByDoctorIdAndDateBetween(Long id, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByDoctorIdAndDateAfter(Long id, LocalDateTime start);

    List<Appointment> findByDateAfter(LocalDateTime start);

    List<Appointment> findByRoomIdAndDateAfter(Long roomId, LocalDateTime start);

    List<Appointment> findByRoomIdAndDateBetween(Long id, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientNameAndDateBetween(String name, LocalDateTime start, LocalDateTime end);


}
