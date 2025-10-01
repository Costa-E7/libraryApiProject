package io.github.costa.library.validator;

import io.github.costa.library.exceptions.RegistroDuplicadoException;
import io.github.costa.library.model.Autor;
import io.github.costa.library.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor){
        if (exist(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean exist(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if (autor.getId() == null) return autorEncontrado.isPresent();

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
