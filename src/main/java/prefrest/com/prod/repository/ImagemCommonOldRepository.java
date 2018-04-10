package prefrest.com.prod.repository;

import java.lang.reflect.InvocationTargetException;

public interface ImagemCommonOldRepository {
    boolean updateImagem(String caminhoImagem, Object imagem) throws InvocationTargetException, IllegalAccessException;
    //List getImagens (FiltroPadrao filter, Class<?> clazz);
}
