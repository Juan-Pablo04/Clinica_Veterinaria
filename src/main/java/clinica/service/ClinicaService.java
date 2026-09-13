package clinica.service;

import clinica.model.Animal;
import clinica.model.Tutor;
import clinica.repository.AnimalRepository;
import clinica.repository.TutorRepository;
import clinica.service.exception.CpfDuplicadoException;
import clinica.service.exception.RecursoNaoEncontradoException;
import clinica.service.exception.TutorComAnimaisException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Camada de servico da atividade pratica: concentra as regras de negocio
 * do cadastro de tutores e animais e delimita as transacoes.
 */
@Service
public class ClinicaService {

    private final TutorRepository tutorRepository;
    private final AnimalRepository animalRepository;

    public ClinicaService(TutorRepository tutorRepository, AnimalRepository animalRepository) {
        this.tutorRepository = tutorRepository;
        this.animalRepository = animalRepository;
    }

    // --- Tutor ---------------------------------------------------------------

    @Transactional
    public Tutor cadastrarTutor(Tutor tutor) {
        if (tutorRepository.existsByCpf(tutor.getCpf())) {
            throw new CpfDuplicadoException(tutor.getCpf());
        }
        return tutorRepository.save(tutor);
    }

    @Transactional(readOnly = true)
    public Tutor buscarTutor(Long id) {
        return tutorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Tutor", id));
    }

    @Transactional(readOnly = true)
    public List<Tutor> listarTutores() {
        return tutorRepository.findAll();
    }

    /** Atualiza nome e telefone do tutor; identificador inexistente deve falhar. */
    @Transactional
    public Tutor atualizarTutor(Long id, String novoNome, String novoTelefone) {
        Tutor tutor = buscarTutor(id);
        tutor.setNome(novoNome);
        tutor.setTelefone(novoTelefone);
        return tutorRepository.save(tutor);
    }

    /** Remove o tutor apenas se nao houver animais vinculados. */
    @Transactional
    public void removerTutor(Long id) {
        Tutor tutor = buscarTutor(id);
        long quantidadeDeAnimais = animalRepository.countByTutorId(id);
        if (quantidadeDeAnimais > 0) {
            throw new TutorComAnimaisException(id, quantidadeDeAnimais);
        }
        tutorRepository.delete(tutor);
    }

    // --- Animal ----------------------------------------------------------------

    /** Cadastra o animal vinculando-o ao tutor informado. */
    @Transactional
    public Animal cadastrarAnimal(Long tutorId, Animal animal) {
        Tutor tutor = buscarTutor(tutorId);
        tutor.adicionarAnimal(animal);
        return animalRepository.save(animal);
    }

    @Transactional(readOnly = true)
    public List<Animal> listarAnimaisDoTutor(Long tutorId) {
        buscarTutor(tutorId);
        return animalRepository.findByTutorId(tutorId);
    }

    @Transactional(readOnly = true)
    public List<Animal> buscarAnimaisPorEspecie(String especie) {
        return animalRepository.findByEspecieIgnoreCase(especie);
    }

    @Transactional
    public void removerAnimal(Long animalId) {
        Animal animal = animalRepository.findById(animalId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Animal", animalId));
        animalRepository.delete(animal);
    }
}
