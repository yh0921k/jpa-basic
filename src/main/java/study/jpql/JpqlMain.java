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
      Team teamA = new Team();
      teamA.setName("TeamA");
      em.persist(teamA);

      Team teamB = new Team();
      teamB.setName("teamB");
      em.persist(teamB);

      Team teamC = new Team();
      teamC.setName("teamC");
      em.persist(teamC);

      Member member1 = new Member();
      member1.setUsername("회원1");
      member1.setAge(20);
      member1.setTeam(teamA);
      member1.setType(MemberType.ADMIN);
      em.persist(member1);

      Member member2 = new Member();
      member2.setUsername("회원2");
      member2.setAge(20);
      member2.setTeam(teamA);
      member2.setType(MemberType.ADMIN);
      em.persist(member2);

      Member member3 = new Member();
      member3.setUsername("회원3");
      member3.setAge(20);
      member3.setTeam(teamB);
      member3.setType(MemberType.ADMIN);
      em.persist(member3);

      Member member4 = new Member();
      member4.setUsername("회원2");
      member4.setAge(20);
      member4.setType(MemberType.ADMIN);
      em.persist(member4);

      em.flush();
      em.clear();

      String query = "select m from Member m where m.team = :team";
      List<Member> findMember = em.createQuery(query, Member.class).setParameter("team", teamA).getResultList();
      for (Member member : findMember) {
        System.out.println("member = " + member);
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
