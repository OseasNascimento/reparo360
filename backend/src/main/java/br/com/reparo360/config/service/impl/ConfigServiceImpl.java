package br.com.reparo360.config.service.impl;

import br.com.reparo360.config.entity.ParametroConfig;
import br.com.reparo360.config.repository.ParametroConfigRepository;
import br.com.reparo360.config.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class ConfigServiceImpl implements ConfigService {

    private final ParametroConfigRepository repo;

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getDecimal(String key, BigDecimal fallback) {
        return repo.findByChave(key)
                .map(ParametroConfig::getValorNumeric)
                .orElse(fallback);
    }

    @Override
    public void setDecimal(String key, BigDecimal value) {
        ParametroConfig p = repo.findByChave(key)
                .orElseGet(() -> ParametroConfig.builder().chave(key).build());
        p.setValorNumeric(value != null ? value : BigDecimal.ZERO);
        repo.save(p);
    }
}
