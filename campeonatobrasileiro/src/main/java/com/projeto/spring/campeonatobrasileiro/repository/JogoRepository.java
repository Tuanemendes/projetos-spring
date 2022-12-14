package com.projeto.spring.campeonatobrasileiro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.spring.campeonatobrasileiro.entity.Jogo;
import com.projeto.spring.campeonatobrasileiro.entity.Time;

@Repository
public interface JogoRepository extends JpaRepository<Jogo,Long>{

    // select * from  jogo where time1 = :time1 and encerrado = :encerrado
    List<Jogo> findByTime1AndEcerrado(Time time1, Boolean encerrado);

    List<Jogo> findByTime2AndEcerrado(Time time2, Boolean encerrado);
    
}
