package com.projeto.spring.campeonatobrasileiro.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.campeonatobrasileiro.dto.TimeDto;
import com.projeto.spring.campeonatobrasileiro.entity.Time;
import com.projeto.spring.campeonatobrasileiro.repository.TimeRepository;

@Service
public class TimeService {

    @Autowired
    private TimeRepository timeRepository;


    public TimeDto cadastrarTime(TimeDto time) throws Exception{
        Time timeEntity = toEntity(time);
        if(time.getIdTime()== null){
            timeEntity = timeRepository.save(timeEntity);
            return toDto(timeEntity);
        }else{
            throw new Exception("Time já existe");
        }
        
    }

    private Time toEntity(TimeDto time) {
        Time timeEntity = new Time();
        timeEntity.setIdTime(time.getIdTime());
        timeEntity.setNome(time.getNome());
        timeEntity.setSigla(time.getSigla());
        timeEntity.setUf(time.getUf());
        return timeEntity;
    }
    public TimeDto toDto(Time timeEntity) {
        TimeDto timeDto = new TimeDto();
        timeDto.setIdTime(timeEntity.getIdTime());
        timeDto.setNome(timeEntity.getNome());
        timeDto.setSigla(timeEntity.getSigla());
        timeDto.setUf(timeEntity.getUf());
        return timeDto;
    }

    public List<TimeDto> listaTime(){
        return timeRepository.findAll().stream().map(entity -> toDto(entity)).collect(Collectors.toList());
    }

    public TimeDto buscarTime(Integer id){
        return toDto(timeRepository.findById(id).get());
    }

    public List<Time> findAll() {
        return timeRepository.findAll();
    }

   
}
