package ar.edu.unrn.catalogo.web;

import java.util.List;

public record PeliculaInfoCatalogo(
        Long id,
        String titulo,
        int anio,
        double precio,
        String director,
        List<String> actores,
        String imagen
) {}

