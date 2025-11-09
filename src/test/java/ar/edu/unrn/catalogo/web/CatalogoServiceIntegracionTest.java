package ar.edu.unrn.catalogo.web;

import ar.edu.unrn.catalogo.messaging.PeliculaMessagePublisher;
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
    private PeliculaMessagePublisher messagePublisher;

    // Mock simple para testing - no envía mensajes reales
    private static class MockPeliculaMessagePublisher implements PeliculaMessagePublisher {
        private Long ultimoId;
        private String ultimoNombre;
        private Double ultimoPrecio;
        private int cantidadLlamadas = 0;

        @Override
        public void publicarPeliculaAgregada(Long id, String nombre, Double precio) {
            this.ultimoId = id;
            this.ultimoNombre = nombre;
            this.ultimoPrecio = precio;
            this.cantidadLlamadas++;
        }

        public void reset() {
            this.ultimoId = null;
            this.ultimoNombre = null;
            this.ultimoPrecio = null;
            this.cantidadLlamadas = 0;
        }
    }

    @BeforeEach
    void beforeEach() {
        emf = new EmfBuilder()
                .memory()
                .addClass(Pelicula.class)
                .addClass(Actor.class)
                .addClass(Director.class)
                .build();
        emf.getSchemaManager().truncate();
        messagePublisher = new MockPeliculaMessagePublisher();
        service = new CatalogoService(emf, messagePublisher);
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

    @Test
    @DisplayName("Crear película nueva funciona correctamente")
    void crearPeliculaNueva_funcionaCorrectamente() {
        // Setup: crear DTO con datos de película
        PeliculaNueva peliculaNueva = new PeliculaNueva(
                "Matrix", 1999, 1500.0, "Hermanos Wachowski",
                List.of("Keanu Reeves", "Laurence Fishburne"),
                "Usada", "Blu-ray", "Ciencia Ficción",
                "Un programador descubre la realidad.", "matrix.jpg");

        // Ejercitación: crear película
        PeliculaDetalle resultado = service.crearPelicula(peliculaNueva);

        // Verificación
        assertNotNull(resultado.id(), "La película debe tener un ID asignado");
        assertEquals("Matrix", resultado.titulo(), "El título debe coincidir");
        assertEquals(1999, resultado.anio(), "El año debe coincidir");
        assertEquals(1500.0, resultado.precio(), "El precio debe coincidir");
        assertEquals("Hermanos Wachowski", resultado.director(), "El director debe coincidir");
        assertEquals(2, resultado.actores().size(), "Debe tener 2 actores");
        assertTrue(resultado.actores().contains("Keanu Reeves"), "Debe contener a Keanu Reeves");
        assertTrue(resultado.actores().contains("Laurence Fishburne"), "Debe contener a Laurence Fishburne");
        assertEquals("Usada", resultado.condicion(), "La condición debe coincidir");
        assertEquals("Blu-ray", resultado.formato(), "El formato debe coincidir");
        assertEquals("Ciencia Ficción", resultado.genero(), "El género debe coincidir");
        assertEquals("Un programador descubre la realidad.", resultado.sinopsis(), "La sinopsis debe coincidir");
        assertEquals("matrix.jpg", resultado.imagen(), "La imagen debe coincidir");
    }

    @Test
    @DisplayName("Crear película con título vacío lanza excepción")
    void crearPeliculaConTituloVacio_lanzaExcepcion() {
        // Setup: crear DTO con título vacío
        PeliculaNueva peliculaNueva = new PeliculaNueva(
                "", 1999, 1500.0, "Director",
                List.of("Actor"), "Nueva", "DVD", "Acción",
                "Sinopsis", "imagen.jpg");

        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> {
            service.crearPelicula(peliculaNueva);
        });
        assertEquals("El título no puede ser vacío", ex.getMessage(),
                "Debe lanzar excepción por título vacío");
    }

    @Test
    @DisplayName("Crear película con año inválido lanza excepción")
    void crearPeliculaConAnioInvalido_lanzaExcepcion() {
        // Setup: crear DTO con año inválido
        PeliculaNueva peliculaNueva = new PeliculaNueva(
                "Película", 1500, 1500.0, "Director",
                List.of("Actor"), "Nueva", "DVD", "Acción",
                "Sinopsis", "imagen.jpg");

        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> {
            service.crearPelicula(peliculaNueva);
        });
        assertEquals("El año debe ser mayor que 1800", ex.getMessage(),
                "Debe lanzar excepción por año inválido");
    }

    @Test
    @DisplayName("Crear película con precio inválido lanza excepción")
    void crearPeliculaConPrecioInvalido_lanzaExcepcion() {
        // Setup: crear DTO con precio inválido
        PeliculaNueva peliculaNueva = new PeliculaNueva(
                "Película", 1999, -100.0, "Director",
                List.of("Actor"), "Nueva", "DVD", "Acción",
                "Sinopsis", "imagen.jpg");

        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> {
            service.crearPelicula(peliculaNueva);
        });
        assertEquals("El precio debe ser positivo", ex.getMessage(),
                "Debe lanzar excepción por precio inválido");
    }

    @Test
    @DisplayName("Crear película sin actores lanza excepción")
    void crearPeliculaSinActores_lanzaExcepcion() {
        // Setup: crear DTO sin actores
        PeliculaNueva peliculaNueva = new PeliculaNueva(
                "Película", 1999, 1500.0, "Director",
                List.of(), "Nueva", "DVD", "Acción",
                "Sinopsis", "imagen.jpg");

        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> {
            service.crearPelicula(peliculaNueva);
        });
        assertEquals("Debe haber al menos un actor", ex.getMessage(),
                "Debe lanzar excepción por lista de actores vacía");
    }
}

