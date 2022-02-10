package com.devcamp.flametalk.config;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.SessionFactoryFactoryBean;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Cassandra 연결을 위한 설정 파일입니다.
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CassandraProperties.class)
@EnableCassandraRepositories(basePackages = {"com.devcamp.flametalk.domain"})
public class CassandraConfig {

  private final CassandraProperties cassandraProperties;

  /**
   * 싱글톤 CqlSession 을 생성 및 구성합니다. (per application and keyspace)
   *
   * @return session.
   */
  @Bean
  public CqlSessionFactoryBean session() {

    CqlSessionFactoryBean session = new CqlSessionFactoryBean();
    session.setContactPoints(cassandraProperties.getContactPoints());
    session.setKeyspaceName(cassandraProperties.getKeyspaceName());
    session.setLocalDatacenter(cassandraProperties.getLocalDatacenter());
    session.setPort(cassandraProperties.getPort());

    return session;
  }

  /**
   * CQL 실행을 지원하고 데이터베이스 스키마(keyspace)를 초기화하는 SessionFactory 를 생성합니다.
   *
   * @param session   CqlSession
   * @param converter CassandraConverter
   * @return sessionFactory
   */
  @Bean
  public SessionFactoryFactoryBean sessionFactory(CqlSession session,
      CassandraConverter converter) {

    SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
    sessionFactory.setSession(session);
    sessionFactory.setConverter(converter);
    sessionFactory.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

    return sessionFactory;
  }

  /**
   * CassandraPersistentEntity 와 CassandraPersistentProperty 를 기본 추상화로 사용하는 Cassandra에 대한
   * MappingContext 기본 구현입니다.
   *
   * @param cqlSession CqlSession
   * @return mappingContext
   */
  @Bean
  public CassandraMappingContext mappingContext(CqlSession cqlSession) {

    CassandraMappingContext mappingContext = new CassandraMappingContext();
    mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cqlSession));

    return mappingContext;
  }

  @Bean
  public CassandraConverter converter(CassandraMappingContext mappingContext) {
    return new MappingCassandraConverter(mappingContext);
  }

  @Bean
  public CassandraOperations cassandraTemplate(SessionFactory sessionFactory,
      CassandraConverter converter) {
    return new CassandraTemplate(sessionFactory, converter);
  }
}