package dev.guilhermepisco.msscjmsexample.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -7967718829318619602L;

    private UUID id;
    private String message;
}
