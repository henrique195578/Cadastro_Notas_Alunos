package com.unipar.cadastronotasalunos.Models;

public class Bimestre {

    private int id;
    private String nome;
    private int primeiro_bimestre;
    private int segundo_bimestre;
    private int terceiro_bimestre;
    private int quarto_bimestre;


    public Bimestre() {
    }

    public Bimestre(int id, String nome, int primeiro_bimestre, int segundo_bimestre, int terceiro_bimestre, int quarto_bimestre) {
        this.id = id;
        this.nome = nome;
        this.primeiro_bimestre = primeiro_bimestre;
        this.segundo_bimestre = segundo_bimestre;
        this.terceiro_bimestre = terceiro_bimestre;
        this.quarto_bimestre = quarto_bimestre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPrimeiro_bimestre() {
        return primeiro_bimestre;
    }

    public void setPrimeiro_bimestre(int primeiro_bimestre) {
        this.primeiro_bimestre = primeiro_bimestre;
    }

    public int getSegundo_bimestre() {
        return segundo_bimestre;
    }

    public void setSegundo_bimestre(int segundo_bimestre) {
        this.segundo_bimestre = segundo_bimestre;
    }

    public int getTerceiro_bimestre() {
        return terceiro_bimestre;
    }

    public void setTerceiro_bimestre(int terceiro_bimestre) {
        this.terceiro_bimestre = terceiro_bimestre;
    }

    public int getQuarto_bimestre() {
        return quarto_bimestre;
    }

    public void setQuarto_bimestre(int quarto_bimestre) {
        this.quarto_bimestre = quarto_bimestre;
    }
}
