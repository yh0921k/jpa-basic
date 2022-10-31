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

      String query = "select m.username, 'Hello', TRUE from Member m where m.type = :type";
      List<Object[]> result =
          em.createQuery(query).setParameter("type", MemberType.USER).getResultList();

      for (Object[] objects : result) {
        System.out.println("objects[0] = " + objects[0]);
        System.out.println("objects[1] = " + objects[1]);
        System.out.println("objects[2] = " + objects[2]);
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
