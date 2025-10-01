package io.github.costa.library.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "Campo obrigatorio")
        @Size(max = 100, min = 2, message = "Campo fora do tamanho padrão")
        String nome,
        @NotNull(message = "Campo obrigatorio")
        @Past(message = "Não pode ser uma data futura")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatorio")
        @Size(max = 50, min = 2, message = "Campo fora do tamanho padrão")
        String nacionalidade,

        UUID idEndereco
) {
}
