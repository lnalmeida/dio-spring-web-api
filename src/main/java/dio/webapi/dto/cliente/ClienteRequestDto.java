package dio.webapi.dto.cliente;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClienteRequestDto(

        @NotNull(message = "O nome não pode ser nulo.")
        @NotBlank(message = "O nome não pode ser vazio.")
        String nome,

        @NotNull(message = "O email não pode ser nulo.")
        @NotNull(message = "O email não pode ser vazio.")
        String email,

        @NotNull(message = "O cpf não pode ser nulo.")
        @NotBlank(message = "O cpf não pode ser vazio.")
        String cpf
) {
}
