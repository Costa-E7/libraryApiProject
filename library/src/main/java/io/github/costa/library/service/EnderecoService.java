package io.github.costa.library.service;

import io.github.costa.library.controler.EnderecoController;
import io.github.costa.library.controler.ViaCepService;
import io.github.costa.library.controler.mappers.EnderecoMapper;
import io.github.costa.library.dto.EnderecoRequestDTO;
import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.dto.EnderecoViaCepDTO;
import io.github.costa.library.model.Endereco;
import io.github.costa.library.repository.EnderecoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final ViaCepService viaCepService;
    private final EnderecoMapper mapper;
    private final EnderecoRepository repository;

    public EnderecoSalvarDTO createAddress(String cep, String numero, String complemento) {
        return  viaCepService.buscar(cep, numero, complemento);
    }
    public void delete(Endereco endereco) {
        repository.delete(endereco);
    }

    public void update(EnderecoRequestDTO dto) {
        EnderecoSalvarDTO enderecoSalvarDTO = viaCepService.buscar(dto.cep(), dto.numeroEndereco(), dto.complemento());
        repository.save(mapper.toEntity(enderecoSalvarDTO));
    }
}
