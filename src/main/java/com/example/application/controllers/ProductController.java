package com.example.application.controllers;

import java.util.Date;

public class ProductController {
    private Integer id_product;
    private String nome;
    private String tipo_produto;
    private Integer quantidade;
    private Date data;
    private String doador_nome;

    public ProductController() {
    }

    public ProductController(Integer id_product, String nome, String tipo_produto, Integer quantidade, Date data, String doador_nome, Integer id_user) {
        this.id_product = id_product;
        this.nome = nome;
        this.tipo_produto = tipo_produto;
        this.quantidade = quantidade;
        this.data = data;
        this.doador_nome = doador_nome;
    }

    public Integer getIdProduct() {
        return id_product;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoProduto() {
        return tipo_produto;
    }

    public void setTipoProduto(String tipoProduto) {
        this.tipo_produto = tipoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDoadorNome() {
        return doador_nome;
    }

    public void setDoadorNome(String doadorNome) {
        this.doador_nome = doadorNome;
    }
}