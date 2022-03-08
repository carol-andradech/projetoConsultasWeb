package br.edu.iff.projetoConsultas.model;

import java.util.List;


public class Assistente extends Pessoa{
    private String matricula;
    private String senha;
    
    private List<Consulta> consultas;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }


}
