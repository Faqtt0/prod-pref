package prefrest.com.prod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import prefrest.com.prod.model.Agenda;
import prefrest.com.prod.model.Folder;
import prefrest.com.prod.model.FolderHistorico;
import prefrest.com.prod.model.Imagem;
import prefrest.com.prod.model.empresas.Segmento;
import prefrest.com.prod.model.enquetes.Enquete;
import prefrest.com.prod.repository.*;
import prefrest.com.prod.repository.filter.FiltroPadrao;
import prefrest.com.prod.util.UtilBase64Image;
import prefrest.com.prod.util.UtilPasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CargaInicialService {
    @Autowired
    EnqueteRepository enqueteRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    SegmentoRepository segmentoRepository;

    @Autowired
    EmpresaRespository empresaRespository;

    @Autowired
    ImagemRepository imagemRepository;

    @Autowired
    FolderRespository folderRespository;

    @Autowired
    FolderHistoricoRespository folderHistoricoRespository;

    @Autowired
    AgendaRepository agendaRepository;

    public ResponseEntity<Map<String, Object>> getAllInfos(FiltroPadrao filtroPadrao) {
        Map<String, Object> dadosTabelas = new HashMap<>();
        if (filtroPadrao.getAlteracao() != null){
            LocalDateTime dataUltAlt = LocalDateTime.parse(filtroPadrao.getAlteracao());
            recuperaTabelas().forEach(s -> {
                if (s.equals("Enquete")){
                    recuperaDadosEnquete(dadosTabelas, dataUltAlt);
                } else if (s.equals("Segmento")) {
                    recuperaDadosSegmento(dadosTabelas, dataUltAlt);
                } else if (s.equals("Folder")){
                    recuperaDadosFolder(dadosTabelas, dataUltAlt);
                } else if (s.equals("FolderHistorico")){
                    recuperaDadosFolderHist(dadosTabelas, dataUltAlt);
                } else {
                    recuperaDadosAgenda(dadosTabelas, dataUltAlt);
                }
            });
            return ResponseEntity.ok().body(dadosTabelas);
        }
        return ResponseEntity.ok().build();
    }

    private void recuperaDadosAgenda(Map<String, Object> dadosTabelas, LocalDateTime dataUltAlt) {
        List<Agenda> agenda = agendaRepository.findAllByUltAltAfterOrderByUltAlt(dataUltAlt);
        agenda.forEach(a -> {
            Imagem imagemAgenda = imagemRepository.findById(a.getCodImagem());
            imagemAgenda.setImagemBase64(UtilBase64Image.encoder(imagemAgenda.getCaminho()));
            a.setImagem(imagemAgenda);
        });
        dadosTabelas.put("AGENDA", agenda);
    }

    private void recuperaDadosFolderHist(Map<String, Object> dadosTabelas, LocalDateTime dataUltAlt) {
        List<FolderHistorico> folderHistorico = folderHistoricoRespository.findAllByUltAltAfterOrderByUltAlt(dataUltAlt);
        folderHistorico.forEach(folderHist ->{
            Imagem imagemHist = imagemRepository.findById(folderHist.getCodImagem());
            imagemHist.setImagemBase64(UtilBase64Image.encoder(imagemHist.getCaminho()));
            folderHist.setImagem(imagemHist);
        } );
        dadosTabelas.put("FOLDERHISTORICO", folderHistorico);
    }

    private void recuperaDadosFolder(Map<String, Object> dadosTabelas, LocalDateTime dataUltAlt) {
        List<Folder> folder = folderRespository.findAllByUltAltAfterOrderByUltAlt(dataUltAlt);
        folder.forEach(f -> {
            Imagem imagemFolder = imagemRepository.findById(f.getCodImagem());
            imagemFolder.setImagemBase64(UtilBase64Image.encoder(imagemFolder.getCaminho()));
            f.setImagem(imagemFolder);
        });
        dadosTabelas.put("FOLDER", folder);
    }

    private void recuperaDadosSegmento(Map<String, Object> dadosTabelas, LocalDateTime dataUltAlt) {
        List<Segmento> segmentos = segmentoRepository.findAllByUltAltAfterOrderByUltAlt(dataUltAlt);
        segmentos.forEach(segmento -> {
            segmento.setEmpresas(empresaRespository.findAllByCodSegmento(segmento.getId()));
        });
        dadosTabelas.put("SEGMENTO", segmentos);
    }

    private void recuperaDadosEnquete(Map<String, Object> dadosTabelas, LocalDateTime dataUltAlt) {
        List<Enquete> enquetes = enqueteRepository.findAllByUltAltAfterOrderByUltAlt(dataUltAlt);
        enquetes.forEach(enquete -> {
            enquete.setPerguntas(perguntaRepository.findAllByCodEnqueteOrderByCodigo(enquete.getId()));
            enquete.getPerguntas().forEach(pergunta -> pergunta.setRespostas(respostaRepository.carregarRespostas(pergunta.getCodEnquete(), pergunta.getCodigo())));
        });
        dadosTabelas.put("ENQUETE", enquetes);
    }

    private List<String> recuperaTabelas (){
        List<String> classes = new ArrayList<>();
        classes.add("Enquete");
        classes.add("Segmento");
        classes.add("Folder");
        classes.add("FolderHistorico");
        classes.add("Agenda");
        return classes;
    }
}
