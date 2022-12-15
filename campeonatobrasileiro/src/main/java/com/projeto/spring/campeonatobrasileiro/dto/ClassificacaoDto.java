package com.projeto.spring.campeonatobrasileiro.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClassificacaoDto {
    
    private List<ClassificacaoTimeDto> times = new ArrayList<>();
}
