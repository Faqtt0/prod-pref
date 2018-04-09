package prefrest.com.prod.repository;

import java.util.List;

public interface ImagemCommomRepository {
    Long recuperaCodImagem(Long codigo, Class<?> clazz);
    boolean updateCodImagem(Long codigo, Long codigoImagem, Class<?> clazz);
}
