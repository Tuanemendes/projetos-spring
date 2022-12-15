package com.projeto.spring.campeonatobrasileiro.rest;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.spring.campeonatobrasileiro.dto.ClassificacaoDto;
import com.projeto.spring.campeonatobrasileiro.dto.JogoDto;
import com.projeto.spring.campeonatobrasileiro.dto.JogoFinalizadoDto;

import com.projeto.spring.campeonatobrasileiro.service.JogoService;

@RestController
@RequestMapping(value= "/jogos")
public class jogoRestcontroller {

    @Autowired
    JogoService jogoService;

    @GetMapping
    public ResponseEntity<List<JogoDto>> listarJogos(){
        return ResponseEntity.ok().body(jogoService.listarJogos());

    }

    @GetMapping(value = "/jogo/{id}")
    public ResponseEntity<JogoDto> buscarJogo(@PathVariable Long id){

        return ResponseEntity.ok().body(jogoService.buscarJogo(id));
    }

    @PostMapping(value = "/gerar-jogos")
    public ResponseEntity<Void> gerarJogos(){
        jogoService.gerarJogos(LocalDateTime.now());
        return ResponseEntity.ok().build();
    }
    @PostMapping(value = "/finalizar/{id}")
    public ResponseEntity<JogoDto> finalizarJogo(@PathVariable Long id , @RequestBody JogoFinalizadoDto jogofinalizadoDto) throws Exception{
        return ResponseEntity.ok().body(jogoService.finalizarJogo(id, jogofinalizadoDto));
    }
    @GetMapping(value = "/classificacao")
    public ResponseEntity<ClassificacaoDto> classificar(){
        return ResponseEntity.ok().body(jogoService.buscarClassificacao());
    } 
    
}
