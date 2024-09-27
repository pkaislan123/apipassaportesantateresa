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

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venda_cupom")
public class VendaCupom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_venda_cupom;

    private String codigo_cupom;

    private int id_cliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime data_cadastro;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime data_pagamento;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime data_validade;

    // 1 ativo
    // 0 bloqueado
    @Column(columnDefinition = "integer default 1")
    private int status;

    @OneToOne
    @JoinColumn(nullable = true, name = "pagamento_id", referencedColumnName = "id_pagamento")
    private Pagamento pagamento;

}
