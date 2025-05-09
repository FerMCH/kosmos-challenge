package com.komsos.kosmos.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.komsos.kosmos.model.Doctor;

public interface DoctorRepositoy extends JpaRepository<Doctor, Long> {

    Doctor findByNameAndFatherLastNameAndMotherLastName(String name, String fatherLastName, String motherLastName);


}
