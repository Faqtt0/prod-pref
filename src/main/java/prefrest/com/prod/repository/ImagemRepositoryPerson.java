package prefrest.com.prod.repository;

import prefrest.com.prod.model.Imagens;

public interface ImagemRepositoryPerson {
    boolean updateImagem(String caminhoImagem, Imagens imagem);
    boolean updateImagemInfos(Imagens imagem);

}
