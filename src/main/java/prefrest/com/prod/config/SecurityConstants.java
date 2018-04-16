package prefrest.com.prod.config;

import prefrest.com.prod.util.UtilPasswordEncoder;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {
    //Header Authorization Bearer  e aí o hash
    static final String SECRET = "FBUILDER";
    static final String SECRET_TOKEN = "FBUILDER2";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP = "/users/sign-up";
    static final Long EXPIRATION_TIME = 86400000L;

    //Descobrir Millisegundos
    /*public static void main(String[] args) {
        System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
    }*/


    /*public static void main(String[] args) {
        String encode = UtilPasswordEncoder.encodePassword("123");
        System.out.println(encode);
        System.out.println(UtilPasswordEncoder.isPassword("123", encode));

    }*/
}
