package prefrest.com.prod.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.PerguntaID;

public interface PerguntaRepository extends JpaRepository<Pergunta, PerguntaID> {
}
