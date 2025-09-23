package ar.edu.unrn.catalogo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaTest {

    @Test
    @DisplayName("Crear película válida retorna instancia correctamente inicializada")
    void crearPeliculaValida_instanciaCorrecta() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        // Ejercitación: crear película
        Pelicula pelicula = new Pelicula(
                "Inception", 2010, 148, director, List.of(actor),
                "Nueva", "Blu-ray", "Ciencia Ficción", "Un ladrón roba sueños.", "imagen.jpg"
        );
        // Verificación: la instancia no es nula
        assertNotNull(pelicula, "La película debería haberse creado correctamente");
        assertEquals(1, pelicula.actores().size(), "La cantidad de actores debería ser 1");
        assertEquals("Nueva", pelicula.condicion(), "La condición debe coincidir");
        assertEquals("Blu-ray", pelicula.formato(), "El formato debe coincidir");
        assertEquals("Ciencia Ficción", pelicula.genero(), "El género debe coincidir");
        assertEquals("Un ladrón roba sueños.", pelicula.sinopsis(), "La sinopsis debe coincidir");
        assertEquals("imagen.jpg", pelicula.imagen(), "La imagen debe coincidir");
    }

    @Test
    @DisplayName("Crear película con título vacío lanza excepción")
    void crearPelicula_tituloVacio_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        // Ejercitación y Verificación: assertThrows por título vacío
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("", 2010, 148, director, List.of(actor), "Nueva", "Blu-ray", "Ciencia Ficción", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_TITULO_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con año inválido lanza excepción")
    void crearPelicula_anioInvalido_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        // Ejercitación y Verificación: assertThrows por año inválido
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 1700, 148, director, List.of(actor), "Nueva", "Blu-ray", "Ciencia Ficción", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_ANIO_INVALIDO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con director nulo lanza excepción")
    void crearPelicula_directorNulo_lanzaExcepcion() {
        // Setup: preparar actores
        Actor actor = new Actor("Leonardo DiCaprio");
        // Ejercitación y Verificación: assertThrows por director nulo
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, null, List.of(actor), "Nueva", "Blu-ray", "Ciencia Ficción", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_DIRECTOR_NULO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con lista de actores vacía lanza excepción")
    void crearPelicula_actoresVacio_lanzaExcepcion() {
        // Setup: preparar director
        Director director = new Director("Christopher Nolan");
        // Ejercitación y Verificación: assertThrows por lista de actores vacía
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, director, List.of(), "Nueva", "Blu-ray", "Ciencia Ficción", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_ACTORES_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Obtener lista de actores es solo lectura")
    void actores_listaSoloLectura() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        Pelicula pelicula = new Pelicula("Inception", 2010, 148, director, List.of(actor), "Nueva", "Blu-ray", "Ciencia Ficción", "Sinopsis", "img.jpg");
        // Ejercitación y Verificación: intentar modificar la lista debe lanzar excepción
        var actores = pelicula.actores();
        assertThrows(UnsupportedOperationException.class, () -> actores.add(new Actor("Otro Actor")),
                "La lista de actores debe ser inmutable");
    }

    @Test
    @DisplayName("Crear película con condición vacía lanza excepción")
    void crearPelicula_condicionVacia_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, director, List.of(actor), "", "Blu-ray", "Ciencia Ficción", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_CONDICION_VACIA, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con formato vacío lanza excepción")
    void crearPelicula_formatoVacio_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, director, List.of(actor), "Nueva", "", "Ciencia Ficción", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_FORMATO_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con género vacío lanza excepción")
    void crearPelicula_generoVacio_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, director, List.of(actor), "Nueva", "Blu-ray", "", "Sinopsis", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_GENERO_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con sinopsis vacía lanza excepción")
    void crearPelicula_sinopsisVacia_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, director, List.of(actor), "Nueva", "Blu-ray", "Ciencia Ficción", "", "img.jpg")
        );
        assertEquals(Pelicula.ERROR_SINOPSIS_VACIA, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear película con imagen vacía lanza excepción")
    void crearPelicula_imagenVacia_lanzaExcepcion() {
        // Setup: preparar actores y director
        Actor actor = new Actor("Leonardo DiCaprio");
        Director director = new Director("Christopher Nolan");
        var ex = assertThrows(RuntimeException.class, () ->
            new Pelicula("Inception", 2010, 148, director, List.of(actor), "Nueva", "Blu-ray", "Ciencia Ficción", "Sinopsis", "")
        );
        assertEquals(Pelicula.ERROR_IMAGEN_VACIA, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }
}
