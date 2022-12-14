package com.projeto.spring.campeonatobrasileiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.spring.campeonatobrasileiro.entity.Time;
@Repository
public interface TimeRepository extends JpaRepository<Time, Integer>  {
    
}
