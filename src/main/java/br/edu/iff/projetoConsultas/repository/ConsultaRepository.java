
package br.edu.iff.projetoConsultas.repository;
import org.springframework.data.domain.Pageable;
import br.edu.iff.projetoConsultas.model.Consulta;
import java.util.Calendar;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
   
    public List<Consulta> findByPacienteId(Long pacienteId, Pageable page);
    
    public List<Consulta> findByPsicologoId(Long psicologoId, Pageable page);
    
    public List<Consulta> findByAssistenteId(Long assostenteId, Pageable page);
    
    @Query("SELECT DISTINCT (c) from CONSULTA c WHERE c.dataConsulta =:dataConsulta")
    public List<Consulta> findConsultasPorData(Calendar dataConsulta);
}
