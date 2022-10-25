package study.jpa_basic;

import javax.persistence.*;

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

      Member refMember = em.getReference(Member.class, member1.getId());
      System.out.println("refMember.getClass() = " + refMember.getClass());
      refMember.getUsername();
      System.out.println("emf.getPersistenceUnitUtil().isLoaded(refMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      e.printStackTrace();
    } finally {
      em.close();
    }
    emf.close();
  }
}
