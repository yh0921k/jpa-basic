package study.jpa_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
      member.setTeamId(team.getId());
      em.persist(member);

      em.flush();

      Member findMember = em.find(Member.class, member.getId());
      System.out.println("findMember.getUsername() = " + findMember.getUsername());
      
      Long findTeamId = findMember.getTeamId();
      Team findTeam = em.find(Team.class, findTeamId);
      System.out.println("findTeam.getName() = " + findTeam.getName());

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
