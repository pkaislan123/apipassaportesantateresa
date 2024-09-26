package br.com.kapplanapi.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.kapplanapi.models.Cliente;

import br.com.kapplanapi.models.Fornecedor;

import br.com.kapplanapi.models.User;

import br.com.kapplanapi.repository.ClienteRepository;

import br.com.kapplanapi.repository.UserRepository;
import br.com.kapplanapi.services.ClientesService;

import br.com.kapplanapi.utils.ClienteDadosReponse;
import br.com.kapplanapi.utils.MessageResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/")
public class ClientesController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ClientesService service;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	UserRepository userRepository;

	@CrossOrigin
	@PutMapping(path = "protected/cliente/mudarsenha/{id_cliente}")
	public ResponseEntity mudarSenha(@PathVariable("id_cliente") int id_usuario, @RequestBody Cliente cliente) {
		return clienteRepository.findById(id_usuario).map(record -> {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			record.setSenha(encoder.encode(cliente.getSenha()));

			Cliente updated = clienteRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("protected/clientes/listarTodos")
	public List<Cliente> searchAll()

	{
		return clienteRepository.findAll();

	}

	@PostMapping("protected/clientes/cadastrar")
	public Cliente cadastrarCliente(@RequestBody Cliente cliente) {

		cliente.setStatus(1);

		cliente.setSenha(encoder.encode(cliente.getSenha()));

		cliente.setData_cadastro(LocalDateTime.now());

		cliente.setRoles("ROLE_CLIENTE");

		cliente = clienteRepository.saveAndFlush(cliente);

		return cliente;
	}

	@CrossOrigin
	@GetMapping(path = "protected/retornardadoscliente/{id}")
	public Optional<Cliente> retornarDadosCliente(@PathVariable int id) {

		return clienteRepository.findById(id);
	}

	private static final Gson gson = new Gson();

	@CrossOrigin
	@PutMapping(path = "protected/clientes/atualizar/{id_cliente}")
	public ResponseEntity atualizar(@PathVariable("id_cliente") int id_cliente, @RequestBody Cliente cliente) {
		return clienteRepository.findById(id_cliente).map(record -> {

	
			
			Cliente updated = clienteRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

}
