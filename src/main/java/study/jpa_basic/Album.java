package study.jpa_basic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "A")
public class Album extends Item{

  private String artist;
}
