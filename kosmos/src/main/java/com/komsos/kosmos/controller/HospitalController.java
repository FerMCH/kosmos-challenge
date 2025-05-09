package com.komsos.kosmos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.komsos.kosmos.dto.request.AppointmentRequest;
import com.komsos.kosmos.service.HospitalService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;




@AllArgsConstructor
@Slf4j
@RestController()
@RequestMapping("${routes.hospital}")
public class HospitalController {

    private HospitalService hospitalService;

    @PostMapping()
    public ResponseEntity<Object> postMethodName(@RequestBody AppointmentRequest appointmentRequest) {
        
        return new ResponseEntity<>(this.hospitalService.savAppointment(appointmentRequest), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putMethodName(@PathVariable String id) {
        this.hospitalService.cancelAppointment(Long.valueOf(id));
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    @GetMapping("doctor/appointments/{doctorId}/{date}")
    public ResponseEntity<Object> getAppointmentsByDoctor(@PathVariable String doctorId, @PathVariable String date) {

        return new ResponseEntity<>(this.hospitalService.getAppointmentsByDoctor(doctorId, date), HttpStatus.OK);
    }

    @GetMapping("room/appointments/{roomId}/{date}")
    public ResponseEntity<Object> getAppointmentsByRoom(@PathVariable String roomId, @PathVariable String date) {

        return new ResponseEntity<>(this.hospitalService.getAppointmentsByRoom(roomId, date), HttpStatus.OK);
    }
    
    

    

}
