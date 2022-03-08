package br.edu.iff.projetoConsultas.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Psicologo extends Pessoa{
    
    @Column(nullable = false, length = 14, unique = true)
    private String crp;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
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
