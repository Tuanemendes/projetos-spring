package com.projeto.spring.campeonatobrasileiro.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Jogo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJogo;
    private Integer golsTime1;
    private Integer golsTime2;
    private Integer publicoPagante; 
    private LocalDateTime dataDoJogo;
    private Integer rodada;
    private Boolean encerrado;

    @ManyToOne
    @JoinColumn(name = "time1")
    private Time time1 ;
    @ManyToOne
    @JoinColumn(name = "time2")
    private Time time2 ;  
}
