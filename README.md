# Processo Seletivo Emulador

Este projeto é um emulador de um processo seletivo, desenvolvido utilizando o framework Spring Boot e Java. O objetivo é simular um sistema que gerencia as etapas de um processo seletivo, desde o recebimento de candidaturas até a qualificação e aprovação dos candidatos.

## Funcionalidades

O sistema possui as seguintes funcionalidades:

- Recebimento de candidaturas: os candidatos podem se cadastrar informando seu nome através do endpoint `/segundo/processo?nome=nomedocandidato`.

- Marcar entrevista: é possível marcar uma entrevista para um candidato específico através do endpoint `/segundo/entrevista/{id}`.

- Desqualificar candidato: um candidato pode ser desqualificado do processo através do endpoint `/segundo/desqualifica/{id}`.

- Verificar status do candidato: é possível verificar o status de um candidato através do endpoint `/segundo/verificastatus/{id}`.

- Aprovar candidato: um candidato pode ser aprovado no processo através do endpoint `/segundo/aprovar/{id}`.

- Obter lista de aprovados: é possível obter uma lista com os nomes dos candidatos aprovados através do endpoint `/segundo/aprovados`.

## Estrutura do projeto

O projeto possui a seguinte estrutura de diretórios:

- `src/main/java/com/project/processo_seletivo`: Contém os arquivos Java do projeto.
  - `ProcessoSeletivoEmulador.java`: Classe principal do projeto, responsável por iniciar a aplicação Spring Boot.
  - `Segundo.java`: Controlador REST que define os endpoints e suas respectivas operações.
  - `Candidato.java`: Classe que representa um candidato, contendo informações como nome e status.
  - `Status.java`: Enumeração que define os possíveis status de um candidato.

## Fontes de pesquisa: 
 - Além das aulas do bootcamp com o Yan, que desde já deixo meus sinceros agradecimentos, pesquisei nos sites:
 - https://medium.com/@andgomes/criando-um-projeto-maven-simples-a2ad88b25e78
 - https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/
 - https://www.alura.com.br/artigos/desenvolvendo-aplicacoes-javacode#:~:text=Para%20iniciar%20um%20novo%20projeto,v%C3%A1rias%20op%C3%A7%C3%B5es%20de%20projeto%20Java
 - https://docs.oracle.com/javase/8/docs/api/java/util/Map.html
 - https://docs.oracle.com/javase/8/docs/api/java/util/Map.html#method.summary
 - https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 - https://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html
 - https://www.baeldung.com/spring-response-entity
