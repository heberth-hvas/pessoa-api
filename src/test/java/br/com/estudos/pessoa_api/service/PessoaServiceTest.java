package br.com.estudos.pessoa_api.service;

import br.com.estudos.pessoa_api.dto.EnderecoDTO;
import br.com.estudos.pessoa_api.dto.PessoaDTO;
import br.com.estudos.pessoa_api.entitie.Endereco;
import br.com.estudos.pessoa_api.entitie.Pessoa;
import br.com.estudos.pessoa_api.mapper.PessoaMapper;
import br.com.estudos.pessoa_api.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(pessoaService, "pessoaMapper", pessoaMapper);
    }

    @Test
    void deveSalvarPessoaComEnderecoComSucesso() {
        // Arrange
        EnderecoDTO enderecoDTO = new EnderecoDTO(
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste",
                "12345-678"
        );

        PessoaDTO pessoaDTO = new PessoaDTO(
                "Teste",
                "1234",
                "teste@teste.com",
                "4321",
                List.of(enderecoDTO)
        );

        Endereco endereco = new Endereco(
                null,
                "Rua Teste",
                "Bairro Teste",
                "Cidade Teste",
                "Estado Teste",
                "12345-678",
                null
        );

        Pessoa pessoa = new Pessoa(
                null,
                "Teste",
                "4321",
                "1234",
                "teste@teste.com",
                List.of(endereco)
        );

        // Configuração mais explícita dos mocks
        when(pessoaMapper.toPessoa(any(PessoaDTO.class))).thenReturn(pessoa);
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);
        when(pessoaMapper.toPessoaDTO(any(Pessoa.class))).thenReturn(pessoaDTO);

        // Act
        PessoaDTO resultado = pessoaService.salvar(pessoaDTO);

        // Assert
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(pessoaDTO.getNome(), resultado.getNome());
        assertFalse(resultado.getEnderecos().isEmpty());
        assertEquals(1, resultado.getEnderecos().size());

        EnderecoDTO enderecoResultado = resultado.getEnderecos().get(0);
        assertEquals(enderecoDTO.getLogradouro(), enderecoResultado.getLogradouro());
        assertEquals(enderecoDTO.getCep(), enderecoResultado.getCep());

        // Verificações de chamada de métodos
        verify(pessoaRepository).save(any(Pessoa.class));
        verify(pessoaMapper).toPessoa(any(PessoaDTO.class));
        verify(pessoaMapper).toPessoaDTO(any(Pessoa.class));
    }
}
