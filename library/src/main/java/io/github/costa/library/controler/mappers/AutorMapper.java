package io.github.costa.library.controler.mappers;

import io.github.costa.library.dto.AutorDTO;
import io.github.costa.library.model.Autor;
import io.github.costa.library.repository.EnderecoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AutorMapper {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Mapping(target = "endereco", expression = "java( enderecoRepository.findById(dto.idEndereco()).orElse(null))")
    public abstract Autor toEntity(AutorDTO dto) ;

    public  abstract  AutorDTO toDTO(Autor autor);
}
