package io.github.costa.library.service;

import io.github.costa.library.controler.mappers.AutorMapper;
import io.github.costa.library.controler.mappers.EnderecoMapper;
import io.github.costa.library.dto.AutorDTO;
import io.github.costa.library.dto.EnderecoRequestDTO;
import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.exceptions.OperacaoNaoPermitidaException;
import io.github.costa.library.model.Autor;
import io.github.costa.library.model.Endereco;
import io.github.costa.library.repository.AutorRepository;
import io.github.costa.library.repository.LivroRepository;
import io.github.costa.library.validator.AutorValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final EnderecoService enderecoService;
    private final AutorMapper mapper;
    private final EnderecoMapper enderecoMapper;

    @Transactional
    public Autor save(AutorDTO autorDTO) {
        EnderecoSalvarDTO enderecoSalvarDTO = enderecoService.createAddress(
                autorDTO.cep(),
                autorDTO.numeroEndereco(),
                autorDTO.complementoEndereco());

        Endereco endereco = enderecoMapper.toEntity(enderecoSalvarDTO);
        Autor autor = mapper.toEntity(autorDTO, endereco);
        validator.validate(autor);
        return repository.save(autor);
    }

    public void Update(Autor autor, Endereco endereco) {
        if (autor.getId() == null)
            throw new IllegalArgumentException("Para Atualizar é necessario que o autor já estaja salvo");
        validator.validate(autor);
        autor.setEndereco(endereco);
        repository.save(autor);
    }

    public Optional<Autor> getOne(String id) {
        UUID idAutor = UUID.fromString(id);

        return repository.findById(idAutor);
    }

    public void delete(Autor autor) {
        if (possuiLivro(autor)) {
            throw new OperacaoNaoPermitidaException(
                    "Não é possivel excluir autor possui livros cadastrados!"
            );
        }
        repository.delete(autor);
        enderecoService.delete(autor.getEndereco());
    }

    //busca usando o example do jpa.
    // o example paga uma classe, e faz a query com os valores preenchidos, por exemplo:
    // autor tem nome nacionalidade e data de nascimento, se ele tiver preenchido so o nome, ele so vai inserir o nome
    // na query
    public List<Autor> search(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id","dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor) {
        return livroRepository.existsByAutor(autor);
    }

    public void updateAdres(String autorId, EnderecoRequestDTO dto) {
        Optional<Autor> autor = this.getOne(autorId);
        if (autor.isEmpty())
            throw new OperacaoNaoPermitidaException("Autor inexistente para ter endereço atualizado");

        enderecoService.update(dto, autor.get().getEndereco().getId());


    }
}
