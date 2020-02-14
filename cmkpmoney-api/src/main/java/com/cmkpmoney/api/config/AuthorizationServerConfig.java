package com.cmkpmoney.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("Angular")//configurando o usuario client(angular, react, node, terão que acessar por este usuario)
			.secret("angular")		//obs. isso não é a senha do usuario (admin, admin)
			.scopes("read", "write") //client pode ler e escrever na api
			.authorizedGrantTypes("client_credentials") //grand_type da requisição
			.accessTokenValiditySeconds(1800); //definido o tempo de validade do da bearer (30 min)
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()) // definindo onde será quardado o token
		.authenticationManager(authenticationManager);
	}

	private TokenStore tokenStore() {
		//quardando o token em memoria
		return new InMemoryTokenStore();
	}
	
}
