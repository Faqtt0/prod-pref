package prefrest.com.prod.util;


import liquibase.util.file.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import prefrest.com.prod.model.Imagens;
import prefrest.com.prod.repository.ImagemCommonRepository;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class UtilConverterImagem{
    private static BufferedImage bufferedImage;

    public static ResponseEntity atualizarSalvarImagem(MultipartFile file, String caminho, Object classe, ImagemCommonRepository commonRepository) {
        String diretorioImagem;
        Long imgMb = tamanhoImagem(file);
        //Criar um diretório acima a pasta imagens
        if (isDiretorioCriado(caminho)) {
            if (FilenameUtils.getExtension(file.getOriginalFilename()).equals("png")) {
                diretorioImagem = converteImagemPngToJpg(file, caminho);
            } else {
                if (imgMb > 2) {
                    try {
                        compressImagemColors(file, caminho);
                    } catch (IOException e) {
                        e.getMessage();
                    }
                } else {
                    try {
                        salvaImagemJpg(file, caminho);
                    } catch (IOException e) {
                        e.getMessage();
                    }
                }
                diretorioImagem = caminho + "\\" + file.getOriginalFilename();
            }

            if (diretorioImagem != null) {

                boolean isAtualizado = false;
                if (classe != null) {
                    try {
                        isAtualizado = commonRepository.updateImagem(diretorioImagem, classe);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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


    public static long tamanhoImagem(MultipartFile file) {
        return file.getSize() / (1024 * 1024);
    }

    public static Boolean isDiretorioCriado(String diretorio) {
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


    public static String converteImagemPngToJpg (MultipartFile file, String diretorio){
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

    public static void compressImagemColors(MultipartFile file, String diretorio) throws IOException {
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

    public static void salvaImagemJpg(MultipartFile file, String diretorio) throws IOException {
        //Salva a imagem primeiro no diretório
        byte[] bytes = file.getBytes();
        Path path = Paths.get(diretorio + "\\" + file.getOriginalFilename());
        Files.write(path, bytes);
    }

    public static void deletarImagemHD(String diretorioImagemSalva) {
        File imagem = new File(diretorioImagemSalva);
        imagem.delete();
    }

}
