package prefrest.com.prod.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UtilPasswordEncoder {

    public static String encodePassword (String pass){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        StringBuilder sb = new StringBuilder(passwordEncoder.encode(pass));
        //TODO Criar algum algoritmo aqui
        return sb.toString();
    }

    public static boolean isPassword(String info, String passwordEncoded){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(info, passwordEncoded);
        return matches;
    }


}
