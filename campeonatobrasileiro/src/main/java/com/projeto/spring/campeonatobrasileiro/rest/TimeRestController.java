package com.projeto.spring.campeonatobrasileiro.rest;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.spring.campeonatobrasileiro.dto.TimeDto;
import com.projeto.spring.campeonatobrasileiro.service.TimeService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value= "/times")
public class TimeRestController {
    
    @Autowired
    private TimeService timeService;

    
    @GetMapping
    public ResponseEntity<List<TimeDto>> getTimes(){
        return ResponseEntity.ok().body(timeService.listaTime());
    }
    @ApiOperation(value = "Obtém os dados de um time.")
    @GetMapping(value = "{id}")
    public ResponseEntity<TimeDto> getTime(@PathVariable Integer id){
        return ResponseEntity.ok().body(timeService.buscarTime(id));
    }

    @PostMapping
    public ResponseEntity<TimeDto> cadastroTime(@RequestBody TimeDto time) throws Exception{
       
        return ResponseEntity.ok().body( timeService.cadastrarTime(time));
    }

}
