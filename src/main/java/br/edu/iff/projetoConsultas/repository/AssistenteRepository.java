
package br.edu.iff.projetoConsultas.repository;

import br.edu.iff.projetoConsultas.model.Assistente;
import br.edu.iff.projetoConsultas.model.Pessoa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssistenteRepository extends JpaRepository<Assistente, Long>{
    @Query("SELECT p FROM Pessoa p WHERE p.cpf = :cpf OR p.email =:email")
    public List<Pessoa> findbyCpfOrEmail(@Param("cpf") String cpf, String email);
    
    public Assistente finByEmail(String email);
}
