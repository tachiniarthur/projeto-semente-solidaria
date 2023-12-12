package com.example.application.controllers;

import java.util.Date;

public class CollectionController {
    private Integer id_collection;
    private Double valor_doado;
    private String forma_doacao;
    private Date data;
    private String nome_doador;

    public CollectionController() {
    }

    public CollectionController(Integer id_collection, Double valor_doado, String forma_doacao, Date data, String nome_doador) {
        this.id_collection = id_collection;
        this.valor_doado = valor_doado;
        this.forma_doacao = forma_doacao;
        this.data = data;
        this.nome_doador = nome_doador;
    }

    public Integer getId() {
        return id_collection;
    }

    public Double getValorDoado() {
        return valor_doado;
    }

    public void setValorDoado(Double valorDoado) {
        this.valor_doado = valorDoado;
    }

    public String getFormaDoacao() {
        return forma_doacao;
    }

    public void setFormaDoacao(String formaDoacao) {
        this.forma_doacao = formaDoacao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNomeDoador() {
        return nome_doador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nome_doador = nomeDoador;
    }
}