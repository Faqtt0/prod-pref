package prefrest.com.prod.repository;

import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.repository.filter.EnqueteFilter;

import java.util.List;

public interface EnquetePersonRepository  {
    List<Enquete> filtrarEnquetes(EnqueteFilter enqueteFilter);
    Integer getAtivo();
    Integer dentroDataIntervalo (Enquete enquete);
    Enquete carregadaDadosEnqueteParcial (Enquete enquete);
    boolean isEditavel (Long idEnquete);

}
