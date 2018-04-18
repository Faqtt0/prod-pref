package prefrest.com.prod.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import prefrest.com.prod.config.property.ControleProperty;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

//TIPO do dado que deseja ser interceptado quando estiver voltando a requisição (OAuth2AccessToken)
//Ou seja toda vez que for retornar um ResposeBody de OAuth2AccessToken var cair nos métodos aqui.
//Pode ser utilizado para qualquer outro tipo de classe
@ControllerAdvice
public class RefreshTokenPostProcessor implements ResponseBodyAdvice<OAuth2AccessToken> {
    @Autowired
    private ControleProperty controleProperty;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        //Só irá executar o método beforeBodyWrite se o resultado for verdadeiro.
        return methodParameter.getMethod().getName().equals("postAccessToken");
    }

    @Override
    public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken oAuth2AccessTokenBody,
                                             MethodParameter methodParameter,
                                             MediaType mediaType,
                                             Class<? extends HttpMessageConverter<?>> aClass,
                                             ServerHttpRequest serverHttpRequest,
                                             ServerHttpResponse serverHttpResponse) {
        String refreshToken = oAuth2AccessTokenBody.getRefreshToken().getValue();

        HttpServletRequest req = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest();
        HttpServletResponse resp =  ((ServletServerHttpResponse) serverHttpResponse).getServletResponse();

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessTokenBody;

        adicionarRefreshTokenNoCokie(refreshToken, req, resp);
        removerRefreshTokenDoBody(token);
        return oAuth2AccessTokenBody;
    }

    private void removerRefreshTokenDoBody(DefaultOAuth2AccessToken token) {
        token.setRefreshToken(null);
    }

    private void adicionarRefreshTokenNoCokie(String refreshToken, HttpServletRequest req, HttpServletResponse resp) {
        Cookie refreshTokenCookie =  new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(controleProperty.getSeguranca().isEnableHttps());
        refreshTokenCookie.setPath(req.getContextPath() + "/oauth/token");
        refreshTokenCookie.setMaxAge((int) TimeUnit.SECONDS.convert(30, TimeUnit.DAYS));
        resp.addCookie(refreshTokenCookie);
    }
}
