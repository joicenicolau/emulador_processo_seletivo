package com.project.processo_seletivo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
// import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootApplication
public class ProcessoSeletivoEmulador {

    public static void main(String[] args) {
        SpringApplication.run(ProcessoSeletivoEmulador.class, args);
    }

    @RestController
    @RequestMapping("/api/v1/hiring")
    public class ContratacaoController {
        private Map<Integer, Candidato> candidatos;
        private int nextId;

        public ContratacaoController() {
            this.candidatos = new HashMap<>();
            this.nextId = 1;
        }

        @PostMapping("/start")
        public Integer iniciarProcesso(@RequestBody Map<String, String> requestBody) {
            String nome = requestBody.get("nome");
            Candidato candidato = new Candidato(nome);
            candidatos.put(nextId, candidato);
            return nextId++;
        }

        @PostMapping("/schedule")
        public ResponseEntity<String> marcarEntrevista(@RequestBody Map<String, Integer> requestBody) {
            int codCandidato = requestBody.get("codCandidato");
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                candidato.setStatus(Status.QUALIFICADO);
                return ResponseEntity.ok(candidato.getNome());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado");
        }

        @PostMapping("/disqualify")
        public ResponseEntity<String> desqualificarCandidato(@RequestBody Map<String, Integer> requestBody) {
            int codCandidato = requestBody.get("codCandidato");
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                candidato.setStatus(Status.DESQUALIFICADO);
                 return ResponseEntity.ok(candidato.getNome());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado");
        }

       @PostMapping("/approve")
        public ResponseEntity<Void> aprovarCandidato(@RequestBody Map<String, Integer> requestBody) {
            int codCandidato = requestBody.get("codCandidato");
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                candidato.setStatus(Status.APROVADO);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        @GetMapping("/status/candidate/{id}")
        public ResponseEntity<String> verificarStatusCandidato(@PathVariable("id") int codCandidato) {
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                return ResponseEntity.ok(candidato.getStatus().toString());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato não encontrado");
        }

         @GetMapping("/approved")
        public ResponseEntity<List<String>> obterAprovados() {
            List<String> aprovados = candidatos.values().stream()
                    .filter(candidato -> candidato.getStatus() == Status.APROVADO)
                    .map(Candidato::getNome)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(aprovados);
        }

        private Candidato getCandidato(int codCandidato) {
            if (candidatos.containsKey(codCandidato)) {
                return candidatos.get(codCandidato);
            }
            System.out.println("Candidato não encontrado");
            return null;
        }
    }

    public class Candidato {
        private String nome;
        private Status status;

        public Candidato(String nome) {
            this.nome = nome;
            this.status = Status.RECEBIDO;
        }

        public String getNome() {
            return nome;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }

    public enum Status {
        RECEBIDO,
        QUALIFICADO,
        APROVADO,
        DESQUALIFICADO
    }
}
