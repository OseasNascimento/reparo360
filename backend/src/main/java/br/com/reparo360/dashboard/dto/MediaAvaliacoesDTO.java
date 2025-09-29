package br.com.reparo360.dashboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Média de avaliações recebidas")
public class MediaAvaliacoesDTO {

    @Schema(description = "Valor da média de avaliações (0.0 a 5.0)", example = "4.7")
    private double mediaAvaliacoes;

    public MediaAvaliacoesDTO(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }

    public double getMediaAvaliacoes() {
        return mediaAvaliacoes;
    }

    public void setMediaAvaliacoes(double mediaAvaliacoes) {
        this.mediaAvaliacoes = mediaAvaliacoes;
    }
}
