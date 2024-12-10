package br.com.estudos.pessoa_api.mapper;

import br.com.estudos.pessoa_api.dto.PessoaDTO;
import br.com.estudos.pessoa_api.entitie.Pessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PessoaMapper {

    @Mapping(target = "id", ignore = true)
    Pessoa toPessoa(PessoaDTO pessoaDTO);

    PessoaDTO toPessoaDTO(Pessoa pessoa);
}
