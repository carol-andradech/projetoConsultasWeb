
package br.edu.iff.projetoConsultas.service;

import br.edu.iff.projetoConsultas.model.Consulta;
import br.edu.iff.projetoConsultas.repository.ConsultasRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {
    @Autowired
    private ConsultasRepository repo;
    
    public List<Consulta> findAll (int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }
    
    public List<Consulta> findAll(){
        return repo.findAll();
    }
    
    public List<Consulta> findAll(int page, int size, Long assistenteId, Long pacienteId, Long psicologoId){
        Pageable p = PageRequest.of(page, size);
        if(assistenteId != 0 && pacienteId != 0 && psicologoId != 0){
            repo.findByAssistenteIdAndPacienteIdAndPsicologoId(assistenteId, pacienteId, psicologoId, p);
        }else if (assistenteId!=0){
            repo.findByAssistenteId(assistenteId, p);
        } else if (pacienteId!=0){
            repo.findByPacienteId(pacienteId, p);
        } else if (psicologoId!=0){
            repo.findByPsicologoId(psicologoId, p);
        }
        
        return repo.findAll(p).toList(); 
    }
    
    public Consulta findById(Long id){
        Optional<Consulta> obj = repo.findById(id);
        if(obj.isEmpty()){
            throw new RuntimeException("Consulta não encontrada.");
        }
        return obj.get();
    }
    
    public Consulta save(Consulta c){
        try{
            return repo.save(c);
        }catch(Exception e){
            throw new RuntimeException("Falha ao salvar a consulta");
        }
    }
    
    public Consulta update(Consulta c){
        
        Consulta obj = findById(c.getId());
        
        List<Consulta> consultasAtuais = obj.getConsultas();
        consultasAtuais.removeAll(c.getConsultas());
        verificaExclusaoConsultaMarcada(consultasAtuais);
        
        try{
            c.setValor(obj.getValor());
            return repo.save(c);
        }catch(Exception e){
            throw new RuntimeException("Falha ao atualizar a consulta.");
        }
    }
    
    public void delete(Long id){
            Consulta obj = findById(id);
            verificaExclusaoConsultaMarcada(obj.getConsultas());
            try{
                repo.delete(obj);
            }catch(Exception e){
                throw new RuntimeException("Falha ao deletar a consulta.");
            }
    }
    
    private void verificaExclusaoConsultaMarcada(List<Consulta> consultas){
        for(Consulta c : consultas){
            if(!c.getPaciente().isEmpty()){
                throw new RuntimeException("Não é possível excluir consulta com cliente marcado.");
            }
        }
    }
  
}


