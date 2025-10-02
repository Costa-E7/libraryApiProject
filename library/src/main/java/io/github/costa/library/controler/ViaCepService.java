package io.github.costa.library.controler;

import io.github.costa.library.controler.mappers.AutorMapper;
import io.github.costa.library.controler.mappers.EnderecoMapper;
import io.github.costa.library.dto.EnderecoRequestDTO;
import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.dto.EnderecoViaCepDTO;
import io.github.costa.library.exceptions.OperacaoNaoPermitidaException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    private final RestTemplate restTemplate;
    private final   EnderecoMapper mapper;


    public EnderecoSalvarDTO buscar(String cep, String numero, String complemento) {
        String cepFormatado = cep.replaceAll("\\D", "");
        String url = String.format("https://viacep.com.br/ws/%s/json/", cepFormatado);
        try {
            EnderecoViaCepDTO response = restTemplate.getForObject(url, EnderecoViaCepDTO.class);
            if (response == null || response.cep() == null) {
                throw new OperacaoNaoPermitidaException("CEP inválido ou não encontrado na API ViaCEP");
            }

            return mapper.toDTO(response,numero, complemento);

        } catch (Exception e) {
            throw new OperacaoNaoPermitidaException("Erro ao consultar ViaCEP: " + e.getMessage());
        }
    }
}