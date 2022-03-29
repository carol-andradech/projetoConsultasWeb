package br.edu.iff.projetoConsultas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Paciente extends Pessoa{
    @Column(nullable = false)
    @NotBlank(message = "Prontuário necessário.")
    private int prontuario;
    
    @JsonIgnore
    @OneToMany(mappedBy = "paciente")
    @JoinColumn(nullable = false, name = "paciente_ID")
    private List<Consulta> consultas = new ArrayList<>();

    public int getProntuario() {
        return prontuario;
    }

    public void setProntuario(int prontuario) {
        this.prontuario = prontuario;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

}
