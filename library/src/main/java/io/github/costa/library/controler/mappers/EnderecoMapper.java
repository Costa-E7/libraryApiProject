package io.github.costa.library.controler.mappers;

import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.model.Endereco;
import io.github.costa.library.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EnderecoMapper {
    
    public abstract Endereco toEntity(EnderecoSalvarDTO dto);

    public abstract EnderecoSalvarDTO toDTO(Endereco endereco);
}
