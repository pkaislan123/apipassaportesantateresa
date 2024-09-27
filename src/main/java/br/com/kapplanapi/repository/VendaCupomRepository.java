package br.com.kapplanapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.VendaCupom;

@Repository
public interface VendaCupomRepository extends JpaRepository<VendaCupom,Integer>{


}
