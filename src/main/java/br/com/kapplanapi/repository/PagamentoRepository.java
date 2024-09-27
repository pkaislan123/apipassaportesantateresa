package br.com.kapplanapi.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.kapplanapi.models.Pagamento;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento,Integer>{

@Query(value = "select * from pagamento where payment_id = ? limit 1", nativeQuery = true)
	Optional<Pagamento> buscarPorIdPagamentoMercadoPago(String id_pagamento);
}
