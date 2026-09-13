package clinica.service.exception;

public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super("%s nao encontrado para o identificador %d".formatted(recurso, id));
    }
}
