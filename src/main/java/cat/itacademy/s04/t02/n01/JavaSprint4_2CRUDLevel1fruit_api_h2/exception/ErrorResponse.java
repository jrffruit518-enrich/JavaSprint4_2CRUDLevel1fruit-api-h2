package cat.itacademy.s04.t02.n01.JavaSprint4_2CRUDLevel1fruit_api_h2.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime localDateTime) {
}
