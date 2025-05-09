package com.komsos.kosmos.repositoy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.komsos.kosmos.model.Room;

public interface RoomRepositoy extends JpaRepository<Room, Long>{

    Room findByNumberAndFloor(String number, String floor);

}
