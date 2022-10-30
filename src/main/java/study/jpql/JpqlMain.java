package study.jpql;

import study.jpql.domain.Member;
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
      member.setUsername("team");
      member.setAge(20);
      member.setTeam(team);
      em.persist(member);

      em.flush();
      em.clear();

      String query = "select m from Member m left join Team t on m.username = t.name";
      List<Member> resultList = em.createQuery(query, Member.class).getResultList();
      System.out.println("resultList.size() = " + resultList.size());

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
