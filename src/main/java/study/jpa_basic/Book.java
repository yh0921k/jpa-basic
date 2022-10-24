package study.jpa_basic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "B")
public class Book extends Item{

  private String author;
  private String isbn;
}
