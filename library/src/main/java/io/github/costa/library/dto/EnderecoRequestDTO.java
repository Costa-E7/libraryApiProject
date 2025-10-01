package io.github.costa.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido. Use o formato 00000-000")
        String cep,

        @NotBlank(message = "Número da casa é obrigatório")
        String numeroEndereco,

        String complemento  // opcional
) { }
