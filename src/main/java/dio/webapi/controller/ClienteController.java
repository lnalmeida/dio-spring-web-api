package dio.webapi.controller;

import dio.webapi.dto.cliente.ClienteRequestDto;
import dio.webapi.dto.cliente.ClienteResponseDto;
import dio.webapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Retorna todos os clientes", description = "Este endpoint retorna a lista de todos os clientes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> obterClientes() {
        return new ResponseEntity<>(clienteService.getAllClientes(), HttpStatus.OK);
    }

    @Operation(summary = "Retorna um cliente pelo ID", description = "Este endpoint retorna um único cliente, realizando a busca pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornada com sucesso")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> obterClientePorId(
            @Parameter(description = "ID do registro de cliente a ser retornado", required = true, example = "1")
            @PathVariable Long id
    ) {
        Optional<ClienteResponseDto> cliente = clienteService.getClienteById(id);
        if(cliente.isEmpty()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }


    @Operation(summary = "Retorna um cliente pelo CPF", description = "Este endpoint retorna um único cliente, realizando a busca pelo CPF.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente retornada com sucesso")
    })
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> obterClientePorCpf(
            @Parameter(description = "CPF do cliente a ser retornado, sem pontos e traços", required = true, example = "11111111111")
            @PathVariable String cpf
    ) {
        Optional<ClienteResponseDto> cliente = clienteService.getClienteByCpf(cpf);
        if(cliente.isEmpty()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @Operation(summary = "Adiciona um novo cliente", description = "Este endpoint adiciona um novo cliente à base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<String> adicionarCliente(
            @Parameter(description = "JSON contendo os dados do cliente a ser cadastrado",
                    required = true,
                    example = "{" +
                            "'nome':'João'," +
                            "'email':'joão@teste.com'," +
                            "'cpf':'111.111.111-11 || 11111111111'," +
                            "}")
            @RequestBody ClienteRequestDto data
    ) {
        clienteService.addCliente(data);
        return new ResponseEntity<>("Cliente adicionado com sucesso", HttpStatus.CREATED);
    }

    @Operation(summary = "Altera os dados de um cliente", description = "Este endpoint altera os dados um cliente previamente cadastrado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente editado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Object> editarClientePorId(
            @Parameter(description = "ID do registro de cliente a ser alterado", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "JSON contendo os dados do cliente a ser alterado",
                    required = true,
                    example = "{" +
                            "'nome':'João'," +
                            "'email':'joão@teste.com'," +
                            "'cpf':'111.111.111-11 || 11111111111'," +
                            "}")
            @RequestBody ClienteRequestDto data
    ) {
        if(clienteService.updateCliente(id, data).isEmpty()) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clienteService.updateCliente(id, data), HttpStatus.OK);

    }

    @Operation(summary = "Exclui um cliente", description = "Este endpoint deleta os dados de um cliente cadastrado da base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarClientePorId(
            @Parameter(description = "ID do registro de cliente a ser excluído", required = true, example = "1")
            @PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>("Cliente deletado com sucesso", HttpStatus.NO_CONTENT);
    }

}
