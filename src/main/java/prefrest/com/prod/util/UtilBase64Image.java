package prefrest.com.prod.util;

import java.util.Base64;

public class UtilBase64Image {
    public static String encoder(String imagem) {
        String base64Image = "";
        byte imageData[] = new byte[Integer.parseInt(imagem)];
        base64Image = Base64.getEncoder().encodeToString(imageData);
        return base64Image;
    }

    public static byte[] decoder(String base64Image) {
       return Base64.getDecoder().decode(base64Image);
    }
}
