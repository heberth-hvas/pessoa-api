package br.com.estudos.pessoa_api.service;

import br.com.estudos.pessoa_api.dto.PessoaDTO;
import br.com.estudos.pessoa_api.entitie.Pessoa;
import br.com.estudos.pessoa_api.mapper.PessoaMapper;
import br.com.estudos.pessoa_api.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaMapper pessoaMapper;
    private final PessoaRepository pessoaRepository;

    @Transactional
    public PessoaDTO salvar(PessoaDTO pessoaDTO) {
        // Adicione logs ou verificações para garantir que o método não retorne null
        if (pessoaDTO == null) {
            throw new IllegalArgumentException("PessoaDTO não pode ser nulo");
        }

        // Converte DTO para entidade
        Pessoa pessoa = pessoaMapper.toPessoa(pessoaDTO);

        // Garante que os endereços tenham referência bidirecional
        pessoa.getEnderecos().forEach(endereco -> endereco.setPessoa(pessoa));

        // Salva a pessoa
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);

        // Converte de volta para DTO
        return pessoaMapper.toPessoaDTO(pessoaSalva);
    }

    public List<PessoaDTO> listarTodas() {
        var list = pessoaRepository.findAll();
        return list.stream().map(pessoaMapper::toPessoaDTO).toList();
    }

    public PessoaDTO buscarPorId(Long id) {
        return pessoaRepository.findById(id).map(pessoaMapper::toPessoaDTO).orElseThrow(()
                        -> new RuntimeException("Pessoa não encontrada"));
    }

    public PessoaDTO atualizar(Long id, PessoaDTO pessoaAtualizada) {
        Pessoa pessoa = pessoaRepository.getReferenceById(id);
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setTelefone(pessoaAtualizada.getTelefone());
        pessoa.setEmail(pessoaAtualizada.getEmail());
        var pessoaNew = pessoaRepository.save(pessoa);
        return pessoaMapper.toPessoaDTO(pessoaNew);
    }

    public void excluir(Long id) {
        pessoaRepository.deleteById(id);
    }
}
