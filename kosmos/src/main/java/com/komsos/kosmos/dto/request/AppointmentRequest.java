package com.komsos.kosmos.dto.request;

public record AppointmentRequest(String date, DoctorRequest doctor, RoomRequest room, String patientName) {

}
