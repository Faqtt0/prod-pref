package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.constants.Constants;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.repository.FolderRespository;
import prefrest.com.prod.repository.ImagemRepository;
import prefrest.com.prod.util.UtilConverterImagem;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class FolderService {
    @Autowired
    FolderRespository folderRespository;

    @Autowired
    ImagemRepository imagemRepository;

    public ResponseEntity<Folder> salvarFolder(Folder folder, HttpServletResponse response, ApplicationEventPublisher publisher) {
        folder.setUltAlt(LocalDateTime.now());
        Folder folderSalvo = folderRespository.save(folder);
        publisher.publishEvent(new RecursoEvent(this, response, folderSalvo.getId()));
        return ResponseEntity.ok(folderSalvo);
    }

    public ResponseEntity atualizarFolder(Long codigo, Folder folder) {
        Folder folderSalvo = folderRespository.findOne(codigo);
        BeanUtils.copyProperties(folder, folderSalvo, "id");
        folderSalvo.setUltAlt(LocalDateTime.now());
        folderRespository.save(folderSalvo);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity atualizarSalvarImagem(Long codigo, MultipartFile file) {
        Folder folderSalvo = folderRespository.findOne(codigo);

        Map<String, Object> imagem = UtilConverterImagem.atualizarSalvarImagem(file,
                Constants.DIRETORIO_IMAGENS,
                Folder.class,
                folderSalvo.getCodImagem(),
                imagemRepository
        );

        folderSalvo.setUltAlt(LocalDateTime.now());

        if (folderSalvo.getCodImagem() == null) {
            folderSalvo.setCodImagem((Long) imagem.get("codImagem"));
        }

        folderRespository.save(folderSalvo);

        return ResponseEntity.status((Integer) imagem.get("status")).build();
    }

    public ResponseEntity deletarImagem(Long codigo) {
        Folder folderSalvo = folderRespository.findOne(codigo);
        imagemRepository.deleteById(folderSalvo.getCodImagem());
        return null;
    }
}
