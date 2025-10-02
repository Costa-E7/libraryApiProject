package io.github.costa.library.controler.mappers;

import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.dto.EnderecoViaCepDTO;
import io.github.costa.library.model.jpa.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class EnderecoMapper {
    
    public abstract Endereco toEntity(EnderecoSalvarDTO dto);

    public abstract EnderecoSalvarDTO toDTO(Endereco endereco);

    @Mapping(target = "numero", source = "numero")
    @Mapping(target = "complemento", source = "complemento")
    @Mapping(target = "cidade", source = "endereco.localidade")
    public abstract EnderecoSalvarDTO toDTO (EnderecoViaCepDTO endereco,String numero, String complemento);
}
