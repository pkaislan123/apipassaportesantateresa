package br.com.kapplanapi.configs;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.kapplanapi.services.CustomClienteDetailsService;
import br.com.kapplanapi.services.CustomFornecedorDetailsService;
import br.com.kapplanapi.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomClienteDetailsService clienteDetailsService;

	@Autowired
	private CustomFornecedorDetailsService fornecedorDetailsService;

	@Bean
	public AuthAdminTokenFilter authenticationAdminJwtTokenFilter() {
		return new AuthAdminTokenFilter();
	}

	@Bean
	public AuthClientTokenFilter authenticationClientJwtTokenFilter() {
		return new AuthClientTokenFilter();
	}

	@Bean
	public AuthFornecedorTokenFilter authenticationFornecedorJwtTokenFilter() {
		return new AuthFornecedorTokenFilter();
	}

	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.authenticationProvider((AuthenticationProvider) authAdminProvider());
		authenticationManagerBuilder.authenticationProvider((AuthenticationProvider) authClientProvider());
		authenticationManagerBuilder.authenticationProvider((AuthenticationProvider) authFornecedorProvider());

	}

	@Bean
	public DaoAuthenticationProvider authAdminProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService((UserDetailsService) this.userDetailsService);
		authProvider.setPasswordEncoder(this.passwordEncoder);
		return authProvider;
	}

	@Bean
	public DaoAuthenticationProvider authClientProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService((UserDetailsService) this.clienteDetailsService);
		authProvider.setPasswordEncoder(this.passwordEncoder);
		return authProvider;
	}

	@Bean
	public DaoAuthenticationProvider authFornecedorProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService((UserDetailsService) this.fornecedorDetailsService);
		authProvider.setPasswordEncoder(this.passwordEncoder);
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((
		 * ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((
		 * HttpSecurity)((HttpSecurity)((HttpSecurity)http.cors().and()).csrf().disable(
		 * )).exceptionHandling().authenticationEntryPoint((AuthenticationEntryPoint)
		 * this.unauthorizedHandler).and())
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		 * and()) .authorizeRequests().antMatchers(new String[] { , })).permitAll()
		 * .anyRequest()).authenticated();
		 * http.addFilterBefore((Filter)authenticationAdminJwtTokenFilter(),
		 * UsernamePasswordAuthenticationFilter.class);
		 * http.addFilterAfter((Filter)authenticationClientJwtTokenFilter(),
		 * UsernamePasswordAuthenticationFilter.class);
		 */
		
		
		http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/v1/protected/signin", "/v1/protected/cliente/buscarporuid/{uid}",
						"/v1/protected/cliente/inserir_registro_consumo",
						"/v1/protected/users/cadastrar",
						"/v1/protected/clientes/cadastrar",
						"/v1/protected/cliente/finalizar_sessao_carga/{id_sessao_carga}",
						"/v1/protected/veiculos/testegerarpdf/{id_cliente}",
						"/v1/protected/logs/cadastrar",
						"/v1/protected/refreshtoken",
						"/v1/protected/pagamentos/atualizar",
						"/v1/protected/transmissores/informarOnline/{numero_serie}/{senha}",
						"/v1/protected/acao/listarPorReceptorNumeroSerieNaoRespondidas/{numero_serie_receptor}/{senha}",
						"/v1/protected/acao/responder/{id_requisicao}/{numero_serie}/{senha}",
						"/v1/protected/acao/registrarzonadisparada/{num_serie_tx}/{canal_tx}/{zona_rx}/{num_serie_rx}/{senha}",
						"/v1/protected/acao/informarstatuscentral/{num_serie_rx}/{senha}/{status_central}",
					    "/v1/protected/controladoracesso/buscarporuid/{numero_serie_ctrl_acesso}/{senha}/{uid}",
					    "/v1/protected/acao/listarPorCtrlAcessoNumeroSerieNaoRespondidas/{numero_serie_ctrl}/{senha}",
					    "/v1/protected/controladoracesso/responder/{id_requisicao}/{numero_serie}/{senha}",
					    "/v1/protected/controladoresacesso/teste",
					    "/v1/protected/acao/registrartxoffline/{num_serie_tx}/{num_serie_rx}/{senha}",
					    "/ws",
					    "ws",
					    "*ws*",
					    "/ws/app",
					    "/v1/protected/noticias/listarNoticia/{id_noticia}",
						"/v1/protected/noticias/listarNoticias**",
						"/v1/protected/noticias/listarCategorias",
						"/v1/protected/noticias/listartodasnoticias",
						"/v1/protected/noticias/listarNoticiaVizualizacao/{id_noticia}**",
						"/monitoramentowebsocket*",
						"/v1/protected/modulosonoro/tarefas/responder/{id_tarefa}",
						"/v1/protected/modulosonoro/tarefas/listar/{codigo_modulo_sonoro}",
						"/v1/protected/modulosonoro/download/{id_audio}",
						"/v1/protected/modulosonoro/informarOnline/{codigo}/{senha}",
						"/v1/protected/modulosonoro/listarconfigs/{codigo}/{senha}",
						"/v1/protected/modulosonoro/disparo/{codigo}/{senha}",
						"/v1/protected/modulosonoro/disparo/teste1",
						"/v1/protected/modulosonoro/disparo/teste2",
						"/v1/protected/modulosonoro/disparo/teste3",
						"/v1/protected/modulopgm/informarOnline/{codigo}/{senha}",
						"/v1/protected/modulopgm/tarefas/listar/{codigo_modulo_pgm}/{senha}",
						"/v1/protected/modulopgm/tarefas/responder/{id_tarefa}/{codigo_modulo_pgm}/{senha}",
						"/v1/protected/modulopgm/tarefas/criar/{codigo_modulo_pgm}/{senha}/{tipo_tarefa}",
						"/v1/protected/modulopgm/tarefas/informar/{codigo_modulo_pgm}/{senha}/{tipo}/{origem}",
						"/v1/protected/modulopgm/tarefas/listartodas/{codigo_modulo_pgm}/{senha}",
						"/v1/public/consultarautenticacao",
						"/v1/public/emails/registrarvizualizacao/{id_pre_cotacao}",
						"/v1/public/notificacoes/registrarvizualizacao/{id_notificacao}",
						"/v1/protected/notificacoes/baixarPdf",
						"/v1/protected/contatos/retornardadoscontato/*",
						"/v1/protected/modulopgm/medicao/enviar/{codigo_modulo_pgm}/{senha}",
						"/v1/protected/modulopgm/medicao/listar/{codigo_modulo_pgm}",
						"/v1/protected/modulopgm/buscaratt",
						"/v1/protected/modulopgmmedidor/buscaratt",
						"/v1/protected/modulopgm/buscarattpgmrosinetosesp8266",
						"/v1/protected/signupemailverify/{email}",
						"/v1/protected/signuppfverify/{identificacao}",
						"/v1/protected/cupons/listar",
						"/v1/protected/pagamentos/atualizar",
						"/v1/protected/mp/webhock"
						)
				.permitAll().anyRequest().authenticated();
			

		http.addFilterBefore((Filter) authenticationAdminJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter((Filter) authenticationClientJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterAfter((Filter) authenticationFornecedorJwtTokenFilter(),
				UsernamePasswordAuthenticationFilter.class);

	}
}