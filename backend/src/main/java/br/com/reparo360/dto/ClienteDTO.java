package br.com.reparo360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "ClienteDTO", description = "Dados de um cliente")
public class ClienteDTO {

    @Schema(description = "Identificador do cliente", example = "1")
    private Long idCliente;

    @NotBlank(message = "Nome do cliente é obrigatório")
    @Size(max = 255, message = "Nome não pode exceder 255 caracteres")
    @Schema(description = "Nome completo do cliente", example = "Maria Souza", required = true)
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email não pode exceder 255 caracteres")
    @Schema(description = "E-mail do cliente", example = "maria.souza@example.com", required = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 50, message = "Telefone não pode exceder 50 caracteres")
    @Schema(description = "Telefone de contato do cliente", example = "(11) 91234-5678", required = true)
    private String telefone;

    @NotBlank
    @Size(max = 255)
    @Schema(description = "Logradouro (rua, avenida etc.)", example = "Av. Paulista, 1000", required = true)
    private String logradouro;

    @NotBlank
    @Size(max = 50)
    @Schema(description = "Número do endereço", example = "1000", required = true)
    private String numero;

    @Size(max = 255)
    @Schema(description = "Complemento do endereço", example = "Apto 101")
    private String complemento;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Bairro do cliente", example = "Bela Vista", required = true)
    private String bairro;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Cidade do cliente", example = "São Paulo", required = true)
    private String cidade;

    @NotBlank
    @Size(min = 2, max = 2)
    @Schema(description = "Sigla da UF (2 caracteres)", example = "SP", required = true)
    private String uf;

    @NotBlank
    @Size(max = 20)
    @Schema(description = "CEP do cliente", example = "01310-100", required = true)
    private String cep;


    @Schema(description = "Data de cadastro do cliente", example = "2025-06-12T10:15:30")
    private LocalDateTime dataCadastro;

    @Schema(description = "Origem do cadastro do cliente (p. ex., WEB, APP)", example = "WEB")
    private String origem;
}
