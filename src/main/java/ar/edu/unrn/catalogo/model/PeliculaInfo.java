package ar.edu.unrn.catalogo.model;

import java.util.List;

public record PeliculaInfo(Long id, String titulo, int anio, double precio, String director, List<String> actores) {
    public boolean esDeDirector(String nombreDirector) {
        return this.director.equals(nombreDirector);
    }
    public boolean tieneActor(String nombreActor) {
        return this.actores.contains(nombreActor);
    }
    public int cantidadDeActores() {
        return this.actores.size();
    }
}

