package com.projeto.spring.campeonatobrasileiro.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JogoDto {
    private Long idJogo;
    private Integer golsTime1;
    private Integer golsTime2;
    private Integer publicoPagante; 
    private LocalDateTime dataDoJogo;
    private Integer rodada;
    private Boolean encerrado;
    private TimeDto time1;
    private TimeDto time2;
}
