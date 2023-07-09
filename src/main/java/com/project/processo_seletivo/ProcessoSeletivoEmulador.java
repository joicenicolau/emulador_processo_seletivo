package com.project.processo_seletivo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class ProcessoSeletivoEmulador {

    public static void main(String[] args) {
        SpringApplication.run(ProcessoSeletivoEmulador.class, args);
    }

    @RestController
    @RequestMapping("/segundo")
    public class Segundo {
        private Map<Integer, Candidato> candidatos;
        private int nextId;
        private Scanner scanner;

        public Segundo() {
            this.candidatos = new HashMap<>();
            this.nextId = 1;
            this.scanner = new Scanner(System.in);
        }

        @PostMapping("/processo")
        public int iniciarProcesso() {
            System.out.print("Digite o nome do candidato: ");
            String nome = scanner.nextLine();
            Candidato candidato = new Candidato(nome);
            candidatos.put(nextId, candidato);
            return nextId++;
        }

        @PostMapping("/entrevista/{id}")
        public String marcarEntrevista(@PathVariable("id") int codCandidato) {
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                candidato.setStatus(Status.QUALIFICADO);
                return candidato.getNome();
            }
            return "Candidato não encontrado";
        }

        @PostMapping("/desqualifica/{id}")
        public String desqualificarCandidato(@PathVariable("id") int codCandidato) {
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                candidato.setStatus(Status.DESQUALIFICADO);
                return candidato.getNome();
            }
            return "Candidato desqualificado";
        }

        @GetMapping("/verificastatus/{id}")
        public String verificarStatusCandidato(@PathVariable("id") int codCandidato) {
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                return candidato.getStatus().toString();
            }
            return "Candidato não encontrado";
        }

        @PostMapping("/aprovar/{id}")
        public void aprovarCandidato(@PathVariable("id") int codCandidato) {
            Candidato candidato = getCandidato(codCandidato);
            if (candidato != null) {
                candidato.setStatus(Status.APROVADO);
            }
        }

        @GetMapping("/aprovados")
        public List<String> obterAprovados() {
            return candidatos.values().stream()
                    .filter(candidato -> candidato.getStatus() == Status.APROVADO)
                    .map(Candidato::getNome)
                    .collect(Collectors.toList());
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
