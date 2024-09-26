package br.com.kapplanapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.kapplanapi.models.Fornecedor;
import br.com.kapplanapi.repository.FornecedorRepository;



@Service
public class FornecedoresService {

	 @Autowired
	 FornecedorRepository repository;
	    
	    

	  

}