package br.com.kapplanapi.models;

import java.time.LocalDateTime;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "email" }),  // Exemplo de outra coluna única
    @UniqueConstraint(columnNames = { "cpf" })     // Exemplo de mais uma coluna única
})
public class Cliente {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_cliente;

	@NotBlank
	@Size(max = 11)
	private String cpf;

	@NotBlank
	@Size(max = 255)
	@Column(name = "senha")
	private String senha;

	private String email, nome_completo, whatsapp;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;

	// 1 ativo
	// 0 bloqueado
	@Column(columnDefinition = "integer default 1")
	private int status;

	private String roles;
}
