# ADS1253 — Aula 8 — Atividade Prática de Laboratório

Sistema de Cadastro de uma Clínica Veterinária — CRUD com Spring Data JPA.

## Requisitos do ambiente

- JDK 21 (ou superior)
- Maven 3.9+
- Banco de dados H2 em memória (já configurado; nenhuma instalação necessária)

## Execução

    mvn test          # executa a suíte de testes
    mvn spring-boot:run   # inicia a aplicação (console H2 em /h2-console)

## Roteiro

1. **ETAPA 1** — Importar o projeto na IDE e executar `mvn test`. Todos os
   casos devem falhar ou não compilar: esse é o ponto de partida esperado.
2. **ETAPA 2** — Mapear a entidade `Animal` (`model/Animal.java`), usando
   `Tutor` como referência.
3. **ETAPA 3** — Declarar a interface `AnimalRepository`
   (`repository/AnimalRepository.java`), com as consultas derivadas indicadas.
4. **ETAPA 4** — Implementar as operações pendentes de `ClinicaService`.
5. **ETAPA 5** — Escrever os testes 5, 6 e 7 de `ClinicaServiceTest`.
6. **ETAPA 6** — Executar `mvn test` até que os sete casos passem e refatorar
   o código mantendo a suíte verde.

## Regras de negócio

| # | Regra |
|---|-------|
| 1 | O CPF do tutor é único; cadastro duplicado lança `CpfDuplicadoException`. |
| 2 | Busca por identificador inexistente lança `RecursoNaoEncontradoException`. |
| 3 | A atualização preserva o identificador do registro. |
| 4 | Um tutor com animais vinculados não pode ser removido (`TutorComAnimaisException`, cuja mensagem contém o identificador do tutor). |
| 5 | Todo animal pertence obrigatoriamente a um tutor existente. |
| 6 | A listagem de animais de um tutor retorna apenas os seus animais. |
| 7 | A busca por espécie ignora maiúsculas e minúsculas. |

## Entrega

Projeto completo (código-fonte e histórico de commits) pelo AVA, conforme as
orientações do material da aula.
