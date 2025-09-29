package br.com.reparo360.financeiro.mapper;

import br.com.reparo360.financeiro.dto.LancamentoFinanceiroDTO;
import br.com.reparo360.financeiro.entity.LancamentoFinanceiro;
import br.com.reparo360.financeiro.entity.TipoTransacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LancamentoFinanceiroMapper {

    /**
     * Converte Entidade → DTO.
     *
     * - O campo tipoTransacao (enum) será convertido automaticamente em String (usando name()).
     */
    @Mapping(source = "id",                     target = "id")
    @Mapping(source = "contaCaixa.id",          target = "contaCaixaId")
    @Mapping(source = "categoriaFinanceira.id", target = "categoriaFinanceiraId")
    @Mapping(source = "valor",                  target = "valor")
    @Mapping(source = "dataLancamento",         target = "dataLancamento")
    @Mapping(source = "tipoTransacao",          target = "tipoTransacao")
    @Mapping(source = "descricao",              target = "descricao")
    LancamentoFinanceiroDTO toDTO(LancamentoFinanceiro entity);

    /**
     * Converte DTO → Entidade.
     *
     * - Ignora id, contaCaixa e categoriaFinanceira (serão atribuídos manualmente no Service).
     * - Para converter o campo tipoTransacao (String) em enum TipoTransacao de forma case-insensitive,
     *   usamos o método mapearTipo (anotado com @Named("mapearTipo")).
     */
    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "contaCaixa",             ignore = true)
    @Mapping(target = "categoriaFinanceira",    ignore = true)
    @Mapping(source = "valor",                  target = "valor")
    @Mapping(source = "dataLancamento",         target = "dataLancamento")
    @Mapping(source = "tipoTransacao",          target = "tipoTransacao", qualifiedByName = "mapearTipo")
    @Mapping(source = "descricao",              target = "descricao")
    LancamentoFinanceiro toEntity(LancamentoFinanceiroDTO dto);

    /**
     * Converte lista de Entidades → lista de DTOs.
     */
    List<LancamentoFinanceiroDTO> toDTOList(List<LancamentoFinanceiro> entities);

    /**
     * Atualiza apenas os campos mutáveis da entidade com base no DTO.
     * Ignora contaCaixa e categoriaFinanceira (se quiser permitir mudança, trate no Service).
     */
    @Mapping(target = "contaCaixa",          ignore = true)
    @Mapping(target = "categoriaFinanceira", ignore = true)
    @Mapping(source = "valor",               target = "valor")
    @Mapping(source = "dataLancamento",      target = "dataLancamento")
    @Mapping(source = "tipoTransacao",       target = "tipoTransacao", qualifiedByName = "mapearTipo")
    @Mapping(source = "descricao",           target = "descricao")
    void updateEntityFromDTO(LancamentoFinanceiroDTO dto, @MappingTarget LancamentoFinanceiro entity);

    /**
     * Método utilitário para converter String → TipoTransacao de modo case-insensitive.
     * Se o valor for inválido (não corresponder a "RECEITA" ou "DESPESA" após trim/uppercase), vai lançar IllegalArgumentException.
     */
    @Named("mapearTipo")
    default TipoTransacao mapearTipo(String tipo) {
        if (tipo == null) {
            return null;
        }
        try {
            return TipoTransacao.valueOf(tipo.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    "TipoTransacao inválido: '" + tipo + "'. Use 'RECEITA' ou 'DESPESA' (qualquer capitalização)."
            );
        }
    }
}
