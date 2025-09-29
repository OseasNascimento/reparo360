package br.com.reparo360.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "AgendamentoDTO", description = "Dados de um agendamento de serviço")
public class AgendamentoDTO {

    @Schema(description = "Identificador do agendamento", example = "1")
    private Long id;

    @NotBlank(message = "Nome do cliente é obrigatório")
    @Schema(description = "Nome completo do cliente", example = "João da Silva", required = true)
    private String nomeCliente;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    @Schema(description = "E-mail do cliente", example = "joao@example.com", required = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Schema(description = "Telefone de contato do cliente", example = "(11)99999-9999", required = true)
    private String telefone;

    @NotNull(message = "Cliente é obrigatório")
    @Schema(description = "ID do cliente", example = "10", required = true)
    private Long clienteId;

    @NotNull(message = "Técnico é obrigatório")
    @Schema(description = "ID do técnico responsável", example = "5", required = true)
    private Long tecnicoId;

    @NotNull(message = "Data de agendamento é obrigatória")
    @Schema(description = "Data e hora planejadas para o serviço", example = "2025-06-15T14:30:00", required = true)
    private LocalDateTime dataAgendamento;

    @NotEmpty(message = "Selecione ao menos um serviço")
    @Schema(description = "Lista de IDs de serviços solicitados", example = "[1, 2]", required = true)
    private List<@NotNull(message = "ID de serviço não pode ser nulo") Long> servicosId;

    @Size(max = 255, message = "Observações não podem exceder 255 caracteres")
    @Schema(description = "Observações adicionais sobre o agendamento", example = "Cliente solicita troca de filtro")
    private String observacoes;

    @NotNull(message = "Status é obrigatório")
    @Schema(description = "Status atual do agendamento", example = "AGENDADO", required = true)
    private String status;

    @NotBlank(message = "Logradouro é obrigatório")
    @Schema(description = "Logradouro do local do serviço", example = "Rua das Flores, 123", required = true)
    private String logradouro;

    @Schema(description = "Número do imóvel", example = "123")
    private String numero;

    @Schema(description = "Complemento do endereço", example = "Apto 45")
    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    @Schema(description = "Bairro do endereço", example = "Centro", required = true)
    private String bairro;

    @NotBlank(message = "Cidade é obrigatório")
    @Schema(description = "Cidade do endereço", example = "São Paulo", required = true)
    private String cidade;

    @NotBlank(message = "UF é obrigatório")
    @Schema(description = "Unidade Federativa (estado)", example = "SP", required = true)
    private String uf;

    @NotBlank(message = "CEP é obrigatório")
    @Schema(description = "CEP do endereço", example = "01001-000", required = true)
    private String cep;
}
