package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.enquetes.Perguntas;
import prefrest.com.prod.model.enquetes.PerguntasID;

public interface PerguntaRepository extends JpaRepository<Perguntas, PerguntasID>{

}
