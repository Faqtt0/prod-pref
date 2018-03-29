package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.enquetes.Pergunta;
import prefrest.com.prod.model.enquetes.PerguntaID;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, PerguntaID> {

    @Query(nativeQuery = true, value =  "SELECT COALESCE(MAX(CODIGO),0) + 1 AS CODIGO FROM PERGUNTA")
    Long incrementCodigo ();
}
