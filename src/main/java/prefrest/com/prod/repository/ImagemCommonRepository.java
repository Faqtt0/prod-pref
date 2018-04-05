package prefrest.com.prod.repository;

import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.filter.ImagensFilter;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface ImagemCommonRepository {
    boolean updateImagem(String caminhoImagem, Object imagem) throws InvocationTargetException, IllegalAccessException;
}
