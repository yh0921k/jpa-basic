package study.jpql;

import study.jpql.domain.Member;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try {
      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      em.persist(member);

      TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
      List<Member> resultList = query.getResultList();
      for (Member findMember : resultList) {
        System.out.println("findMember.getUsername() = " + findMember.getUsername());
      }

      TypedQuery<Member> query2 = em.createQuery("select m from Member m where m.id = 1", Member.class);
      Member result = query.getSingleResult();
      System.out.println("result.getUsername() = " + result.getUsername());

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
