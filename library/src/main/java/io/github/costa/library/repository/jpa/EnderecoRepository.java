package io.github.costa.library.repository.jpa;

import io.github.costa.library.model.jpa.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
