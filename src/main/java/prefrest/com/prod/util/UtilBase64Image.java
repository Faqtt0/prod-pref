package prefrest.com.prod.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class UtilBase64Image {

    public static String encoder(String imagemDiretorio) {
        File file = new File(imagemDiretorio);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Ler imagem
            String base64Image = "";
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            //Codificar Imagem
            base64Image = Base64.getEncoder().encodeToString(imageData);
            return base64Image;
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return null;
    }

    public static byte[] decoder(String base64Image) {
       return Base64.getDecoder().decode(base64Image);
    }
}
