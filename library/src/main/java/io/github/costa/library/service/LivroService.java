package io.github.costa.library.service;

import io.github.costa.library.model.jpa.GeneroLivro;
import io.github.costa.library.model.jpa.Livro;
import io.github.costa.library.repository.jpa.LivroRepository;
import io.github.costa.library.validator.LivroValidador;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import static io.github.costa.library.repository.specs.LivrosSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidador validador;

    public Livro save(Livro livro) {
        validador.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(String idString) {
        UUID id = UUID.fromString(idString);
        return repository.findById(id);
    }

    public void delete(Livro livro) {
        repository.delete(livro);
    }

    public Page<Livro> search(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro generoLivro,
            Integer anoPublicacao,
            Integer pagina,
            Integer tamanhoPagina) {


        Specification<Livro> specs = Specification.allOf();
        if (isbn != null) specs = specs.and(isbnEqual(isbn));
        if (titulo != null) specs = specs.and(tituloLike(titulo));
        if (generoLivro != null) specs = specs.and(generoEqual(generoLivro));
        if (anoPublicacao != null) specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        if (nomeAutor != null) specs = specs.and(nomeAutorLike(nomeAutor));

        Pageable page = PageRequest.of(pagina, tamanhoPagina);

        return repository.findAll(specs, page);
    }

    public void update(Livro livro) {
        if (livro.getId() == null)
            throw new IllegalArgumentException("Para Atualizar é necessario que o livro já esteja salvo");
        validador.validar(livro);
        repository.save(livro);

    }
}
