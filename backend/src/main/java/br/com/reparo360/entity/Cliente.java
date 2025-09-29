package br.com.reparo360.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "clientes")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotBlank(message = "Nome do cliente é obrigatório")
    @Size(max = 255, message = "Nome não pode exceder 255 caracteres")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email não pode exceder 255 caracteres")
    @Column(name = "email",unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 50, message = "Telefone não pode exceder 50 caracteres")
    @Column(name = "telefone")
    private String telefone;

    @Embedded
    private EnderecoAgendamento endereco;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "origem")
    private String origem;


}