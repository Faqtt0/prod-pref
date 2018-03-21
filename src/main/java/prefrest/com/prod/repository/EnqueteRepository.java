package prefrest.com.prod.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import prefrest.com.prod.model.enquetes.Enquetes;

import java.util.List;

public interface EnqueteRepository extends JpaRepository<Enquetes, Long> {

    @Query(nativeQuery = true, value = "select * from enquete limit 100")
    List<Enquetes> findByEnquetesLimit();


}
