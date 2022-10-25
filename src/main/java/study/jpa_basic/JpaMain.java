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

      Member member2 = new Member();
      member2.setUsername("BBB");
      em.persist(member2);

      Member member3 = new Member();
      member3.setUsername("CCC");
      em.persist(member3);
      
      em.flush();
      em.clear();

      Member m1 = em.find(Member.class, member1.getId());
      Member m2 = em.find(Member.class, member2.getId());
      Member m3 = em.getReference(Member.class, member3.getId());
      compare(m1, m2);
      compare(m1, m3);

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }

  private static void compare(Member m1, Member m2) {
//    System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass()));
    System.out.println("m1 == m2 : " + (m1 instanceof Member && m2 instanceof Member));
  }
}
