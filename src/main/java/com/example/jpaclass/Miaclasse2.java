package com.example.jpaclass;

import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TODO: Add missing implementazione Overriding of hashCode and equals using IDE Actions Code Generation.. */
@Entity
@Table(
    name = "MIACLASSE2"
)
public class Miaclasse2 {
  private String proprieta3;

  private BigDecimal proprieta4;

  public Miaclasse2() {
  }

  public Miaclasse2(String proprieta3, BigDecimal proprieta4) {
    this.proprieta3=proprieta3;
    this.proprieta4=proprieta4;
  }

  public void setProprieta3(String proprieta3) {
    this.proprieta3=proprieta3;
  }

  @Column(
      name = "PROPRIETA_3"
  )
  public String getProprieta3() {
    return proprieta3;
  }

  public void setProprieta4(BigDecimal proprieta4) {
    this.proprieta4=proprieta4;
  }

  @Column(
      name = "PROPRIETA_4"
  )
  public BigDecimal getProprieta4() {
    return proprieta4;
  }

  @Override
  public String toString() {
    StringBuilder resultString = new StringBuilder("Miaclasse2").append("{").append("proprieta3").append("=").append(proprieta3).append(",");
    resultString.append("proprieta4").append("=").append(proprieta4).append("}");
    return resultString.toString();
  }
}
