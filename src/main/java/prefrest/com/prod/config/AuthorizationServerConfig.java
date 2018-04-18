package prefrest.com.prod.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import prefrest.com.prod.config.property.ControleProperty;
import prefrest.com.prod.config.token.CustomTokenEnchancer;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static prefrest.com.prod.config.SecurityConstants.SECRET;
import static prefrest.com.prod.config.SecurityConstants.SECRET_TOKEN;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    ControleProperty controleProperty;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("angular")
                .secret(SECRET)
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")//fluxo PasswordFlow da aplicação que recebe usuário e senha do client. Fluxo somente utilizado se for aplicação que responde ao cliente que é implementado por você
                //.accessTokenValiditySeconds((int) TimeUnit.SECONDS.convert(30, TimeUnit.MINUTES));//Token válido por 30 minutos
                .accessTokenValiditySeconds(controleProperty.getCookie().getTempoVida())
                .refreshTokenValiditySeconds((int) TimeUnit.SECONDS.convert(24, TimeUnit.HOURS));

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /*TokenEnhancerChain tokenEnhancerChain =  new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), acessTokenConverter()));*/
        endpoints.tokenStore(tokenStore())
                .accessTokenConverter(acessTokenConverter())
                .reuseRefreshTokens(false)
                .authenticationManager(authenticationManager);
    }

    @Bean
    public JwtAccessTokenConverter acessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(SECRET_TOKEN);//Segredo para codificar o token
        return accessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
        //return new InMemoryTokenStore();
        return new JwtTokenStore(acessTokenConverter());
    }

    /*@Bean
    public TokenEnhancer tokenEnhancer(){
        return new CustomTokenEnchancer();
    }*/
}
