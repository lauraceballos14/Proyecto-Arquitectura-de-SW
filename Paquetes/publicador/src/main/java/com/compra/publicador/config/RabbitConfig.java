package com.compra.publicador.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "estado-paquete";
    public static final String EXCHANGE_NAME = "estado-paquete-exchange";
    public static final String ROUTING_KEY = "estado.paquete";

    @Bean
    public Queue estadoQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange estadoExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding estadoBinding(Queue estadoQueue, DirectExchange estadoExchange) {
        return BindingBuilder.bind(estadoQueue).to(estadoExchange).with(ROUTING_KEY);
    }
}
