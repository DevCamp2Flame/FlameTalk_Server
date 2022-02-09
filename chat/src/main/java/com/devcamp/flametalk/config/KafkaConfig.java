package com.devcamp.flametalk.config;

import com.devcamp.flametalk.domain.Message;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@RequiredArgsConstructor
@Configuration
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;

  //Sender config
  @Bean
  public ProducerFactory<String, Message> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs(), null,
        new JsonSerializer<Message>());
  }

  @Bean
  public KafkaTemplate<String, Message> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public Map<String, Object> producerConfigs() {
    return ImmutableMap.<String, Object>builder()
        .put("bootstrap.servers", kafkaProperties.bootstrapServers) //kafka server ip & port
        .put("key.serializer", IntegerSerializer.class)
        .put("value.serializer", JsonSerializer.class) //Object json parser
        .put("group.id", kafkaProperties.groupId) // chatting  group id
        .build();
  }

  //Receiver config
  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, Message> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs(), null,
        new JsonDeserializer<>(Message.class));
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    return ImmutableMap.<String, Object>builder()
        .put("bootstrap.servers", kafkaProperties.bootstrapServers)
        .put("key.deserializer", IntegerDeserializer.class)
        .put("value.deserializer", JsonDeserializer.class)
        .put("group.id", kafkaProperties.groupId)
        .build();
  }
}