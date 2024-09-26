package br.com.kapplanapi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.warrenstrange.googleauth.GoogleAuthenticator;


import br.com.kapplanapi.models.Cliente;


import br.com.kapplanapi.models.Fornecedor;
import br.com.kapplanapi.models.User;
import br.com.kapplanapi.repository.ClienteRepository;
import br.com.kapplanapi.repository.UserRepository;
import br.com.kapplanapi.utils.MessageResponse;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/")
public class UsuarioController {


	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepository userRepository;
	

	
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@CrossOrigin
	@GetMapping(path = "protected/user/listartodos")
	public List<User> listarTodos () {

		
		
		return userRepository.findAll();
	}
	



	@CrossOrigin
	@GetMapping(path = "protected/user/retornardadosusuario/{id}")
	public Optional<User> retornarDadosUsuario(@PathVariable int id) {

		return userRepository.findById(id);
	}

	@CrossOrigin
	@ApiOperation(value = "Alterações no Usuário")
	@PutMapping(path = "protected/user/alterar/{id_usuario}")
	public ResponseEntity alterarUsuario(@PathVariable("id_usuario") int id_usuario, @RequestBody User user) {
		return userRepository.findById(id_usuario).map(record -> {

			

			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("protected/users/cadastrar")
	public ResponseEntity cadastrarUsuario( @RequestBody User user) {

		
		
		user.setStatus(1);
		
		user.setSenha(encoder.encode(user.getSenha()));

		user.setData_cadastro(LocalDateTime.now());
		user.setRoles("ROLE_ADMIN");

		user = userRepository.saveAndFlush(user);

		if (user != null) {

			return ResponseEntity.ok(new MessageResponse("Usuario registered successfully!"));

		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@CrossOrigin
	@PutMapping(path = "protected/users/mudarStatus/{id_usuario_acionador}/{id_usuario_acionado}/{mensagem}")
	public ResponseEntity mudarStatusUsuario(@PathVariable("id_usuario_acionador") int id_usuario_acionador,@PathVariable("id_usuario_acionado") int id_usuario_acionado, @PathVariable("mensagem") String mensagem) {
		
		User usuario_acionador = userRepository.findById(id_usuario_acionador).get();
		User usuario_acionado = userRepository.findById(id_usuario_acionado).get();

		
		int status_atual = usuario_acionado.getStatus();
		
		
		if(status_atual == 1) {
			usuario_acionado.setStatus(0);
		}else {
			usuario_acionado.setStatus(1);
		}
		
		User updated = userRepository.save(usuario_acionado);
		
		
		
		
		
					

	  return ResponseEntity.ok().body(updated);
		
	}
	
	
	@CrossOrigin
	@PutMapping(path = "protected/users/atualizar/{id_usuario}")
	public ResponseEntity mudarConfiguracoes(@PathVariable("id_usuario") int id_usuario, @RequestBody User user) {
		return userRepository.findById(id_usuario).map(record -> {

		

						
			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	

	@CrossOrigin
	@PutMapping(path = "protected/users/autorizar/{id_usuario}")
	public ResponseEntity autorizar(@PathVariable("id_usuario") int id_usuario) {
				
		
		return userRepository.findById(id_usuario).map(record -> {

									
			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	
	@CrossOrigin
	@PutMapping(path = "protected/users/mudarsenha/{id_usuario}")
	public ResponseEntity mudarSenha(@PathVariable("id_usuario") int id_usuario, @RequestBody User user) {
		return userRepository.findById(id_usuario).map(record -> {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			record.setSenha(encoder.encode(user.getSenha()));

			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	
	@CrossOrigin
	@PutMapping(path = "protected/user/atualizarpermissoes/{id_usuario_acionador}")
	public ResponseEntity atualizarPermissoes(@PathVariable("id_usuario_acionador") int id_usuario_acionador,@RequestBody User user
			) {
		
		User usuario_acionador = userRepository.findById(id_usuario_acionador).get();

	
		User updated = userRepository.save(user);
		
		
		
					

	  return ResponseEntity.ok().body(updated);
		
	}
	
	
	@CrossOrigin
	@PutMapping(path = "protected/user/atualizarconfiguracoes/{id_usuario_acionador}")
	public ResponseEntity atualizarConfiguracoes(@PathVariable("id_usuario_acionador") int id_usuario_acionador,@RequestBody User user
			) {
		
		User usuario_acionador = userRepository.findById(id_usuario_acionador).get();

	
		User updated = userRepository.save(user);
		
		
		
		
					

	  return ResponseEntity.ok().body(updated);
		
	}
	

}
