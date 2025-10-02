package io.github.costa.library.dto;

import io.github.costa.library.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoSalvarDTO(

        @NotBlank(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido")
        String cep,

        @NotBlank(message = "Número da casa é obrigatório")
        String numero,

        @NotBlank(message = "Complemento é obrigatório")
        String complemento,

        @NotBlank(message = "Bairro é obrigatório")
        String bairro,

        @NotBlank(message = "Cidade é obrigatório")
        String cidade,

        @NotBlank(message = "UF é obrigatório")
        String uf,

        String estado // opcional
) { }