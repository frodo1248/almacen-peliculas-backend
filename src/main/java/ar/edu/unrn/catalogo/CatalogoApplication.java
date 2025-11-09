package ar.edu.unrn.catalogo;

import ar.edu.unrn.catalogo.messaging.PeliculaMessagePublisher;
import ar.edu.unrn.catalogo.model.Actor;
import ar.edu.unrn.catalogo.model.Director;
import ar.edu.unrn.catalogo.model.Pelicula;
import ar.edu.unrn.catalogo.service.CatalogoService;
import ar.edu.unrn.catalogo.utils.EmfBuilder;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@SpringBootApplication(
    exclude = {
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class,
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class
    }
)
@ComponentScan(basePackages = {"ar.edu.unrn.catalogo.*"})
public class CatalogoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogoApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public EntityManagerFactory entityManagerFactory() {
        return new EmfBuilder()
                .addClass(Pelicula.class)
                .addClass(Director.class)
                .addClass(Actor.class)
                .build();
    }

    @Bean
    public CatalogoService catalogoService(EntityManagerFactory emf, PeliculaMessagePublisher messagePublisher) {
        return new CatalogoService(emf, messagePublisher);
    }
}
