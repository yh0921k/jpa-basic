package study.jpql;

import study.jpql.domain.Member;
import study.jpql.domain.MemberType;
import study.jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpqlMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      Team team = new Team();
      team.setName("team");
      em.persist(team);

      Member member1 = new Member();
      member1.setUsername("관리자1");
      member1.setAge(20);
      member1.setTeam(team);
      member1.setType(MemberType.ADMIN);
      em.persist(member1);

      Member member2 = new Member();
      member2.setUsername("관리자2");
      member2.setAge(20);
      member2.setTeam(team);
      member2.setType(MemberType.ADMIN);
      em.persist(member2);

      em.flush();
      em.clear();

      String query = "select t.members from Team t ";
//      Collection result = em.createQuery(query, Collection.class).getResultList();
      List<Collection> result = em.createQuery(query, Collection.class).getResultList();
      for (Object o : result) {
        System.out.println("o = " + o);
      }

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
