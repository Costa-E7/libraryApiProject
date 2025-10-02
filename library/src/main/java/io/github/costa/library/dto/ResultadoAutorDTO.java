package io.github.costa.library.dto;

import io.github.costa.library.model.Endereco;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoAutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalidade,
        EnderecoSalvarDTO endereco
) {
}
