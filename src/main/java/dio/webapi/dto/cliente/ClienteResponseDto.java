package dio.webapi.dto.cliente;

import java.time.LocalDateTime;

public record ClienteResponseDto(Long id, String nome, String email, String cpf, LocalDateTime dataCriacao, LocalDateTime dataAlteracao) {
}
