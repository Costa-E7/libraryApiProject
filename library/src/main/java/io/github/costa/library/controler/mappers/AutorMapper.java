package io.github.costa.library.controler.mappers;

import io.github.costa.library.controler.dto.AutorDTO;
import io.github.costa.library.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AutorMapper {
    public abstract Autor toEntity(AutorDTO dto) ;

    public  abstract  AutorDTO toDTO(Autor autor);
}
