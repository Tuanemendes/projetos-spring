package com.projeto.spring.campeonatobrasileiro.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.spring.campeonatobrasileiro.dto.ClassificacaoDto;
import com.projeto.spring.campeonatobrasileiro.dto.ClassificacaoTimeDto;
import com.projeto.spring.campeonatobrasileiro.dto.JogoDto;
import com.projeto.spring.campeonatobrasileiro.dto.JogoFinalizadoDto;
import com.projeto.spring.campeonatobrasileiro.entity.Jogo;
import com.projeto.spring.campeonatobrasileiro.entity.Time;
import com.projeto.spring.campeonatobrasileiro.repository.JogoRepository;
import com.projeto.spring.campeonatobrasileiro.repository.TimeRepository;

@Service
public class JogoService {
    
    @Autowired
    JogoRepository jogoRepository;

    @Autowired
    TimeService timeService;

  

    public void gerarJogos(LocalDateTime primeiraRodada) {
        final List<Time> times = timeService.findAll();
        List<Time> all1 = new ArrayList<>();
        List<Time> all2 = new ArrayList<>();
        all1.addAll(times);//.subList(0, times.size()/2));
        all2.addAll(times);//.subList(all1.size(), times.size()));

        jogoRepository.deleteAll();

        List<Jogo> jogos = new ArrayList<>();

        int t = times.size();
        int m = times.size() / 2;
        LocalDateTime dataJogo = primeiraRodada;
        Integer rodada = 0;
        for (int i = 0; i < t - 1; i++) {
            rodada = i + 1;
            for (int j = 0; j < m; j++) {
                //Teste para ajustar o mando de campo
                Time time1;
                Time time2;
                if (j % 2 == 1 || i % 2 == 1 && j == 0) {
                    time1 = times.get(t - j - 1);
                    time2 = times.get(j);
                } else {
                    time1 = times.get(j);
                    time2 = times.get(t - j - 1);
                }
                if (time1 == null) {
                    System.out.println("Time  1 null");
                }
                jogos.add(gerarJogo(dataJogo, rodada, time1, time2));
                dataJogo = dataJogo.plusDays(7);
            }
            //Gira os times no sentido horário, mantendo o primeiro no lugar
            times.add(1, times.remove(times.size() - 1));
        }

        jogos.forEach(jogo -> System.out.println(jogo));

        jogoRepository.saveAll(jogos);

        List<Jogo> jogos2 = new ArrayList<>();

        jogos.forEach(jogo -> {
            Time time1 = jogo.getTime2();
            Time time2 = jogo.getTime1();
            jogos2.add(gerarJogo(jogo.getDataDoJogo().plusDays(7 * jogos.size()), jogo.getRodada() + jogos.size(), time1, time2));
        });
        jogoRepository.saveAll(jogos2);
    }

    private Jogo gerarJogo(LocalDateTime dataJogo, Integer rodada, Time time1, Time time2) {
        
        Jogo jogo = new Jogo();
        jogo.setTime1(time1);
        jogo.setTime2(time2);
        jogo.setRodada(rodada);
        jogo.setDataDoJogo(dataJogo);
        jogo.setEncerrado(false);
        jogo.setGolsTime1(0);
        jogo.setGolsTime2(0);
        jogo.setPublicoPagante(0);
        return jogo;
        
    }

    private JogoDto toJogoDto(Jogo jogoEntity){
        JogoDto jogoDto = new JogoDto();
        jogoDto.setIdJogo(jogoEntity.getIdJogo());
        jogoDto.setDataDoJogo(jogoEntity.getDataDoJogo());
        jogoDto.setEncerrado(jogoEntity.getEncerrado());
        jogoDto.setGolsTime1(jogoEntity.getGolsTime1());
        jogoDto.setGolsTime2(jogoEntity.getGolsTime2());
        jogoDto.setPublicoPagante(jogoEntity.getPublicoPagante());
        jogoDto.setRodada(jogoEntity.getRodada());
        jogoDto.setTime1(timeService.toDto(jogoEntity.getTime1()));
        jogoDto.setTime2(timeService.toDto(jogoEntity.getTime2()));
        return jogoDto;
    }

