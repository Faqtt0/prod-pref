package prefrest.com.prod.repository;

import prefrest.com.prod.repository.filter.FiltroPadrao;

import java.util.List;

public interface ImagemCommomRepository {
    Long recuperaCodImagem(Long codigo, Class<?> clazz);
    boolean updateCodImagem(Long codigo, Long codigoImagem, Class<?> clazz);
}
