package ar.edu.unrn.catalogo.service;

import ar.edu.unrn.catalogo.model.Pelicula;
import ar.edu.unrn.catalogo.web.PeliculaInfoCatalogo;
import ar.edu.unrn.catalogo.web.PeliculaDetalle;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Optional;

public class CatalogoService {
    private final EntityManagerFactory emf;

    public CatalogoService(EntityManagerFactory emf) {
        this.emf = emf;
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
}
