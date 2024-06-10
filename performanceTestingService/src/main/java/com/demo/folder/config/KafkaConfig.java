package com.demo.folder.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaConfig {

    @Bean
    public NewTopic createTopic(){
        return TopicBuilder.name("t-test-type").partitions(1).replicas(1).build();
    }
}
