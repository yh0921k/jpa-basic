package study.jpql;

import study.jpql.domain.Member;
import study.jpql.domain.MemberType;
import study.jpql.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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

      Member member = new Member();
      member.setUsername("관리자");
      member.setAge(20);
      member.setTeam(team);
      member.setType(MemberType.ADMIN);

      em.persist(member);

      em.flush();
      em.clear();

      String query = "select locate('de', 'abcdefg') from Member m";
      List<Integer> resultList = em.createQuery(query, Integer.class).getResultList();
      for (Integer integer : resultList) {
        System.out.println("integer = " + integer);
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
