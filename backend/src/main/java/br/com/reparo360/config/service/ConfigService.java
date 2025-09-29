package br.com.reparo360.config.service;

import java.math.BigDecimal;

public interface ConfigService {
    /**
     * Lê um parâmetro decimal salvo como String. Se não existir ou estiver inválido, retorna fallback.
     */
    BigDecimal getDecimal(String key, BigDecimal fallback);

    /**
     * Grava/atualiza um parâmetro decimal (salvo como String).
     */
    void setDecimal(String key, BigDecimal value);
}
