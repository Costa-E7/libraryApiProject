package io.github.costa.library.repository.jpa;

import io.github.costa.library.model.jpa.Autor;
import io.github.costa.library.model.jpa.GeneroLivro;
import io.github.costa.library.model.jpa.Livro;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {
    List<Livro> findByTitulo(String titulo);
    Optional<Livro> findByIsbn(String isbn);

    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdernadoPorTituloAndPreco();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGeneroOrderBy(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String paramOrdenacao
    );

    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroOrderByPositionalParam(GeneroLivro generoLivro, String nomePropriedade);

    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 ")
    void updateDataPublicacao(LocalDate novaData);



    boolean existsByAutor(Autor autor);




}
