package io.github.costa.library.validator;

import io.github.costa.library.exceptions.RegistroDuplicadoException;
import io.github.costa.library.model.jpa.Autor;
import io.github.costa.library.repository.jpa.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {
    private AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validate(Autor autor){
        if (exist(autor)){
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    private boolean exist(Autor autor){
        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()
        );

        if (autor.getId() == null) return autorEncontrado.isPresent();

        return autorEncontrado.isPresent() && !autor.getId().equals(autorEncontrado.get().getId());
    }
}
