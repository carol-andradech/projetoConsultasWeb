
package br.edu.iff.projetoConsultas.service;

import br.edu.iff.projetoConsultas.model.Paciente;
import br.edu.iff.projetoConsultas.model.Pessoa;
import br.edu.iff.projetoConsultas.repository.PacienteRepository;
import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository repo;
    
    public List<Paciente> findAll(int page, int size){
        Pageable p = PageRequest.of(page, size);
        return repo.findAll(p).toList();
    }
    
    public List<Paciente> findAll(){
        return repo.findAll();
    }
    
    public Paciente findById(Long id){
        Optional<Paciente> result = repo.findAllById(id);
        if(result.isEmpty()){
            throw new RuntimeException("Paciente não encontrado.");
        }
        return result.get();
    }
    
    public Paciente save(Paciente pa, MultipartFile file){
        //arquivo
        if(file!=null){
            if(file.isEmpty()){
                salvarArquivo(file, pa.getCpf()+".pdf");
                pa.setProntuario(pa.getCpf()+".pdf");
            }
        } else{
            pa.setProntuario(null);
        }
        //Verifica se cpf já existe
        verificaCpfCadastrado(pa.getCpf());
        try{
           return repo.save(pa);
        }catch(Exception e){
            throw new RuntimeException("Falha ao salvar Paciente.");
        }
    }
    
    public Paciente update(Paciente pa, MultipartFile file){
        //Paciente já existe
        Paciente obj = findById(pa.getId());
        //Arquivo
        if(file!=null){
            if(file.isEmpty()){
                salvarArquivo(file, pa.getCpf()+".pdf");
                pa.setProntuario(pa.getCpf()+".pdf");
            }
        } else{
            pa.setProntuario(null);
        }
        try{
            pa.setCpf(obj.getCpf());
            return repo.save(pa);
        }catch(Exception e){
            throw new RuntimeException("Falha ao atualizar o Paciente.");
        }
    }
    
    public void delete(Long id){
        Paciente obj = findById(id);
        //Verificar se há consultas
        verificaExclusaoPacienteComConsultas(obj);
        try{
            repo.delete(obj);
            if(obj.getProntuario() != null){
            Path caminho = Paths.get("uploads", obj.getProntuario());
            Files.deleteIfExists(caminho);
            }
        }catch(Exception e){
            throw new RuntimeException("Falha ao excluir Paciente.");
            }
    }
    
    private void salvarArquivo(MultipartFile file, String novoNome){
        if(file.getContentType().equals(MediaType.APPLICATION_PDF_VALUE)){
            Path dest = Paths.get("uploads", novoNome);
            try {
                file.transferTo(dest);
            } catch(IOException ex) {
                throw new RuntimeException("Falha ao enviar arquivo.");
            }
        }
        else{
            throw new RuntimeException("Arquivo deve ser do tipo PDF.");
        }
        
    }
    
    private void verificaCpfCadastrado(String cpf){
        List<Pessoa> result = repo.findByCpf(cpf);
        if(!result.isEmpty()){
            throw new RuntimeException("CPF já cadastrado.");
        }
    }
    
    private void verificaExclusaoPacienteComConsultas(Paciente pa){
   
        if(!pa.getConsultas().isEmpty()){
            throw new RuntimeException("Paciente possui consultas. Não pode ser excluido.");
        }
    }
}
