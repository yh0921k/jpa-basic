package study.jpa_basic;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team teamA = new Team();
      teamA.setName("TeamAAA");
      em.persist(teamA);

      Team teamB = new Team();
      teamB.setName("TeamBBB");
      em.persist(teamB);

      Member member1 = new Member();
      member1.setUsername("AAA");
      member1.setTeam(teamA);
      em.persist(member1);

      Member member2 = new Member();
      member2.setUsername("BBB");
      member2.setTeam(teamB);
      em.persist(member2);

      em.flush();
      em.clear();

//      Member m = em.find(Member.class, member1.getId());
      List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

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
