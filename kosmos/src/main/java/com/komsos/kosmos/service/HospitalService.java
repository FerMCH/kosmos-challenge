package com.komsos.kosmos.service;

import java.util.List;

import com.komsos.kosmos.dto.request.AppointmentRequest;
import com.komsos.kosmos.model.Appointment;

public interface HospitalService {

    Appointment savAppointment(AppointmentRequest appointment);

    Appointment cancelAppointment(Long id);

    Appointment editAppointment(AppointmentRequest appointment);

    List<Appointment> getAppointmentsByDoctor(String doctorId, String date);

    List<Appointment> getAppointmentsByRoom(String roomId, String date);
   
}
