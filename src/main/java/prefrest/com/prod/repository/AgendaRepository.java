package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prefrest.com.prod.model.Agenda;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findAllByUltAltAfterOrderByUltAlt(LocalDateTime dateTime);
}
