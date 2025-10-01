package io.github.costa.library.service;

import io.github.costa.library.exceptions.OperacaoNaoPermitidaException;
import io.github.costa.library.model.Autor;
import io.github.costa.library.repository.AutorRepository;
import io.github.costa.library.repository.LivroRepository;
import io.github.costa.library.validator.AutorValidator;
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

    public Autor save(Autor autor) {
        validator.validar(autor);
        return repository.save(autor);
    }

    public void Update(Autor autor) {
        if (autor.getId() == null)
            throw new IllegalArgumentException("Para Atualizar é necessario que o autor já estaja salvo");
        validator.validar(autor);
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

}
