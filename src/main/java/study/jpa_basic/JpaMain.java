package study.jpa_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {

      Team team = new Team();
      team.setName("TeamA");
      em.persist(team);

      Member member = new Member();
      member.setUsername("Member1");
      member.setTeam(team);
      em.persist(member);

      em.flush();
      em.clear();

      Member findMember = em.find(Member.class, member.getId());
      System.out.println("findMember.getUsername() = " + findMember.getUsername());

      Team findTeam = member.getTeam();
      List<Member> members = findTeam.getMembers();

      for (Member member1 : members) {
        System.out.println("member.getUsername() = " + member.getUsername());
      }

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
