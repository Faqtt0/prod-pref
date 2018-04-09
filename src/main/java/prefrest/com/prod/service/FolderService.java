package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.repository.FolderRespository;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Service
public class FolderService {
    @Autowired
    FolderRespository folderRespository;

    public ResponseEntity<Folder> salvarFolder(Folder folder, HttpServletResponse response, ApplicationEventPublisher publisher) {
        folder.setUltAlt(LocalDateTime.now());
        Folder folderSalvo = folderRespository.save(folder);
        publisher.publishEvent(new RecursoEvent(this, response, folderSalvo.getId()));
        return ResponseEntity.ok(folderSalvo);
    }

    public ResponseEntity atualizarFolder(Long codigo, Folder folder) {
        Folder folderSalvo = folderRespository.findOne(codigo);
        BeanUtils.copyProperties(folder, folderSalvo, "id");
        Folder folderAtualizado = folderRespository.save(folderSalvo);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity atualizarSalvarImagem(Long codigo, MultipartFile file) {

        return null;
    }
}
