package io.github.costa.library.service;

import io.github.costa.library.model.Autor;
import io.github.costa.library.model.GeneroLivro;
import io.github.costa.library.model.Livro;
import io.github.costa.library.repository.AutorRepository;
import io.github.costa.library.repository.LivroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;


    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("21fc95bf-858e-4479-a102-0f7c6c07e2af")).orElse(null);

        livro.setDataPublicacao(LocalDate.of(2023,06,8));

    }

    @Transactional
    public void executar(){
        Livro livro = criarLivro("teste3");
        Autor autor = criarAutor("teste3");
        livro.setAutor(autor);
        Livro livroSalvo = livroRepository.save(livro);
        System.out.println("Livro Criado" + livroSalvo);

        if (autor.getNome().equals("teste3")){
            throw new RuntimeException("Rollback!");
        }
    }


    public Autor criarAutor(String nome){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2002,3,6));
        return autor;
    }

    public Livro criarLivro(String titulo){
        Livro livro = new Livro();
        livro.setIsbn("90877-84874");
        livro.setPreco(BigDecimal.valueOf(90));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo(titulo);
        livro.setDataPublicacao(LocalDate.of(1980,1,2));
        return livro;
    }
}
