package io.github.costa.library.controler;

import io.github.costa.library.dto.EnderecoRequestDTO;
import io.github.costa.library.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EnderecoController implements GenericController {
    private final AutorService autorService;

    @PutMapping("/autor/{autorId}/endereco")
    public ResponseEntity<Void> updateAddress(
            @PathVariable("autorId") String autorId,
            @RequestBody @Valid EnderecoRequestDTO dto
    ) {
        autorService.updateAdres(autorId,  dto);
        return ResponseEntity.noContent().build();
    }
}
