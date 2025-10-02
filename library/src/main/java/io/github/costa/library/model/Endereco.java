package io.github.costa.library.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table
@Data
public class Endereco {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "cep", length = 15)
    private String cep;

    @Column(name = "complemento", length = 50)
    private String complemento;

    @Column(name = "bairro", length = 200)
    private String bairro;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "estado", length = 100, nullable = false)
    private String estado;

    @Column(name = "numero", length = 10, nullable = false)
    private  String numero;

    @OneToOne(mappedBy = "endereco")
    @JsonBackReference
    @ToString.Exclude
    private Autor autor;

}
