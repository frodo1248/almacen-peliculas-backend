package ar.edu.unrn.catalogo.messaging;

public record PeliculaAgregadaEvent(
    Long id,
    String nombre,
    double precio
) {
}

