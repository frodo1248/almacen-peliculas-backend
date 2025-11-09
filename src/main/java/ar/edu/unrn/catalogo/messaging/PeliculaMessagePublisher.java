package ar.edu.unrn.catalogo.messaging;

public interface PeliculaMessagePublisher {
    void publicarPeliculaAgregada(Long id, String nombre, Double precio);
}

