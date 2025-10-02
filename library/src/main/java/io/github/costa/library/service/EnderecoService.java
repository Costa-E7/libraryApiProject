package io.github.costa.library.service;

import io.github.costa.library.controler.ViaCepService;
import io.github.costa.library.controler.mappers.EnderecoMapper;
import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.dto.EnderecoViaCepDTO;
import io.github.costa.library.model.Endereco;
import io.github.costa.library.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final ViaCepService viaCepService;
    private final EnderecoMapper mapper;
    private final EnderecoRepository repository;

    public EnderecoSalvarDTO createAddress(String cep, String numero, String complemento) {
        EnderecoViaCepDTO endersoViaCep = viaCepService.buscar(cep);
        return  mapper.toDTO(endersoViaCep, numero, complemento);

    }
    public Endereco salvar(Endereco endereco) {
        return repository.save(endereco);
    }

    public void delete(Endereco endereco) {
        repository.delete(endereco);
    }
}
