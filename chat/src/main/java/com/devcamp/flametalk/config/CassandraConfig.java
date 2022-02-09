package com.devcamp.flametalk.config;

import com.datastax.oss.driver.api.core.CqlSession;
import lombok.RequiredArgsConstructor;
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

@Configuration
@RequiredArgsConstructor
@EnableCassandraRepositories(basePackages = {"com.devcamp.flametalk.domain"})
public class CassandraConfig {

  private final CassandraProperties cassandraProperties;

  @Bean
  public CqlSessionFactoryBean session() {

    CqlSessionFactoryBean session = new CqlSessionFactoryBean();
    session.setContactPoints(cassandraProperties.getContactPoints());
    session.setKeyspaceName(cassandraProperties.getKeyspaceName());
    session.setLocalDatacenter(cassandraProperties.getLocalDatacenter());
    session.setPort(cassandraProperties.getPort());

    return session;
  }

  @Bean
  public SessionFactoryFactoryBean sessionFactory(CqlSession session,
      CassandraConverter converter) {

    SessionFactoryFactoryBean sessionFactory = new SessionFactoryFactoryBean();
    sessionFactory.setSession(session);
    sessionFactory.setConverter(converter);
    sessionFactory.setSchemaAction(SchemaAction.NONE);

    return sessionFactory;
  }

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