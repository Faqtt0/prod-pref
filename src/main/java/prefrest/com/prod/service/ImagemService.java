package prefrest.com.prod.service;

import liquibase.util.file.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.constants.Constants;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.ImagemCommonRepository;
import prefrest.com.prod.repository.ImagemRepository;
import prefrest.com.prod.repository.ImagemRepositoryPerson;
import prefrest.com.prod.repository.filter.ImagensFilter;
import prefrest.com.prod.util.UtilBase64Image;
import prefrest.com.prod.util.UtilConverterImagem;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImagemService {
    @Autowired
    ImagemRepository imagemRepository;
    @Autowired
    ImagemRepositoryPerson imagemRepositoryPerson;
    @Autowired
    ImagemCommonRepository imagemCommonRepository;

    private BufferedImage bufferedImage;


    public ResponseEntity<Imagens> salvarImagem(Imagens imagem, HttpServletResponse response, ApplicationEventPublisher publisher) {
        imagem.setUltAlt(LocalDateTime.now());
        Imagens imagemSalva = imagemRepository.save(imagem);
        publisher.publishEvent(new RecursoEvent(this, response, imagem.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(imagemSalva);
    }

    public ResponseEntity atualizarSalvarImagem(MultipartFile file, Long codigo) throws IOException, InvocationTargetException, IllegalAccessException {
        String diretorioImagem;
        Long imgMb = UtilConverterImagem.tamanhoImagem(file);
        //Criar um diretório acima a pasta imagens
        if (UtilConverterImagem.isDiretorioCriado(Constants.DIRETORIO_IMAGENS)) {
            if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("png")) {
                diretorioImagem = UtilConverterImagem.converteImagemPngToJpg(file, Constants.DIRETORIO_IMAGENS);
            } else {
                if (imgMb > 2) {
                    UtilConverterImagem.compressImagemColors(file, Constants.DIRETORIO_IMAGENS);
                } else {
                    UtilConverterImagem.salvaImagemJpg(file, Constants.DIRETORIO_IMAGENS );
                }
                diretorioImagem = Constants.DIRETORIO_IMAGENS + "\\" + file.getOriginalFilename();
            }

            if (diretorioImagem != null) {
                Imagens imagemSalva = imagemRepository.findOne(codigo);
                boolean isAtualizado = false;
                if (imagemSalva != null) {
                     isAtualizado = imagemCommonRepository.updateImagem(diretorioImagem, imagemSalva);
                } else {
                    return ResponseEntity.badRequest().build();
                }

                if (isAtualizado) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
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
        List<Imagens> imagens = imagemRepositoryPerson.getImagens(filter);
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
        File imagem = new File(imagemSalva.getImagem());
        imagem.delete();
        imagemRepository.delete(codigo);
    }
}
