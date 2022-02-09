package com.devcamp.flametalk.domain;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface MessageRepository extends CassandraRepository<Message, String> {

}