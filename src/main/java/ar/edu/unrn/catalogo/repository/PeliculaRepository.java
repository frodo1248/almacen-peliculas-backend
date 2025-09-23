package ar.edu.unrn.catalogo.repository;

import ar.edu.unrn.catalogo.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

}

