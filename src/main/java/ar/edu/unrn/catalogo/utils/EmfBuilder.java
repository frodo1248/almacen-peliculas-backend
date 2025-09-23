package ar.edu.unrn.catalogo.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.cfg.JdbcSettings;
import org.hibernate.tool.schema.Action;

/**
 * EmfBuilder centraliza la configuración de la base de datos.
 * Por defecto usa MariaDB (para desarrollo y producción).
 * Para tests en memoria, llamar al método memory().
 *
 * Ejemplo para tests:
 *   new EmfBuilder().memory().addClass(...).build();
 * Ejemplo para app real:
 *   new EmfBuilder().addClass(...).build();
 */
public class EmfBuilder {
    public static final String DB_USER = "root";
    public static final String DB_PWD = "";
    public static final String IN_MEMORY_DB_URL = "jdbc:h2:mem:peliculas;MODE=MySQL;DB_CLOSE_DELAY=-1";
    public static final String CLIENT_DB_URL = "jdbc:mariadb://localhost:3306/peliculas";
    private EntityManagerFactory emf;
    private PersistenceConfiguration config;

    public EmfBuilder() {
        config = new PersistenceConfiguration("peliculas")
                .property(PersistenceConfiguration.JDBC_USER, DB_USER)
                .property(PersistenceConfiguration.JDBC_PASSWORD, DB_PWD)
                .property(JdbcSettings.SHOW_SQL, true)
                .property(JdbcSettings.FORMAT_SQL, true)
                .property(JdbcSettings.HIGHLIGHT_SQL, true)
                .property(PersistenceConfiguration.JDBC_URL, CLIENT_DB_URL)
                .property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, Action.CREATE_DROP)
                .property("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect")
                .property(PersistenceConfiguration.JDBC_DRIVER, "org.mariadb.jdbc.Driver");
    }

    /**
     * Usar solo en tests: cambia la configuración a H2 en memoria.
     */
    public EmfBuilder memory() {
        config.property(PersistenceConfiguration.JDBC_URL, IN_MEMORY_DB_URL)
              .property(PersistenceConfiguration.JDBC_DRIVER, "org.h2.Driver")
              .property("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return this;
    }

    public EmfBuilder addClass(Class<?> clazz) {
        config.managedClass(clazz);
        return this;
    }

    public EmfBuilder withOutChangeSchema() {
        config.property(PersistenceConfiguration.SCHEMAGEN_DATABASE_ACTION, Action.NONE);
        return this;
    }

    public EntityManagerFactory build() {
        return config.createEntityManagerFactory();
    }
}
