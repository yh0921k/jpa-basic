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

      Member member1 = new Member();
      member1.setUsername("AAA");
      em.persist(member1);
      
      em.flush();
      em.clear();

      Member m1 = em.find(Member.class, member1.getId());
      System.out.println("m1.getClass() = " + m1.getClass());

      Member reference = em.getReference(Member.class, member1.getId());
      System.out.println("reference.getClass() = " + reference.getClass());

      System.out.println("(reference == m1) = " + (reference == m1));
      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
