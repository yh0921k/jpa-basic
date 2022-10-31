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
      member.setUsername("team");
      member.setAge(20);
      member.setTeam(team);
      member.setType(MemberType.ADMIN);

      em.persist(member);

      em.flush();
      em.clear();

      String query =
          "select "
              + "case when m.age <= 10 then '학생요금' "
              + "     when m.age >= 60 then '경로요금' "
              + "     else '일반요금' "
              + "end "
              + "from Member m";
      List<String> resultList = em.createQuery(query, String.class).getResultList();
      for (String s : resultList) {
        System.out.println("s = " + s);
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
