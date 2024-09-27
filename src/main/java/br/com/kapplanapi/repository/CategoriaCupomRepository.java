package br.com.kapplanapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.CategoriaCupom;

@Repository
public interface CategoriaCupomRepository extends JpaRepository<CategoriaCupom, Integer> {

	

}
