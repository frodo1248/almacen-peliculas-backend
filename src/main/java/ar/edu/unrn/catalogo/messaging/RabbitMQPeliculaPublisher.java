package ar.edu.unrn.catalogo.messaging;

import ar.edu.unrn.catalogo.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPeliculaPublisher implements PeliculaMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQPeliculaPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPeliculaPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicarPeliculaAgregada(Long id, String nombre, Double precio) {
        PeliculaAgregadaEvent event = new PeliculaAgregadaEvent(id, nombre, precio);

        logger.info("📤 Enviando mensaje a RabbitMQ - Película agregada: id={}, nombre={}, precio={}",
                    id, nombre, precio);

        rabbitTemplate.convertAndSend(RabbitMQConfig.PELICULA_AGREGADA_QUEUE, event);

        logger.info("✅ Mensaje enviado exitosamente");
    }
}

