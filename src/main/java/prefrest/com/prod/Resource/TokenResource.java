package prefrest.com.prod.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prefrest.com.prod.config.property.ControleProperty;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource {
    @Autowired
    ControleProperty controleProperty;

    //Logout
    @GetMapping("/revoke")
    public void rovoke(HttpServletResponse response, HttpServletRequest request){
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(controleProperty.getSeguranca().isEnableHttps());
        cookie.setPath(request.getContextPath() + "/oauth/token");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(HttpStatus.NO_CONTENT.value());

    }
}
