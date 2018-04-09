package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prefrest.com.prod.repository.ImagemCommonOldRepository;
import prefrest.com.prod.repository.ImagemRepository;
import prefrest.com.prod.repository.ImagemRepositoryPerson;

@Service
public class ImagemOldService {
    @Autowired
    ImagemRepository imagemRepository;
    @Autowired
    ImagemRepositoryPerson imagemRepositoryPerson;
    @Autowired
    ImagemCommonOldRepository imagemCommonOldRepository;

    /*
    public ResponseEntity<Imagens> salvarImagem(Imagens imagem, HttpServletResponse response, ApplicationEventPublisher publisher) {
        imagem.setUltAlt(LocalDateTime.now());
        Imagens imagemSalva = imagemRepository.save(imagem);
        publisher.publishEvent(new RecursoEvent(this, response, imagem.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(imagemSalva);
    }

    public ResponseEntity atualizarSalvarImagem(MultipartFile file, Long codigo){
        Imagens imagemSalva = imagemRepository.findOne(codigo);
        return UtilConverterImagem.atualizarSalvarImagem(file, Constants.DIRETORIO_IMAGENS, imagemSalva, imagemCommonOldRepository);
    }

    public ResponseEntity atualizarImagemInfos(Long codigo, Imagens imagens) {
        Imagens imagemSalva = imagemRepository.findOne(codigo);
        BeanUtils.copyProperties(imagens, imagemSalva, "id");
        if (imagemRepositoryPerson.updateImagemInfos(imagemSalva)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<List<Imagens>> getImagens(ImagensFilter filter) {
        List<Imagens> imagens = (List<Imagens>) imagemCommonOldRepository.getImagens(filter, Imagens.class);

        //List<Imagens> imagens = imagemRepositoryPerson.getImagens(filter);
        if (imagens.size() > 0) {
            imagens.forEach(item -> {
                if (item.getImagem() != null){
                    item.setImagemBase64(UtilBase64Image.encoder(item.getImagem()));
                }
            });
        }
        return ResponseEntity.ok().body(imagens);
    }

    public void deletarImagem(Long codigo)  {
        Imagens imagemSalva = imagemRepository.findOne(codigo);
        if (imagemSalva != null){
            UtilConverterImagem.deletarImagemHD(imagemSalva.getImagem());
            imagemRepository.delete(codigo);
        } else {
            throw new ImagemNaoEncontradaException();
        }

    }*/
}
