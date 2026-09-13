package clinica.service.exception;

public class TutorComAnimaisException extends RuntimeException {

    public TutorComAnimaisException(Long tutorId, long quantidade) {
        super("O tutor %d possui %d animal(is) vinculado(s) e nao pode ser removido"
                .formatted(tutorId, quantidade));
    }
}
