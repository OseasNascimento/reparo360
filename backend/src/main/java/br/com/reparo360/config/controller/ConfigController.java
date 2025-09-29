package br.com.reparo360.config.controller;

import br.com.reparo360.config.dto.ValorDto;
import br.com.reparo360.config.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/config")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;

    private static final String KEY_CUSTO_KM = "CUSTO_POR_KM";
    private static final BigDecimal DEFAULT_CUSTO_KM = new BigDecimal("1.50");

    @GetMapping("/custo-km")
    public ResponseEntity<ValorDto> getCustoKm() {
        BigDecimal valor = configService.getDecimal(KEY_CUSTO_KM, DEFAULT_CUSTO_KM);
        return ResponseEntity.ok(new ValorDto(valor));
    }

    @PutMapping("/custo-km")
    public ResponseEntity<ValorDto> setCustoKm(@RequestBody ValorDto body) {
        BigDecimal novo = body.getValor() != null ? body.getValor() : DEFAULT_CUSTO_KM;
        configService.setDecimal(KEY_CUSTO_KM, novo);
        return ResponseEntity.ok(new ValorDto(novo));
    }
}
