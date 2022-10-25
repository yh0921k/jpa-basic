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

      Member member = new Member();
      member.setUsername("AAA");
      em.persist(member);
      
      em.flush();
      em.clear();

//      Member findMember = em.find(Member.class, 1L);

      Member findMember = em.getReference(Member.class, member.getId());
      System.out.println("findMember = " + findMember.getClass());
      System.out.println("findMember.getUsername() = " + findMember.getUsername());
      System.out.println("findMember = " + findMember.getClass());

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
