package br.com.kapplanapi.models;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;



import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cupom")
public class Cupom {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_cupom;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;

	// 1 ativo
	// 0 bloqueado
	@Column(columnDefinition = "integer default 1")
	private int status;

	private String nome;

	private String descricao;

    private double valor;

    private int validade;


	

	
    
}
