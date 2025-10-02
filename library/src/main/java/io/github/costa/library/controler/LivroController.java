package io.github.costa.library.controler;


import io.github.costa.library.controler.mappers.LivroMapper;
import io.github.costa.library.dto.CadastroLivroDTO;
import io.github.costa.library.dto.ResultadoPesquisaLivroDTO;
import io.github.costa.library.model.jpa.GeneroLivro;
import io.github.costa.library.model.jpa.Livro;
import io.github.costa.library.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.save(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> getOne(
            @PathVariable("id") String id){
             return service.obterPorId(id)
                     .map(livro ->{
                         ResultadoPesquisaLivroDTO resultado = mapper.toDTO(livro);
                         return  ResponseEntity.ok().body(resultado);
                     }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(
            @PathVariable String  id)
    {
        return service.obterPorId(id)
                .map(livro -> {
                    service.delete(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero-livro", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0", required = false)
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "10", required = false)
            Integer tamanhoPagina
    ){
        Page<Livro> paginaResultado  = service.search(isbn, titulo, nomeAutor, genero, anoPublicacao,pagina, tamanhoPagina);
        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(mapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,
            @RequestBody @Valid CadastroLivroDTO dto
    ){
        return service.obterPorId(id)
                .map(livro -> {
                  Livro entidadeAux = mapper.toEntity(dto);

                  livro.setDataPublicacao(entidadeAux.getDataPublicacao());
                  livro.setIsbn(entidadeAux.getIsbn());
                  livro.setPreco(entidadeAux.getPreco());
                  livro.setGenero(entidadeAux.getGenero());
                  livro.setTitulo(entidadeAux.getTitulo());
                  livro.setAutor(entidadeAux.getAutor());

                  service.update(livro);
                  return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
