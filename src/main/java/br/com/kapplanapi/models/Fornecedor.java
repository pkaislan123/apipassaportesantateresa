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
@Table(name = "fornecedor", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "email" }),  // Exemplo de outra coluna única
    @UniqueConstraint(columnNames = { "cnpj" })     // Exemplo de mais uma coluna única
})
public class Fornecedor   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_fornecedor;

	private int tipo_fornecedor;
	

	// dados de pessoa juridica
	private String cnpj;
	private String razao_social;
	private String nome_fantasia;

	// dados de pessoas fisicas
	private String cpf;
	private String rg;
	private String nascimento;
	private String nome;
	private String sobrenome;

	private String porte;
	private String atividade;
	private String ie;
	private String descricao;

	@Size(max = 255)
	@Column(name = "email")
	private String email;

	@NotBlank
	@Size(max = 255)
	@Column(name = "senha")
	private String senha;
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;

	private int status;
	
	
	private String contato;



	//Endereço
			private String logradouro;

			private String numero;

			private String bairro;

			private String complemento;

			private String cidade;

			private String estado;

			private String cep;
			
			private String latitude, longitude;
			
			private String site;
			private String instagram, link_mapa;
			private String links;
			private String roles;


}
