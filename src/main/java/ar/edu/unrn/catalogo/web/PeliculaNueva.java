package ar.edu.unrn.catalogo.web;

import java.util.List;

public record PeliculaNueva(
    String titulo,
    int anio,
    double precio,
    String director,
    List<String> actores,
    String condicion,
    String formato,
    String genero,
    String sinopsis,
    String imagen
) {
}
