package com.devcamp.flametalk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.data.cassandra")
@Getter
@Setter
public class CassandraProperties {

  int port;
  String contactPoints;
  String schemaAction;
  String keyspaceName;
  String localDatacenter;
}
