package study.jpql;

import study.jpql.domain.Member;
import study.jpql.domain.Team;

import javax.persistence.*;
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

      String query = "select m.id, (select avg(m1.age) from Member m1) as avgAge from Member m left join Team t on m.username = t.name";
      Query subQuery = em.createQuery(query);
      List resultList = subQuery.getResultList();
      System.out.println("resultList.size() = " + resultList.size());
      System.out.println("((Object[])resultList.get(0))[0] = " + ((Object[])resultList.get(0))[0]);
      System.out.println("((Object[])resultList.get(0))[1] = " + ((Object[])resultList.get(0))[1]);

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
