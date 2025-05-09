package com.komsos.kosmos.service.imp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.komsos.kosmos.constants.ErrorConstants;
import com.komsos.kosmos.dto.request.AppointmentRequest;
import com.komsos.kosmos.exception.error.BadRequestException;
import com.komsos.kosmos.model.Appointment;
import com.komsos.kosmos.model.Doctor;
import com.komsos.kosmos.model.Room;
import com.komsos.kosmos.repositoy.AppointmentRepository;
import com.komsos.kosmos.repositoy.DoctorRepositoy;
import com.komsos.kosmos.repositoy.RoomRepositoy;
import com.komsos.kosmos.service.HospitalService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class HospitalServiceImp implements HospitalService {

    private final AppointmentRepository appointmentRepository;

    private final DoctorRepositoy doctorRepositoy;

    private final RoomRepositoy roomRepositoy;

    @Override
    public Appointment savAppointment(AppointmentRequest appointment) {
        LocalDateTime fecha = LocalDateTime.parse(appointment.date());
        Optional<List<Appointment>> clientAppointments = Optional.ofNullable(this.appointmentRepository
            .findByPatientNameAndDateBetween(appointment.patientName(), fecha.minus(1, ChronoUnit.MINUTES), fecha.plus(2, ChronoUnit.HOURS)));
           
        if (clientAppointments.isEmpty() || !clientAppointments.get().isEmpty()) {
            throw new BadRequestException(ErrorConstants.BAD_REQUEST, ErrorConstants.ERROR_DETAIL);
        }

        Optional<Doctor> doctor = Optional.ofNullable(this.doctorRepositoy.findByNameAndFatherLastNameAndMotherLastName(appointment.doctor().name(), appointment.doctor().fatherLastName(), appointment.doctor().motherLastName()));

        if (doctor.isEmpty()) {
            throw new BadRequestException(ErrorConstants.BAD_REQUEST, ErrorConstants.ERROR_DETAIL);
        }

        Optional<Room> room = Optional.ofNullable(this.roomRepositoy.findByNumberAndFloor(appointment.room().number(), appointment.room().floor()));

        if (room.isEmpty()) {
            throw new BadRequestException(ErrorConstants.BAD_REQUEST, ErrorConstants.ERROR_DETAIL);
        }

        Optional<List<Appointment>> doctorAppointments = Optional.ofNullable(this.appointmentRepository.findByDoctorIdAndDateAfter(doctor.get().getId(), fecha.minus(1, ChronoUnit.MINUTES)));

        if (doctorAppointments.isEmpty() || doctorAppointments.get().size() >= 8) {
            throw new BadRequestException(ErrorConstants.BAD_REQUEST, ErrorConstants.ERROR_DETAIL);
        } 
        
        doctorAppointments.get().forEach(row -> {

            if(this.dateValid(fecha, row.getDate())) {
                throw new BadRequestException(ErrorConstants.BAD_REQUEST, ErrorConstants.ERROR_DETAIL);
            }

        });
      

        Optional<List<Appointment>> roomAppointments = Optional.ofNullable(this.appointmentRepository.findByRoomIdAndDateBetween
            (room.get().getId(), fecha.minus(1, ChronoUnit.MINUTES), fecha.plus(2, ChronoUnit.HOURS)));

        if (roomAppointments.isEmpty() || !roomAppointments.get().isEmpty()) {
            throw new BadRequestException(ErrorConstants.BAD_REQUEST, ErrorConstants.ERROR_DETAIL);
        }


        Appointment newAppointment = new Appointment();
        newAppointment.setCanceled(false);
        newAppointment.setDate(fecha);
        newAppointment.setDoctor(doctor.get());
        newAppointment.setPatientName(appointment.patientName());
        newAppointment.setRoom(room.get());
        

        return this.appointmentRepository.save(newAppointment);
        
    }

    @Override
    public Appointment cancelAppointment(Long id) {
        Optional<Appointment> appointment = this.appointmentRepository.findById(id);

        if (appointment.isEmpty() || appointment.get().isCanceled()) {

        }

        LocalDateTime now = LocalDateTime.now(); 

        if(now.isAfter(appointment.get().getDate())) {

        }

        appointment.get().setCanceled(true);
        
        return this.appointmentRepository.save(appointment.get());
    }

    @Override
    public Appointment editAppointment(AppointmentRequest appointment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Appointment> getAppointmentsByDoctor(String doctorId, String date) {
        return this.appointmentRepository.findByDoctorIdAndDateAfter(Long.valueOf(doctorId), LocalDateTime.parse(date));
    }

    @Override
    public List<Appointment> getAppointmentsByRoom(String roomId, String date) {
        return this.appointmentRepository.findByRoomIdAndDateAfter(Long.valueOf(roomId), LocalDateTime.parse(date));
    }
    
    private boolean dateValid(LocalDateTime dateReference,LocalDateTime date) {

        return !date.isBefore(dateReference.minus(1, ChronoUnit.MINUTES)) && !date.isAfter(dateReference.plus(2, ChronoUnit.HOURS));
    }


}
