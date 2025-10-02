package io.github.costa.library.dto;


public record EnderecoViaCepDTO(
        String cep,
        String logradouro,
        String bairro,
        String localidade, // cidade
        String uf,
        String estado
) { }
