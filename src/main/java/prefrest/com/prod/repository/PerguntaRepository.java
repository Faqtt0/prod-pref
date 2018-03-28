package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.PerguntaID;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, PerguntaID> {

}
