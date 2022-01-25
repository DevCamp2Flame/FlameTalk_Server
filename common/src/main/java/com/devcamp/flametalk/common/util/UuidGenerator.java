package com.devcamp.flametalk.common.util;

import java.io.Serializable;
import java.util.Random;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * 랜덤한 사용자 id 생성기입니다.
 */
public class UuidGenerator implements IdentifierGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    Random random = new Random();
    return System.currentTimeMillis() + String.format("%06d", random.nextInt(999999));
  }
}