package dio.webapi.controller;

import dio.webapi.dto.cliente.ClienteRequestDto;
import dio.webapi.dto.cliente.ClienteResponseDto;
import dio.webapi.service.ClienteService;
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

    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> obterClientes() {
        return new ResponseEntity<>(clienteService.getAllClientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterClientePorId(@PathVariable Long id) {
        Optional<ClienteResponseDto> cliente = clienteService.getClienteById(id);
        if(cliente.isEmpty()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> obterClientePorCpf(@PathVariable String cpf) {
        Optional<ClienteResponseDto> cliente = clienteService.getClienteByCpf(cpf);
        if(cliente.isEmpty()){
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> adicionarCliente(@RequestBody ClienteRequestDto data) {
        clienteService.addCliente(data);
        return new ResponseEntity<>("Cliente adicionado com sucesso", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editarClientePorId(@PathVariable Long id, @RequestBody ClienteRequestDto data) {
        if(clienteService.updateCliente(id, data).isEmpty()) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clienteService.updateCliente(id, data), HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarClientePorId(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>("Cliente deletado com sucesso", HttpStatus.NO_CONTENT);
    }

}
