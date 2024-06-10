package com.demo.folder.config;


import com.demo.folder.error.handler.GlobalErrorHandler;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.ssl.NoSuchSslBundleException;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@EnableKafka
public class KafkaConfig {


    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public NewTopic createTopic(){
        return TopicBuilder.name("t-test-type").partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic logProblemTopic() {
        return TopicBuilder.name("t-test-problem").partitions(1).replicas(1).build();
    }

    @Bean(name = "globalLogContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> globalLogContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            KafkaTemplate<String,String> kafkaTemplate
    ) {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory,consumerFactory());

        factory.setCommonErrorHandler(new GlobalErrorHandler(kafkaTemplate,"t-test-problem"));

        return factory;
    }

    private ConsumerFactory<Object, Object> consumerFactory() {
        SslBundles sslBundles = getOrCreateSslBundles();

        var properties = kafkaProperties.buildConsumerProperties(sslBundles);
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    private SslBundles getOrCreateSslBundles() {
        // Your logic to create or retrieve an SslBundles instance
        // This could be as simple as a new instance or as complex as you need it to be
        // Example: return new SslBundles();
        return new SslBundles() {
            @Override
            public SslBundle getBundle(String name) throws NoSuchSslBundleException {
                return null;
            }

            @Override
            public void addBundleUpdateHandler(String name, Consumer<SslBundle> updateHandler) throws NoSuchSslBundleException {

            }
        };
    }
}
