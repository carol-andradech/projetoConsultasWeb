
package br.edu.iff.projetoConsultas.service;

import br.edu.iff.projetoConsultas.model.Pessoa;
import br.edu.iff.projetoConsultas.model.Psicologo;
import br.edu.iff.projetoConsultas.repository.PsicologoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PsicologoService {
    @Autowired
    private PsicologoRepository repo;
    
    public List<Psicologo> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }
    
    public List<Psicologo> findAll(){
        return repo.findAll();
    }
    
    public Psicologo findById(Long id){
        Optional<Psicologo> result = repo.findAllById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Psicologo não encontrado.");
        }
        return result.get();
    }
    
    public Psicologo save(Psicologo p){
         //Verifica se cpf existe
        verificaCpfCadastrado(p.getCpf());
        try{
           return repo.save(p);
        }catch(Exception e){
            throw new RuntimeException("Falha ao salvar Psicologo.");
        }
    }
    
    public Psicologo update (Psicologo p, String senhaAtual, String novaSenha, String confirmarNovaSenha){
        //Verifica se já existe
        Psicologo obj = findById(p.getId());
        //Verifica alteração da senha
        alterarSenha(obj, senhaAtual, novaSenha, confirmarNovaSenha);
        try{
            p.setCpf(obj.getCpf());
            return repo.save(p);
        }catch(Exception e){
            throw new RuntimeException("Falha ao atualizar o Psicólogo.");
        }
    }
    
    public void delete(Long id){
        Psicologo obj = findById(id);
        verificaExclusaoPsicologoComConsultas(obj);
        try{
            repo.delete(obj);
        }catch(Exception e){
            throw new RuntimeException("Falha ao excluir Psicólogo.");
        }
    }
    
    private void alterarSenha(Psicologo obj, String senhaAtual, String novaSenha, String confirmarNovaSenha){
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
    
    private void verificaCpfCadastrado(String cpf){
        List<Pessoa> result = repo.findByCpf(cpf);
        if(!result.isEmpty()){
            throw new RuntimeException("CPF já cadastrado.");
        }
    }
    
    private void verificaExclusaoPsicologoComConsultas(Psicologo p){
   
        if(!p.getConsultas().isEmpty()){
            throw new RuntimeException("Psicólogo possui consultas. Não pode ser excluido.");
        }
    }
    
}
