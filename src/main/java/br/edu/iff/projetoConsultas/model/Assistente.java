package br.edu.iff.projetoConsultas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Assistente extends Pessoa{
    @Column(nullable = false)
    private String matricula;
    @Column(nullable = false)
    private String senha;
    
    @JsonBackReference
    @OneToMany(mappedBy = "assistente")
    private List<Consulta> consultas = new ArrayList<>();

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
