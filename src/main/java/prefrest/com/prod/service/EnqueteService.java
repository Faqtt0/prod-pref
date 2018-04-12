package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.exception.EnqueteNaoPermitidaException;
import prefrest.com.prod.model.Agenda;
import prefrest.com.prod.model.DeleteTables;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.model.enquetes.Resposta;
import prefrest.com.prod.repository.*;
import prefrest.com.prod.repository.filter.FiltroPadrao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnqueteService {

    @Autowired
    EnqueteRepository repository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    PerguntaPersonRepository perguntaPersonRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    EnquetePersonRepository repositoryPerson;

    @Autowired
    DeleteTablesRepository deleteTablesRepository;

    public Enquete salvar(Enquete enquete){
        return verificaSalvaEnquete(enquete);
    }

    private Enquete verificaSalvaEnquete(Enquete enquete) {
        enquete.setUltAlt(LocalDateTime.now());
        if (enquete.isAtivo()) {
            Integer enqueteAtivo = repositoryPerson.getAtivo();
            if (enqueteAtivo == 0){
                return repository.save(enquete);
            }
        } else {
            Integer dentroInterval = repositoryPerson.dentroDataIntervalo(enquete);
            if (dentroInterval == 0) {
                return repository.save(enquete);
            }
        }
        throw new EnqueteNaoPermitidaException();
    }


    public ResponseEntity<Enquete> carregaEnqueteParcial (Long id) {
        Enquete enquete = repository.findOne(id);
        if (enquete != null){
            return ResponseEntity.ok(repositoryPerson.carregadaDadosEnqueteParcial(enquete));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<Resposta>> buscaRespostasPergunta(Long id, Long codigo) {
        return ResponseEntity.ok(respostaRepository.carregarRespostas(id, codigo));
    }


    public ResponseEntity<Enquete> atualizaEnquete(Long id, Enquete enquete) {
        Enquete enqueteSalva = repository.findOne(id);
        enquete.setUltAlt(LocalDateTime.now());
        if (verificaEnqueteAtualizar(enquete)){
            BeanUtils.copyProperties(enquete, enqueteSalva, "id");
           return  ResponseEntity.ok(repository.save(enqueteSalva));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private boolean verificaEnqueteAtualizar(Enquete enquete) {
        if (enquete.isAtivo()){
            if (repositoryPerson.getAtivo()  == 0) {
                return true;
            }
        } else {
            if (repositoryPerson.dentroDataIntervalo(enquete) == 0) {
                return true;
            }
        }

        return false;
    }

    public boolean deletarEnquete(Long codigo) {
        if (repositoryPerson.isEditavel(codigo)){
            respostaRepository.removerRespostasAll(codigo);
            perguntaPersonRepository.removerByEnquete(codigo);
            repository.delete(codigo);
            Map<String, String> params = new HashMap<>();
            params.put("ID", String.valueOf(codigo));
            deleteTablesRepository.save(new DeleteTables(Enquete.class, LocalDateTime.now(), params));
            return true;
        }
        return false;
    }

    public ResponseEntity<List<Enquete>> getAllenquetes(FiltroPadrao filtroPadrao) {
        if (filtroPadrao.getAlteracao() != null){
            List<Enquete> enquetes = repository.findAllByUltAltAfterOrderByUltAlt(LocalDateTime.parse(filtroPadrao.getAlteracao()));
            enqueteCompleta(enquetes);
            return ResponseEntity.ok().body(enquetes);
        }
        List<Enquete> allEnquetes = repository.findAll();
        enqueteCompleta(allEnquetes);
        return ResponseEntity.ok().body(allEnquetes);
    }

    private void enqueteCompleta(List<Enquete> enquetes) {
        enquetes.forEach(enquete -> {
            enquete.setPerguntas(perguntaRepository.findAllByCodEnqueteOrderByCodigo(enquete.getId()));
            enquete.getPerguntas().forEach(pergunta -> pergunta.setRespostas(respostaRepository.carregarRespostas(pergunta.getCodEnquete(), pergunta.getCodigo())));
        });
    }
}
