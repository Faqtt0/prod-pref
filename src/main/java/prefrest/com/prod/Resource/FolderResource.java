package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.service.FolderService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/folder")
public class FolderResource {
    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    FolderService service;

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_FOLDER') and #oauth2.hasScope('read')")
    public ResponseEntity<List<Folder>> retornaFolders(FiltroPadrao filtroPadrao){
        return service.buscarFolders(filtroPadrao);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_FOLDER') and #oauth2.hasScope('write')")
    public ResponseEntity<Folder> salvaFolder(@Valid @RequestBody Folder folder, HttpServletResponse response){
        return service.salvarFolder(folder, response, publisher) ;
    }

    @PutMapping("/order")
    public ResponseEntity atualizaOrdemListaImagens(@RequestBody List<Folder> folders){
        return service.atualizarOrdemImagens(folders);
    }

    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_FOLDER') and #oauth2.hasScope('write')")
    public ResponseEntity atualizarIFolderInfos(@PathVariable Long codigo,
                                                @Valid @RequestBody Folder folder){
        return service.atualizarFolder(codigo, folder);
    }

    @PutMapping("/{codigo}/imagem")
    @PreAuthorize("hasAuthority('ROLE_ATUALIZAR_FOLDER') and #oauth2.hasScope('write')")
    public ResponseEntity atualizarSalvarImagem(@PathVariable Long codigo,
                                                @RequestPart MultipartFile file){
        return service.atualizarSalvarImagem(codigo, file);
    }

    @DeleteMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_DELETAR_FOLDER') and #oauth2.hasScope('write')")
    public ResponseEntity deletarImagem(@PathVariable Long codigo){
        return service.deletarImagem(codigo);
    }

}
