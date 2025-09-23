package ar.edu.unrn.catalogo.web;

import ar.edu.unrn.catalogo.model.Actor;
import ar.edu.unrn.catalogo.model.Director;
import ar.edu.unrn.catalogo.model.Pelicula;
import ar.edu.unrn.catalogo.service.CatalogoService;
import ar.edu.unrn.catalogo.utils.EmfBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoServiceIntegracionTest {
    private EntityManagerFactory emf;
    private CatalogoService service;

    @BeforeEach
    void beforeEach() {
        emf = new EmfBuilder()
                .memory()
                .addClass(Pelicula.class)
                .addClass(Actor.class)
                .addClass(Director.class)
                .build();
        emf.getSchemaManager().truncate();
        service = new CatalogoService(emf);
    }

    @Test
    @DisplayName("Guardar y recuperar película funciona correctamente")
    void guardarYRecuperarPelicula_funcionaCorrectamente() {
        // Setup: crear y persistir una película
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Director director = new Director("Spielberg");
        Actor actor = new Actor("Tom Hanks");
        Pelicula pelicula = new Pelicula(
                "Rescatando al Soldado Ryan", 1998, 1000.0, director,
                List.of(actor), "Nueva", "DVD", "Bélica", "Un escuadrón rescata a un soldado.", "imagen.jpg");
        em.persist(director);
        em.persist(actor);
        em.persist(pelicula);
        em.getTransaction().commit();
        em.close();

        // Ejercitación: obtener catálogo y detalle
        List<PeliculaInfoCatalogo> catalogo = service.listarPeliculas();
        PeliculaDetalle detalle = service.buscarPorId(catalogo.get(0).id()).orElseThrow();

        // Verificación
        assertEquals(1, catalogo.size(), "Debe haber una película en el catálogo");
        assertEquals("Rescatando al Soldado Ryan", detalle.titulo(), "El título debe coincidir");
        assertEquals("Spielberg", detalle.director(), "El director debe coincidir");
        assertTrue(detalle.actores().contains("Tom Hanks"), "Debe contener al actor Tom Hanks");
    }
}

