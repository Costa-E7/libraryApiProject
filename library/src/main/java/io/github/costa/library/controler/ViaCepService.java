package io.github.costa.library.controler;

import io.github.costa.library.dto.EnderecoRequestDTO;
import io.github.costa.library.dto.EnderecoSalvarDTO;
import io.github.costa.library.dto.EnderecoViaCepDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate;

    public ViaCepService() {
        this.restTemplate = new RestTemplate();
    }

    public EnderecoViaCepDTO buscar(String cep) {
        String cepFormatado = cep.replaceAll("\\D", "");
        String url = String.format("https://viacep.com.br/ws/%s/json/", cepFormatado);
        try {
            EnderecoViaCepDTO response = restTemplate.getForObject(url, EnderecoViaCepDTO.class);
            if (response == null || response.cep() == null) {
                throw new RuntimeException("CEP inválido ou não encontrado na API ViaCEP");
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao consultar ViaCEP: " + e.getMessage(), e);
        }
    }
}