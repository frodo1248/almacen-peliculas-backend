package ar.edu.unrn.catalogo.model;

import jakarta.persistence.*;
import java.util.Collections;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import ar.edu.unrn.catalogo.web.PeliculaInfoCatalogo;
import ar.edu.unrn.catalogo.web.PeliculaDetalle;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
@Entity
public class Pelicula {
    static final String ERROR_TITULO_VACIO = "El título no puede ser vacío";
    static final String ERROR_ANIO_INVALIDO = "El año debe ser mayor que 1800";
    static final String ERROR_PRECIO_INVALIDO = "El precio debe ser positivo";
    static final String ERROR_DIRECTOR_NULO = "El director no puede ser nulo";
    static final String ERROR_ACTORES_VACIO = "Debe haber al menos un actor";
    static final String ERROR_CONDICION_VACIA = "La condición no puede ser vacía";
    static final String ERROR_FORMATO_VACIO = "El formato no puede ser vacío";
    static final String ERROR_GENERO_VACIO = "El género no puede ser vacío";
    static final String ERROR_SINOPSIS_VACIA = "La sinopsis no puede ser vacía";
    static final String ERROR_IMAGEN_VACIA = "La imagen no puede ser vacía";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private int anio;
    @Column(name = "precio", nullable = false)
    private double precio;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Director director;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "pelicula_actor",
        joinColumns = @JoinColumn(name = "pelicula_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actores;
    private String condicion;
    private String formato;
    private String genero;
    private String sinopsis;
    private String imagen;

    public Pelicula(String titulo, int anio, double precio, Director director, List<Actor> actores,
                    String condicion, String formato, String genero, String sinopsis, String imagen) {
        assertTituloNoVacio(titulo);
        assertAnioValido(anio);
        assertPrecioValido(precio);
        assertDirectorNoNulo(director);
        assertActoresNoVacio(actores);
        assertCondicionNoVacia(condicion);
        assertFormatoNoVacio(formato);
        assertGeneroNoVacio(genero);
        assertSinopsisNoVacia(sinopsis);
        assertImagenNoVacia(imagen);
        this.titulo = titulo;
        this.anio = anio;
        this.precio = precio;
        this.director = director;
        this.actores = List.copyOf(actores);
        this.condicion = condicion;
        this.formato = formato;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }

    // Métodos de acceso package-private para el controlador
    public Long id() { return id; }
    public String titulo() { return titulo; }
    int anio() { return anio; }
    public double precio() { return precio; }
    Director director() { return director; }
    List<Actor> actores() { return Collections.unmodifiableList(actores); }
    String condicion() { return condicion; }
    String formato() { return formato; }
    String genero() { return genero; }
    String sinopsis() { return sinopsis; }
    String imagen() { return imagen; }


    public PeliculaInfoCatalogo toInfoCatalogo() {
        return new PeliculaInfoCatalogo(
            id,
            titulo,
            anio,
            precio,
            director.nombre(),
            actores.stream().map(Actor::nombre).toList(),
            imagen
        );
    }

    public PeliculaDetalle toDetalle() {
        return new PeliculaDetalle(
            id,
            titulo,
            anio,
            precio,
            director.nombre(),
            actores.stream().map(Actor::nombre).toList(),
            condicion,
            formato,
            genero,
            sinopsis,
            imagen
        );
    }

    private void assertTituloNoVacio(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new RuntimeException(ERROR_TITULO_VACIO);
        }
    }
    private void assertAnioValido(int anio) {
        if (anio <= 1800) {
            throw new RuntimeException(ERROR_ANIO_INVALIDO);
        }
    }
    private void assertPrecioValido(double precio) {
        if (precio <= 0) {
            throw new RuntimeException(ERROR_PRECIO_INVALIDO);
        }
    }
    private void assertDirectorNoNulo(Director director) {
        if (director == null) {
            throw new RuntimeException(ERROR_DIRECTOR_NULO);
        }
    }
    private void assertActoresNoVacio(List<Actor> actores) {
        if (actores == null || actores.isEmpty()) {
            throw new RuntimeException(ERROR_ACTORES_VACIO);
        }
    }
    private void assertCondicionNoVacia(String condicion) {
        if (condicion == null || condicion.isBlank()) {
            throw new RuntimeException(ERROR_CONDICION_VACIA);
        }
    }
    private void assertFormatoNoVacio(String formato) {
        if (formato == null || formato.isBlank()) {
            throw new RuntimeException(ERROR_FORMATO_VACIO);
        }
    }
    private void assertGeneroNoVacio(String genero) {
        if (genero == null || genero.isBlank()) {
            throw new RuntimeException(ERROR_GENERO_VACIO);
        }
    }
    private void assertSinopsisNoVacia(String sinopsis) {
        if (sinopsis == null || sinopsis.isBlank()) {
            throw new RuntimeException(ERROR_SINOPSIS_VACIA);
        }
    }
    private void assertImagenNoVacia(String imagen) {
        if (imagen == null || imagen.isBlank()) {
            throw new RuntimeException(ERROR_IMAGEN_VACIA);
        }
    }
}
