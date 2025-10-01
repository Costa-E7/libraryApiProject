package io.github.costa.library.controler.mappers;

import io.github.costa.library.dto.CadastroLivroDto;
import io.github.costa.library.dto.ResultadoPesquisaLivroDTO;
import io.github.costa.library.model.Livro;
import io.github.costa.library.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract  class LivroMapper {
    @Autowired
    AutorRepository autorRepository;
//
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null ))")
    public abstract Livro toEntity(CadastroLivroDto dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
