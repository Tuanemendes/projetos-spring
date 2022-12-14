package com.projeto.spring.campeonatobrasileiro.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name ="TIME")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idTime;
    @Column(length = 20)
    private String nome;
    @Column(length = 4)
    private String sigla;
    @Column(length = 2, name= "estado")
    private String uf;  

 

}
