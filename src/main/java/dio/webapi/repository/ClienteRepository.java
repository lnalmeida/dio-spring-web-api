package dio.webapi.repository;

import dio.webapi.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    Optional<Cliente> findByCpf(@Param("cpf") String cpf);
}
