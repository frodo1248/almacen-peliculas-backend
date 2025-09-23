package ar.edu.unrn.catalogo.util;

import ar.edu.unrn.catalogo.utils.EmfBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EmfBuilderTest {
    public static void main(String[] args) {
        // Probar conexión a MariaDB
        try {
            EntityManagerFactory emf = new EmfBuilder().build();
            EntityManager em = emf.createEntityManager();
            System.out.println("Conexión a MariaDB exitosa!");
            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println("Error al conectar a MariaDB: " + e.getMessage());
            e.printStackTrace();
        }

        // Probar conexión a H2 en memoria
        try {
            EntityManagerFactory emf = new EmfBuilder().memory().build();
            EntityManager em = emf.createEntityManager();
            System.out.println("Conexión a H2 en memoria exitosa!");
            em.close();
            emf.close();
        } catch (Exception e) {
            System.err.println("Error al conectar a H2: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

