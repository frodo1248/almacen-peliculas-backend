package ar.edu.unrn.catalogo.service;

import ar.edu.unrn.catalogo.messaging.PeliculaMessagePublisher;
import ar.edu.unrn.catalogo.model.Actor;
import ar.edu.unrn.catalogo.model.Director;
import ar.edu.unrn.catalogo.model.Pelicula;
import ar.edu.unrn.catalogo.web.PeliculaInfoCatalogo;
import ar.edu.unrn.catalogo.web.PeliculaDetalle;
import ar.edu.unrn.catalogo.web.PeliculaNueva;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class CatalogoService {
    private final EntityManagerFactory emf;
    private final PeliculaMessagePublisher messagePublisher;

    public CatalogoService(EntityManagerFactory emf, PeliculaMessagePublisher messagePublisher) {
        this.emf = emf;
        this.messagePublisher = messagePublisher;
    }
    // Listar todas las películas en orden descendente por año
    public List<PeliculaInfoCatalogo> listarPeliculas() {
        return emf.createEntityManager().createQuery("from Pelicula order by anio desc", Pelicula.class)
            .getResultList()
            .stream()
            .map(Pelicula::toInfoCatalogo)
            .toList();
    }

    public Optional<PeliculaDetalle> buscarPorId(Long id) {
        try (var em = emf.createEntityManager()) {
            Pelicula p = em.find(Pelicula.class, id);
            if (p == null) return Optional.empty();
            return Optional.of(p.toDetalle());
        }
    }

    public PeliculaDetalle crearPelicula(PeliculaNueva peliculaNueva) {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Crear el director
            Director director = new Director(peliculaNueva.director());

            // Crear los actores
            List<Actor> actores = peliculaNueva.actores().stream()
                .map(Actor::new)
                .toList();

            // Crear la película
            Pelicula pelicula = new Pelicula(
                peliculaNueva.titulo(),
                peliculaNueva.anio(),
                peliculaNueva.precio(),
                director,
                actores,
                peliculaNueva.condicion(),
                peliculaNueva.formato(),
                peliculaNueva.genero(),
                peliculaNueva.sinopsis(),
                peliculaNueva.imagen()
            );

            em.persist(pelicula);
            em.getTransaction().commit();

            // Publicar mensaje a RabbitMQ después de persistir exitosamente
            messagePublisher.publicarPeliculaAgregada(
                pelicula.id(),
                pelicula.titulo(),
                pelicula.precio()
            );

            return pelicula.toDetalle();
        }
    }
}
