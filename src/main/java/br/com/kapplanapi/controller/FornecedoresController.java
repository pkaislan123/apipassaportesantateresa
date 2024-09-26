package br.com.kapplanapi.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.kapplanapi.repository.FornecedorRepository;

import br.com.kapplanapi.services.ClientesService;
import br.com.kapplanapi.services.FornecedoresService;
import br.com.kapplanapi.utils.MessageResponse;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.kapplanapi.models.Fornecedor;


@RestController
@RequestMapping("v1/")
public class FornecedoresController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	FornecedoresService service;

	@Autowired
	FornecedorRepository fornecedorRepository;



	@ApiOperation(value = "Listar Todos os Fornecedores")
	@GetMapping("protected/fornecedores/listarTodos")
	public List<Fornecedor> searchAll() {

		return fornecedorRepository.findAll();
	}

	@ApiOperation(value = "Listar Fornecedor por Id")
	@GetMapping("protected/fornecedores/retornardadosfornecedor/{id_fornecedor}")
	public Optional<Fornecedor> buscarPorId(@PathVariable int id_fornecedor) {

		return fornecedorRepository.findById(id_fornecedor);
	}

	@ApiOperation(value = "Cadastrar Fornecedor")
	@PostMapping("protected/fornecedores/cadastrar")
	public ResponseEntity cadastrarFornecedor(@Valid @RequestBody Fornecedor signUpRequest) {

		// Create new user's account
		Fornecedor fornecedor = new Fornecedor();

		// 0 pessoa fisica
		// 1 pessoa juridica
		fornecedor.setTipo_fornecedor(signUpRequest.getTipo_fornecedor());

		// pf
		fornecedor.setCpf(signUpRequest.getCpf());
		fornecedor.setRg(signUpRequest.getRg());
		fornecedor.setNascimento(signUpRequest.getNascimento());
		fornecedor.setNome(signUpRequest.getNome());
		fornecedor.setSobrenome(signUpRequest.getSobrenome());

		// pj
		fornecedor.setCnpj(signUpRequest.getCnpj());
		fornecedor.setRazao_social(signUpRequest.getRazao_social());
		fornecedor.setNome_fantasia(signUpRequest.getNome_fantasia());
		fornecedor.setPorte(signUpRequest.getPorte());
		fornecedor.setAtividade(signUpRequest.getAtividade());
		fornecedor.setIe(signUpRequest.getIe());

		// ambos
		fornecedor.setDescricao(signUpRequest.getDescricao());
		fornecedor.setEmail(signUpRequest.getEmail());
		fornecedor.setStatus(1);

		if (fornecedor.getTipo_fornecedor() == 0) {
			fornecedor.setSenha(encoder.encode(signUpRequest.getCpf()));

		} else if (fornecedor.getTipo_fornecedor() == 1) {
			fornecedor.setSenha(encoder.encode(signUpRequest.getCnpj()));

		}

		fornecedor.setData_cadastro(LocalDateTime.now());

		fornecedor.setLogradouro(signUpRequest.getLogradouro());
		fornecedor.setNumero(signUpRequest.getNumero());

		fornecedor.setBairro(signUpRequest.getNumero());
		fornecedor.setEstado(signUpRequest.getEstado());
		fornecedor.setCep(signUpRequest.getCep());
		fornecedor.setCidade(signUpRequest.getCidade());
		fornecedor.setContato(signUpRequest.getContato());
		fornecedor.setLatitude(signUpRequest.getLatitude());
		fornecedor.setComplemento(signUpRequest.getComplemento());
		fornecedor.setLongitude(signUpRequest.getLongitude());

		fornecedor.setSite(signUpRequest.getSite());

		fornecedor.setRoles("ROLE_FORNECEDOR");


		fornecedor = fornecedorRepository.saveAndFlush(fornecedor);

		return ResponseEntity.ok(new MessageResponse("Fornecedor registered successfully!"));

	}

	

	
	@CrossOrigin
	@GetMapping(path = "protected/retornardadosfornecedor/{id}")
	public Optional<Fornecedor> retornarDadosFornecedor(@PathVariable int id) {

		return fornecedorRepository.findById(id);
	}

	@CrossOrigin
	@PutMapping(path = "protected/fornecedores/atualizar/{id_fornecedor}")
	public ResponseEntity mudarConfiguracoes(@PathVariable("id_fornecedor") int id_fornecedor,
			@RequestBody Fornecedor fornecedor) {
		return fornecedorRepository.findById(id_fornecedor).map(record -> {

			if (fornecedor.getTipo_fornecedor() == 0) {
				// pessoa fisica

				record.setRg(fornecedor.getRg());
				record.setNascimento(fornecedor.getNascimento());
				record.setNome(fornecedor.getNome());
				record.setSobrenome(fornecedor.getSobrenome());
				record.setDescricao(fornecedor.getDescricao());

			} else {
				// pessoa juridica
				record.setRazao_social(fornecedor.getRazao_social());
				record.setNome_fantasia(fornecedor.getNome_fantasia());
				record.setPorte(fornecedor.getPorte());
				record.setAtividade(fornecedor.getAtividade());
				record.setIe(fornecedor.getIe());
			}

			record.setLogradouro(fornecedor.getLogradouro());
			record.setNumero(fornecedor.getNumero());
			record.setBairro(fornecedor.getNumero());
			record.setEstado(fornecedor.getEstado());
			record.setCep(fornecedor.getCep());
			record.setLatitude(fornecedor.getLatitude());
			record.setComplemento(fornecedor.getComplemento());
			record.setLongitude(fornecedor.getLongitude());
			record.setSite(fornecedor.getSite());
			record.setCidade(fornecedor.getCidade());
			record.setContato(fornecedor.getContato());


			Fornecedor updated = fornecedorRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}


	
}
