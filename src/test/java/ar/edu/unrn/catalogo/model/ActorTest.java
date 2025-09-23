package ar.edu.unrn.catalogo.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {

    @Test
    @DisplayName("Crear actor válido retorna instancia correctamente inicializada")
    void crearActorValido_instanciaCorrecta() {
        // Setup y Ejercitación
        Actor actor = new Actor("Brad Pitt");
        // Verificación
        assertNotNull(actor, "El actor debería haberse creado correctamente");
    }

    @Test
    @DisplayName("Crear actor con nombre vacío lanza excepción")
    void crearActor_nombreVacio_lanzaExcepcion() {
        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> new Actor(""));
        assertEquals(Actor.ERROR_NOMBRE_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }

    @Test
    @DisplayName("Crear actor con nombre nulo lanza excepción")
    void crearActor_nombreNulo_lanzaExcepcion() {
        // Ejercitación y Verificación
        var ex = assertThrows(RuntimeException.class, () -> new Actor(null));
        assertEquals(Actor.ERROR_NOMBRE_VACIO, ex.getMessage(), "El mensaje de error debe ser el esperado");
    }
}

