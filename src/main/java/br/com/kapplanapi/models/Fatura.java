package br.com.kapplanapi.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fatura")
public class Fatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_fatura;

	@Column(columnDefinition = "integer default 0")
	private int tipo_fatura;

	@OneToOne
	@JoinColumn(nullable = true, name = "cupom_id", referencedColumnName = "id_cupom")
	private Cupom cupom;

	
	@OneToOne
	@JoinColumn(nullable = true, name = "cliente_id", referencedColumnName = "id_cliente")
	private Cliente cliente;


	@Column(nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_criacao;

	@Column(nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_pagamento;

	@Column(nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_vencimento;


	private String forma_pagamento;
	

	private double valor;

	private String descricao;

	@Column(columnDefinition = "integer default 0")
	private int status_fatura;

	private String id_preferencia_pagamento;
	private String link_pagamento;
	private String external_reference;

	// fatura enviada ao email do cliente
	// 0 -> nao enviado
	// 1 -> enviado
	@Column(columnDefinition = "integer default 0")
	private int fatura_enviada_ao_cliente;



	

}
