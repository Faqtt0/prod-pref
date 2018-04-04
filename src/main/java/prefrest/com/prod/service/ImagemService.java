package prefrest.com.prod.service;

import liquibase.util.file.FilenameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.event.RecursoEvent;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.ImagemRepository;
import prefrest.com.prod.repository.ImagemRepositoryPerson;
import prefrest.com.prod.repository.impl.ImagemRepositoryPersonImpl;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Iterator;

@Service
public class ImagemService {
    @Autowired
    ImagemRepository imagemRepository;
    @Autowired
    ImagemRepositoryPerson imagemRepositoryPerson;

    private String diretorio = new File("").getAbsoluteFile().getParent() + "\\imagens";
    private BufferedImage bufferedImage;


    public ResponseEntity<Imagens> salvarImagem(Imagens imagem, HttpServletResponse response, ApplicationEventPublisher publisher) {
        imagem.setUltAlt(LocalDateTime.now());
        Imagens imagemSalva = imagemRepository.save(imagem);
        publisher.publishEvent(new RecursoEvent(this, response, imagem.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(imagemSalva);
    }

    public ResponseEntity atualizarSalvarImagem(MultipartFile file, Long codigo) throws IOException {
        String diretorioImagem;
        //Transforma o tamanho em MegaBytes de bytes
        Long imgMb = tamanhoImagem(file);
        //Criar um diretório acima a pasta imagens
        if (isDiretorioCriado()) {
            if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("png")) {
                diretorioImagem = converteImagemPngToJpg(file);
            } else {
                //Se for maior que 2MB vai fazer uma compressão de cores para diminuir o tamanho.
                if (imgMb > 2) {
                    compressImagemColors(file);
                } else {
                    salvaImagemJpg(file);

                }
                diretorioImagem = diretorio + "\\" + file.getOriginalFilename();
            }
            if (diretorioImagem != null) {
                Imagens imagemSalva = imagemRepository.findOne(codigo);
                boolean isAtualizado = imagemRepositoryPerson.updateImagem(diretorioImagem, imagemSalva);
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

    private void salvaImagemJpg(MultipartFile file) throws IOException {
        //Salva a imagem primeiro no diretório
        byte[] bytes = file.getBytes();
        Path path = Paths.get(diretorio + "\\" + file.getOriginalFilename());
        Files.write(path, bytes);
    }

    private void compressImagemColors(MultipartFile file) throws IOException {
        InputStream in = new ByteArrayInputStream(file.getBytes());
        BufferedImage image = ImageIO.read(in);

        File output = new File(diretorio + "\\" + file.getOriginalFilename());
        OutputStream out = new FileOutputStream(output);

        ImageWriter writer = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(file.getOriginalFilename())).next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.3f);
        }

        writer.write(null, new IIOImage(image, null, null), param);

        out.close();
        ios.close();
        writer.dispose();
    }

    private String converteImagemPngToJpg(MultipartFile file) {
        try {
            //Le a imagem em memória
            InputStream in = new ByteArrayInputStream(file.getBytes());
            bufferedImage = ImageIO.read(in);

            // Criar uma imagem em vazia, RGB, com a mesma largura e algura, e com fundo branco
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

            String diretorioImagem = diretorio + "\\" +
                    file.getOriginalFilename().replace(FilenameUtils.getExtension(file.getOriginalFilename()), "") + "jpg";
            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", new File(diretorioImagem));
            return diretorioImagem;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Boolean isDiretorioCriado() {

        File folder = new File(diretorio);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    private long tamanhoImagem(@RequestParam MultipartFile file) {
        return file.getSize() / (1024 * 1024);
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
}
