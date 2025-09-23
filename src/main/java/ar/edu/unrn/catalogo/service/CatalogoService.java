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

    public List<PeliculaInfoCatalogo> listarPeliculas() {
        return emf.createEntityManager().createQuery("from Pelicula", Pelicula.class)
            .getResultList()
            .stream()
            .map(Pelicula::toInfoCatalogo)
            .toList();
    }

    public Optional<PeliculaDetalle> buscarPorId(Long id) {
        var em = emf.createEntityManager();
        try {
            Pelicula p = em.find(Pelicula.class, id);
            if (p == null) return Optional.empty();
            return Optional.of(p.toDetalle());
        } finally {
            em.close();
        }
    }
}
