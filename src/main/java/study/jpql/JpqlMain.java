package study.jpql;

import study.jpql.domain.Member;

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
      for(int i = 0; i < 100; i++) {
        Member member = new Member();
        member.setUsername("member" + i);
        member.setAge(i);
        em.persist(member);
      }

      em.flush();
      em.clear();

      List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
          .setFirstResult(15)
          .setMaxResults(10)
          .getResultList();

      System.out.println("resultList.size() = " + resultList.size());
      for (Member findMember : resultList) {
        System.out.println(findMember);
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
