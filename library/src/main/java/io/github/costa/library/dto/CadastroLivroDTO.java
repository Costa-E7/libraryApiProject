package io.github.costa.library.dto;

import io.github.costa.library.model.jpa.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import org.mapstruct.EnumMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(
        @ISBN
        @NotBlank(message = "campo Obrigatorio")
        String isbn,
        @NotBlank(message = "campo Obrigatorio")
        String titulo,
        @NotNull(message = "campo Obrigatorio")
        @Past(message = "nao pode ser uma data futura ")
        LocalDate dataPublicacao,
        @EnumMapping
        GeneroLivro genero,
        BigDecimal preco,
        @NotNull(message = "campo Obrigatorio")
        UUID idAutor
) {
}
