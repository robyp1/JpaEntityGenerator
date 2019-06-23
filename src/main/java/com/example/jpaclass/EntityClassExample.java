package com.example.jpaclass;

import java.lang.String;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(
    name = "nomeTabella"
)
public class EntityClassExample {
  private String proprieta1;

  public EntityClassExample() {
  }

  public EntityClassExample(String proprieta1) {
    this.proprieta1=proprieta1;
  }

  @Column(
      name = "NOME_COLONNA_PROPRIETA1"
  )
  public String getProrpieta1() {
    return proprieta1;
  }

  public void setProrpieta1(String proprieta1) {
    this.proprieta1=proprieta1;
  }
}
