package prefrest.com.prod.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.constants.Constants;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.DeleteTables;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.model.Imagem;
import prefrest.com.prod.repository.DeleteTablesRepository;
import prefrest.com.prod.repository.FolderRespository;
import prefrest.com.prod.repository.ImagemRepository;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.util.UtilBase64Image;
import prefrest.com.prod.util.UtilConverterImagem;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FolderService {
    @Autowired
    FolderRespository folderRespository;

    @Autowired
    ImagemRepository imagemRepository;

    @Autowired
    DeleteTablesRepository deleteTablesRepository;

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
        folderRespository.delete(codigo);
        deleteTablesRepository.save(new DeleteTables(Folder.class, folderSalvo.getId(), LocalDateTime.now()));
        if (folderSalvo.getCodImagem() != null) {
            Imagem imagemSalva = imagemRepository.findById(folderSalvo.getCodImagem());
            UtilConverterImagem.deletarImagemHD(imagemSalva.getCaminho());
            imagemRepository.deleteById(folderSalvo.getCodImagem());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<List<Folder>> buscarFolders(FiltroPadrao filtroPadrao) {
        List<Folder> folders = new ArrayList<>();
        if (filtroPadrao.getAlteracao() != null) {
            LocalDateTime dtUltAlt = LocalDateTime.parse(filtroPadrao.getAlteracao());
            List<Folder> foldersSalvos = folderRespository.findAllByUltAltAfterOrderByUltAlt(dtUltAlt);
            foldersSalvos.forEach(folder -> recuperaImagens(folder));
            return ResponseEntity.ok().body(foldersSalvos);
        } else {
            folders = folderRespository.findAll();
            folders.forEach(folder -> recuperaImagens(folder));
        }
        return ResponseEntity.ok().body(folders);
    }

    private void recuperaImagens(Folder folder) {
        if (folder.getCodImagem() != null) {
            folder.setImagem(imagemRepository.findById(folder.getCodImagem()));
            folder.getImagem().setImagemBase64(UtilBase64Image.encoder(folder.getImagem().getCaminho()));
        }
    }
}
