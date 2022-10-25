package study.jpa_basic;

import org.hibernate.Hibernate;

import javax.persistence.*;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team team = new Team();
      team.setName("TeamAAA");
      em.persist(team);

      Member member1 = new Member();
      member1.setUsername("AAA");
      member1.setTeam(team);
      em.persist(member1);

      em.flush();
      em.clear();

      Member m = em.find(Member.class, member1.getId());
      System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());

      System.out.println("=======================================");
      m.getTeam().getName();
      System.out.println("=======================================");

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
