
package br.edu.iff.projetoConsultas.service;

import br.edu.iff.projetoConsultas.model.Assistente;
import br.edu.iff.projetoConsultas.model.Pessoa;
import br.edu.iff.projetoConsultas.repository.AssistenteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AssistenteService {
    @Autowired
    private AssistenteRepository repo;
    
    public List<Assistente> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }
    
    public List<Assistente> findAll(){
        return repo.findAll();
    }
    
    public Assistente findById(Long id){
        Optional<Assistente> result = repo.findAllById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Assistente não encontrado.");
        }
        return result.get();
    }
    
    public Assistente save(Assistente a){
         //Verifica se cpf existe
        verificaCpfCadastrado(a.getCpf());
        try{
           return repo.save(a);
        }catch(Exception e){
            throw new RuntimeException("Falha ao salvar Assistente.");
        }
    }
    
    public Assistente update (Assistente a, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        //Verifica se Existente já existe
        Assistente obj = findById(a.getId());
        //Verifica alteração da senha
        alterarSenha(obj, senhaAtual, novaSenha, confirmarNovaSenha);
        try{
            a.setCpf(obj.getCpf());
            return repo.save(a);
        }catch(Exception e){
            throw new RuntimeException("Falha ao atualizar o Assistente.");
        }
        
    }
    
    public void delete(Long id){
        Assistente obj = findById(id);
        verificaExclusaoAssistenteComConsultas(obj);
        try{
            repo.delete(obj);
        }catch(Exception e){
            throw new RuntimeException("Falha ao excluir Assistente.");
        }
    }
    
    private void verificaCpfCadastrado(String cpf){
        List<Pessoa> result = repo.findByCpf(cpf);
        if(!result.isEmpty()){
            throw new RuntimeException("CPF já cadastrado.");
        }
    }
    
    private void alterarSenha(Assistente obj, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        if(!senhaAtual.isBlank() && !novaSenha.isBlank() && !confirmarNovaSenha.isBlank()){
            if(senhaAtual.equals(obj.getSenha())){
                throw new RuntimeException("Senha atual está incorreta.");
            }
            if(novaSenha.equals(confirmarNovaSenha)){
                throw new RuntimeException("Nova senha e Confirmar Nova Senha não conferem.");
            }
            obj.setSenha(novaSenha);
        }
    }
    
    private void verificaExclusaoAssistenteComConsultas(Assistente a){
   
        if(!a.getConsultas().isEmpty()){
            throw new RuntimeException("Assistente possui consultas. Não pode ser excluido.");
        }
    }
}