    public List<JogoDto> listarJogos() {
        return jogoRepository.findAll().stream().map(jogoEntity -> toJogoDto(jogoEntity)).collect(Collectors.toList());
    }

    public JogoDto buscarJogo(Long id) {
        return toJogoDto(jogoRepository.findById(id).get());
    }

    public JogoDto finalizarJogo(Long id, JogoFinalizadoDto jogoFinalizadoDto) throws Exception{
       Optional<Jogo> optionalJogo = jogoRepository.findById(id);
       if(optionalJogo.isPresent()){
            final Jogo jogo = optionalJogo.get();
            jogo.setGolsTime1(jogoFinalizadoDto.getGolsTime1());
            jogo.setGolsTime2(jogoFinalizadoDto.getGolsTime2());
            jogo.setEncerrado(true);
            jogo.setPublicoPagante(jogoFinalizadoDto.getPublicoPagante());
            return toJogoDto(jogoRepository.save(jogo));
       }else{
        throw new Exception("Jogo não existe!");
       }
       
    }

     public ClassificacaoDto buscarClassificacao() {
        // quantidade de vitorias *3  +quantidade de Empates 
        ClassificacaoDto classificacaoDto = new ClassificacaoDto();
        final List<Time> times = timeService.findAll();
        times.forEach(time->{
            final List<Jogo> jogosMandate = jogoRepository.findByTime1AndEncerrado(time,true);
            final List<Jogo> jogosVisitante = jogoRepository.findByTime2AndEncerrado(time,true);
            AtomicReference<Integer> vitorias = new AtomicReference<>(0); 
            AtomicReference<Integer> empates =  new AtomicReference<>(0);
            AtomicReference<Integer> derrotas = new AtomicReference<>(0);
            AtomicReference<Integer> golsSofridos = new AtomicReference<>(0);
            AtomicReference<Integer> golsMarcados = new AtomicReference<>(0);

            jogosMandate .forEach(jogo -> {
                if(jogo.getGolsTime1()> jogo.getGolsTime2()){
                    vitorias.getAndSet(vitorias.get() +1);
                }else if(jogo.getGolsTime1() < jogo.getGolsTime2()){
                    derrotas.getAndSet(derrotas.get() +1);
                }else{
                    empates.getAndSet(derrotas.get() +1);
                }
                golsMarcados.set(golsMarcados.get() + jogo.getGolsTime1());
                golsSofridos.set(golsSofridos.get() + jogo.getGolsTime2());
            });
            jogosVisitante.forEach(jogo->{
                if(jogo.getGolsTime1()> jogo.getGolsTime2()){
                    vitorias.getAndSet(vitorias.get() +1);
                }else if(jogo.getGolsTime1() < jogo.getGolsTime2()){
                    derrotas.getAndSet(derrotas.get() +1);
                }else{
                    empates.getAndSet(derrotas.get() +1);
                }
                golsMarcados.set(golsMarcados.get() + jogo.getGolsTime1());
                golsSofridos.set(golsSofridos.get() + jogo.getGolsTime2());
            });

            ClassificacaoTimeDto classificacaoTimeDto = new ClassificacaoTimeDto();
            classificacaoTimeDto.setIdTime(time.getIdTime());
            classificacaoTimeDto.setTime(time.getNome());
            classificacaoTimeDto.setPontos((vitorias.get()*3) + empates.get());
            classificacaoTimeDto.setDerrotas(derrotas.get());
            classificacaoTimeDto.setEmpates(empates.get());
            classificacaoTimeDto.setVitorias(vitorias.get());
            classificacaoTimeDto.setGolsMarcados(golsMarcados.get());
            classificacaoTimeDto.setGolsSofridos(golsSofridos.get());
            classificacaoTimeDto.setJogos(derrotas.get() + empates.get() + vitorias.get());
            
            classificacaoDto.getTimes().add(classificacaoTimeDto);

        });
        Collections.sort(classificacaoDto.getTimes(),Collections.reverseOrder());
        int posicao = 1;
        for(ClassificacaoTimeDto time : classificacaoDto.getTimes()){
            time.setPosicao(posicao++);
        }
        return classificacaoDto;
    }
 




}
