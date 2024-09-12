package dio.webapi.service;

import com.fasterxml.jackson.datatype.jdk8.OptionalLongDeserializer;
import dio.webapi.dto.cliente.ClienteRequestDto;
import dio.webapi.dto.cliente.ClienteResponseDto;
import dio.webapi.model.Cliente;
import dio.webapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponseDto> getAllClientes(){
        return clienteRepository.findAll().stream()
                .map(cliente -> new ClienteResponseDto(cliente.getId(), cliente.getNome(), cliente.getEmail(), formatarCpf(cliente.getCpf()), cliente.getDataCriacao(), cliente.getDataAlteracao()))
                .toList();
    }

    public Optional<ClienteResponseDto> getClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> new ClienteResponseDto(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        formatarCpf(cliente.getCpf()),
                        cliente.getDataCriacao(),
                        cliente.getDataAlteracao()
                        )
                );
    }

    public Optional<ClienteResponseDto> getClienteByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf.replace('.', ' ').replace('-', ' ').trim())
                .map(cliente -> new ClienteResponseDto(
                                cliente.getId(),
                                cliente.getNome(),
                                cliente.getEmail(),
                                formatarCpf(cliente.getCpf()),
                                cliente.getDataCriacao(),
                                cliente.getDataAlteracao()
                        )
                );
    }

    public void addCliente(ClienteRequestDto data) {
        Cliente newCliente = new Cliente();
        newCliente.setNome(data.nome());
        newCliente.setEmail(data.email());
        newCliente.setCpf(data.cpf().replaceAll("[^\\d]", ""));
        newCliente.setDataCriacao(LocalDateTime.now());
        clienteRepository.save(newCliente);
    }

    public Optional<ClienteResponseDto> updateCliente(Long id, ClienteRequestDto data) {
        return clienteRepository.findById(id).map(c -> {
            c.setNome(data.nome());
            c.setEmail(data.email());
            c.setCpf(data.cpf().replaceAll("[^\\d]", ""));
            c.setDataCriacao(c.getDataCriacao());
            c.setDataAlteracao(LocalDateTime.now());

            clienteRepository.save(c);
            return new ClienteResponseDto(
                    c.getId(),
                    c.getNome(),
                    c.getEmail(),
                    formatarCpf(c.getCpf()),
                    c.getDataCriacao(),
                    c.getDataAlteracao()
            );
        });
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    private String formatarCpf(String cpf) {
        // Garantir que o CPF tenha exatamente 11 dígitos
        String cpfSemFormatacao = cpf.replaceAll("[^\\d]", "");
        if (cpfSemFormatacao.length() != 11) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos");
        }

        // Formata o CPF no padrão ###.###.###-##
        return cpfSemFormatacao.replaceFirst("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
