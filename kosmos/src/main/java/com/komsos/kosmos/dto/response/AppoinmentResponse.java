package com.komsos.kosmos.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AppoinmentResponse {

    private Long id;

    private String patientName;

    private LocalDateTime date;

    private boolean isCanceled;

    private String doctorId;

    private String roomId;

}
