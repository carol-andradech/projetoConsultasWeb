package br.edu.iff.projetoConsultas.model;

import java.util.List;

public class Psicologo extends Pessoa{

    private String crp;
    private String senha;
    private TipoAbordagemEnum tipo;
    
    private List<Consulta> consultas;

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoAbordagemEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoAbordagemEnum tipo) {
        this.tipo = tipo;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }
    
}
