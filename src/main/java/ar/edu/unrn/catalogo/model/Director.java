package ar.edu.unrn.catalogo.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Entity
public class Director {
    static final String ERROR_NOMBRE_VACIO = "El nombre del director no puede estar vacío";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    public Director(String nombre) {
        assertNombreValido(nombre);
        this.nombre = nombre;
    }

    String nombre() { return nombre; }
    Long id() { return id; }

    private void assertNombreValido(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new RuntimeException(ERROR_NOMBRE_VACIO);
        }
    }
}
