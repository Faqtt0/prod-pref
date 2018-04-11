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
import prefrest.com.prod.model.*;
import prefrest.com.prod.repository.AgendaRepository;
import prefrest.com.prod.repository.DeleteTablesRepository;
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
public class AgendaService {
    @Autowired
    AgendaRepository agendaRepository;

    @Autowired
    ImagemRepository imagemRepository;

    @Autowired
    DeleteTablesRepository deleteTablesRepository;

    public ResponseEntity<Agenda> salvarAgenda(Agenda agenda, HttpServletResponse response, ApplicationEventPublisher publisher) {
        agenda.setUltAlt(LocalDateTime.now());
        Agenda agendaSalva = agendaRepository.save(agenda);
        publisher.publishEvent(new RecursoEvent(this, response, agendaSalva.getId()));
        return ResponseEntity.ok(agendaSalva);
    }

    public ResponseEntity atualizarAgenda(Long codigo, Agenda agenda) {
        Agenda agendaSalva = agendaRepository.findOne(codigo);
        BeanUtils.copyProperties(agenda, agendaSalva, "id");
        agendaSalva.setUltAlt(LocalDateTime.now());
        agendaRepository.save(agendaSalva);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity atualizarSalvarImagem(Long codigo, MultipartFile file) {
        Agenda agenda = agendaRepository.findOne(codigo);

        Map<String, Object> imagem = UtilConverterImagem.atualizarSalvarImagem(file,
                Constants.DIRETORIO_IMAGENS_AGENDA,
                Folder.class,
                agenda.getCodImagem(),
                imagemRepository
        );

        agenda.setUltAlt(LocalDateTime.now());

        if (agenda.getCodImagem() == null) {
            agenda.setCodImagem((Long) imagem.get("codImagem"));
        }

        agendaRepository.save(agenda);
        return ResponseEntity.status((Integer) imagem.get("status")).build();
    }

    public ResponseEntity deletarImagem(Long codigo) {
        Agenda agendaSalva = agendaRepository.findOne(codigo);
        agendaRepository.delete(codigo);
        deleteTablesRepository.save(new DeleteTables(Agenda.class, agendaSalva.getId(), LocalDateTime.now()));
        if (agendaSalva.getCodImagem() != null) {
            Imagem imagemSalva = imagemRepository.findById(agendaSalva.getCodImagem());
            UtilConverterImagem.deletarImagemHD(imagemSalva.getCaminho());
            imagemRepository.deleteById(agendaSalva.getCodImagem());
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<List<Agenda>> buscaAgenda(FiltroPadrao filtroPadrao) {
        List<Agenda> agendasAll = new ArrayList<>();
        if (filtroPadrao.getAlteracao() != null) {
            LocalDateTime dtUltAlt = LocalDateTime.parse(filtroPadrao.getAlteracao());
            List<Agenda> agendaSalvas = agendaRepository.findAllByUltAltAfterOrderByUltAlt(dtUltAlt);
            agendaSalvas.forEach(agenda -> recuperaImagens(agenda));
            return ResponseEntity.ok().body(agendaSalvas);
        } else {
            agendasAll = agendaRepository.findAll();
            agendasAll.forEach(agenda -> recuperaImagens(agenda));
        }
        return ResponseEntity.ok().body(agendasAll);
    }

    private void recuperaImagens(Agenda folder) {
        if (folder.getCodImagem() != null) {
            folder.setImagem(imagemRepository.findById(folder.getCodImagem()));
            folder.getImagem().setImagemBase64(UtilBase64Image.encoder(folder.getImagem().getCaminho()));
        }
    }
}
