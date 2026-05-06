package com.template.ordem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SagaTopicConfig {

    @Bean
    public NewTopic ordemCriadaTopic(@Value("${saga.topics.ordem-criada}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }

    @Bean
    public NewTopic pagamentoStatusTopic(@Value("${saga.topics.pagamento-status}") String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }
}

