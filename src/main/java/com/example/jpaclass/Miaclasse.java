package com.example.jpaclass;

import java.lang.Override;
import java.lang.Short;
import java.lang.String;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TODO: Add missing implementazione Overriding of hashCode and equals using IDE Actions Code Generation.. */
@Entity
@Table(
    name = "MIACLASSE"
)
public class Miaclasse {
  private Short proprieta1;

  private BigDecimal proprieta2;

  public Miaclasse() {
  }

  public Miaclasse(Short proprieta1, BigDecimal proprieta2) {
    this.proprieta1=proprieta1;
    this.proprieta2=proprieta2;
  }

  public void setProprieta1(Short proprieta1) {
    this.proprieta1=proprieta1;
  }

  @Column(
      name = "PROPRIETA_1"
  )
  public Short getProprieta1() {
    return proprieta1;
  }

  public void setProprieta2(BigDecimal proprieta2) {
    this.proprieta2=proprieta2;
  }

  @Column(
      name = "PROPRIETA_2"
  )
  public BigDecimal getProprieta2() {
    return proprieta2;
  }

  @Override
  public String toString() {
    StringBuilder resultString = new StringBuilder("Miaclasse").append("{").append("proprieta1").append("=").append(proprieta1).append(",");
    resultString.append("proprieta2").append("=").append(proprieta2).append("}");
    return resultString.toString();
  }
}
