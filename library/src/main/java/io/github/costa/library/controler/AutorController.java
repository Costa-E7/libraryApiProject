package io.github.costa.library.controler;

import io.github.costa.library.controler.mappers.AutorMapper;
import io.github.costa.library.dto.AtualizacaoAutorDTO;
import io.github.costa.library.dto.ResultadoAutorDTO;
import io.github.costa.library.service.AutorService;
import io.github.costa.library.dto.AutorDTO;
import io.github.costa.library.model.jpa.Autor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("autores")
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AutorDTO autorDTO) {
        Autor autor = service.save(autorDTO);
        URI location = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoAutorDTO> getOne(@PathVariable("id") String id) {
        Optional<Autor> autorOptional = service.getOne(id);
        return service
                .getOne(id)
                .map(autor -> {
                    ResultadoAutorDTO dto = mapper.toResultadoDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable("id") String id) {
        Optional<Autor> autorOptional = service.getOne(id);
        if (autorOptional.isEmpty()) return ResponseEntity.notFound().build();
        service.delete(autorOptional.get());
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<List<ResultadoAutorDTO>> getByNomeAndNacionalidade(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = service.search(nome, nacionalidade);
        List<ResultadoAutorDTO> lista = resultado.stream().map(mapper::toResultadoDTO
        ).toList();
        return ResponseEntity.ok(lista);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") String id,
            @RequestBody @Valid AtualizacaoAutorDTO dto
    ) {
        Optional<Autor> autorOptional = service.getOne(id);
        if (autorOptional.isEmpty()) return ResponseEntity.notFound().build();
        Autor autor = mapper.toEntity(dto);
        autor.setId(UUID.fromString(id));
        service.Update(autor, autorOptional.get().getEndereco());
        return ResponseEntity.noContent().build();
    }
}
