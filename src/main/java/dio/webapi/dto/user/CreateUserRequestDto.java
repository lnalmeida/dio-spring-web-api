package dio.webapi.dto.user;

import java.time.LocalDateTime;
import java.util.List;

public record CreateUserRequestDto(String nome,
                                   String username,
                                   String password,
                                   List<String> roles,
                                   LocalDateTime dataCriacao,
                                   LocalDateTime dataAlteracao) {
}
