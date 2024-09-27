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

import br.com.kapplanapi.models.Cupom;

import br.com.kapplanapi.models.CategoriaCupom;

import br.com.kapplanapi.repository.CupomRepository;
import br.com.kapplanapi.repository.CategoriaCupomRepository;

import br.com.kapplanapi.utils.ClienteDadosReponse;
import br.com.kapplanapi.utils.MessageResponse;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/")
public class CupomController {

	

	@Autowired
    CupomRepository cupomRepository;

	@Autowired
    CategoriaCupomRepository categoriaCupomRepository;

	@GetMapping("protected/cupons/listar")
	public List<Cupom> searchAll()
	{
		return cupomRepository.findAll();

	}


	@GetMapping("protected/cupons/categorias/listartodas")
	public List<CategoriaCupom> buscarTodasAsCategoriasCupons()
	{
		return categoriaCupomRepository.findAll();

	}

	@PostMapping("protected/cupons/categorias/cadastrar")
	public CategoriaCupom cadastrarCategoria(@RequestBody CategoriaCupom categoria)
	{
		categoria.setData_cadastro(LocalDateTime.now());
		return categoriaCupomRepository.save(categoria);
	}

	

}
