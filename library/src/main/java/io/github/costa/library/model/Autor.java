package io.github.costa.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Data
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class)
public class Autor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento",  nullable = false)
    private LocalDate dataNascimento;


    @Column(name = "nacionalidade", length = 50,  nullable = false)
    private String nacionalidade;

    @JsonManagedReference
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Livro> livros;

    @OneToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @CreatedDate
    @Column(name = "data_cadastro", updatable = false, nullable = false)
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

}
