package oi.github.reynaldo.msavaliadorcredito.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MqConfig {

    @Value("${mq.queues.emissao-cartoes}")
    private String emissaoCartoesFila;

    @Bean
    public Queue queueEmissaoCartao(){
        return new Queue(emissaoCartoesFila, true);
    }

}
