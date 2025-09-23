package ar.edu.unrn.catalogo.web;

import ar.edu.unrn.catalogo.model.Pelicula;
import ar.edu.unrn.catalogo.model.PeliculaInfo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/catalogo")
public class CatalogoController {
    private final ar.edu.unrn.catalogo.service.CatalogoService catalogoService;

    public CatalogoController(ar.edu.unrn.catalogo.service.CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    static final String ERROR_PELICULA_NO_ENCONTRADA = "Película no encontrada";

    @GetMapping
    public List<PeliculaInfoCatalogo> verCatalogo() {
        // Devuelve solo los campos requeridos para el listado
        return catalogoService.listarPeliculas();
    }

    @GetMapping("/{id}")
    public PeliculaDetalle verDetalle(@PathVariable Long id) {
        // Devuelve todos los campos de la película seleccionada
        return catalogoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException(ERROR_PELICULA_NO_ENCONTRADA));
    }
}
