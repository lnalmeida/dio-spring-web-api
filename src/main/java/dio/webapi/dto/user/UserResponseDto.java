package dio.webapi.dto.user;

import java.time.LocalDateTime;
import java.util.List;

public record UserResponseDto(Long id,
                              String nome,
                              String username,
                              List<String> roles,
                              LocalDateTime dataCriacao,
                              LocalDateTime dataAlteracao
) {
}
