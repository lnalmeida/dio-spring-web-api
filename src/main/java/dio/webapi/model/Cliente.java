package dio.webapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "tb_clientes", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    @Column(name = "cl_nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cl_email", nullable = false, length = 100)
    private String email;

    @Column(name = "cl_cpf", nullable = false, length = 50)
    private String cpf;

    @Column(name = "cl_dataCriacao")
    private LocalDateTime dataCriacao;

    @Column(name = "cl_dataAlteracao")
    private LocalDateTime dataAlteracao;

}
