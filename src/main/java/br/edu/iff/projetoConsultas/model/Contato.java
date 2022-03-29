package br.edu.iff.projetoConsultas.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Embeddable
public class Contato implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column( length = 14, nullable = false)
    @NotBlank(message = "Telefone obrigaório.")
    @Length(min = 13, max = 14, message = "Telefone deve ter 13 ou 14 caracteres (Ex: (99)9999-9999 ou (99)99999-9999).")
    private String telefone;
    @Column (nullable = false, length = 100, unique = true, updatable = false)
    @NotBlank(message = "Email obrigaório.")
    @Email
    private String email;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.telefone);
        hash = 37 * hash + Objects.hashCode(this.email);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contato other = (Contato) obj;
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        return true;
    }
      
}
