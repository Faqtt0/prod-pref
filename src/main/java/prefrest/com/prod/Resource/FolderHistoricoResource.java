package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.FolderHistorico;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.FolderHistoricoService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/folderhist")
public class FolderHistoricoResource {

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    FolderHistoricoService service;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_FOLDER_HISTORICO') and #oauth2.hasScope('read')")
    public ResponseEntity<List<FolderHistorico>> retornaFoldersHist(FiltroPadrao filtroPadrao){
        return service.buscaFolderHistor(filtroPadrao);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_FOLDER_HISTORICO') and #oauth2.hasScope('write')")
    public ResponseEntity<FolderHistorico> salvaFolderHist(@Valid @RequestBody FolderHistorico folder, HttpServletResponse response){
        return service.salvarFolder(folder, response, publisher) ;
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_FOLDER_HISTORICO') and #oauth2.hasScope('write')")
    public ResponseEntity atualizarIFolderInfosHist(@PathVariable Long codigo,
                                                @Valid @RequestBody FolderHistorico folder){
        return service.atualizarFolder(codigo, folder);
    }

    @PutMapping("/{codigo}/imagem")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_FOLDER_HISTORICO') and #oauth2.hasScope('write')")
    public ResponseEntity atualizarSalvarImagemHist(@PathVariable Long codigo,
                                                @RequestPart MultipartFile file){
        return service.atualizarSalvarImagem(codigo, file);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_FOLDER_HISTORICO') and #oauth2.hasScope('write')")
    public ResponseEntity deletarImagemHist(@PathVariable Long codigo){
        return service.deletarImagem(codigo);
    }

}
