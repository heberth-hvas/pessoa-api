package br.com.estudos.pessoa_api.mapper;

import br.com.estudos.pessoa_api.dto.EnderecoDTO;
import br.com.estudos.pessoa_api.entitie.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(target = "id", ignore = true)
    Endereco toEndereco(EnderecoDTO enderecoDTO);

    EnderecoDTO toEnderecoDTO(Endereco endereco);
}
