package br.com.estudos.pessoa_api.service;

import br.com.estudos.pessoa_api.dto.EnderecoDTO;
import br.com.estudos.pessoa_api.entitie.Endereco;
import br.com.estudos.pessoa_api.mapper.EnderecoMapper;
import br.com.estudos.pessoa_api.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    @Autowired
    private EnderecoMapper enderecoMapper;

    private final EnderecoRepository enderecoRepository;

    public EnderecoDTO salvar(EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoMapper.toEndereco(enderecoDTO);
        return enderecoMapper.toEnderecoDTO(enderecoRepository.save(endereco));
    }

    public List<EnderecoDTO> listarTodos() {
        var list = enderecoRepository.findAll();
        return list.stream().map(enderecoMapper::toEnderecoDTO).toList();
    }

    public EnderecoDTO buscarPorId(Long id) {
        return enderecoRepository.findById(id).map(enderecoMapper::toEnderecoDTO).orElseThrow(()
                -> new RuntimeException("Endereço não encontrado"));
    }

    public EnderecoDTO atualizar(Long id, EnderecoDTO enderecoAtualizado) {
        Endereco endereco = enderecoRepository.getReferenceById(id);
        endereco.setCep(enderecoAtualizado.getCep());
        endereco.setLogradouro(enderecoAtualizado.getLogradouro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setPais(enderecoAtualizado.getPais());
        var enderecoNew = enderecoRepository.save(endereco);
        return enderecoMapper.toEnderecoDTO(enderecoNew);
    }

    public void excluir(Long id) {
        enderecoRepository.deleteById(id);
    }
}
