package ar.edu.unrn.catalogo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectorTest {

    @Test
    @DisplayName("Crear director válido retorna instancia correctamente inicializada")
    void crearDirectorValido_instanciaCorrecta() {
        // Setup y Ejercitación
        Director director = new Director("Steven Spielberg");
        // Verificación
        assertNotNull(director, "El director debería haberse creado correctamente");
    }

    @Test
    @DisplayName("Crear director con nombre vacío lanza excepción")
    void crearDirector_nombreVacio_lanzaExcepcion() {
        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> new Director(""));
        assertEquals(Director.ERROR_NOMBRE_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear director con nombre nulo lanza excepción")
    void crearDirector_nombreNulo_lanzaExcepcion() {
        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> new Director(null));
        assertEquals(Director.ERROR_NOMBRE_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }
}
