package study.jpa_basic;

import study.jpa_basic.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class BasicMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      Member member = new Member();
      member.setUsername("kim yong hwi");

      em.persist(member);

      em.flush();
      em.clear();

      List<Member> result =
          em.createQuery("select m from Member m where m.username like '%kim%'", Member.class)
              .getResultList();

      System.out.println("result.get(0).getUsername() = " + result.get(0).getUsername());
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
