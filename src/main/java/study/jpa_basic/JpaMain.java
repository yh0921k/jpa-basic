package study.jpa_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Movie movie = new Movie();
      movie.setActor("Actor");
      movie.setDirector("Director");
      movie.setName("TestName");
      movie.setPrice(20000);

      em.persist(movie);

      em.flush();
      em.clear();

      Movie findMovie = em.find(Movie.class, 1L);
      System.out.println("findMovie = " + findMovie.getName());
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
