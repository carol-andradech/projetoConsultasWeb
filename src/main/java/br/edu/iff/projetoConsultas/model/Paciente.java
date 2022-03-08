package br.edu.iff.projetoConsultas.model;

import java.util.List;

public class Paciente extends Pessoa{
    private int prontuario;
    
    private List<Consulta> consultas;

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
