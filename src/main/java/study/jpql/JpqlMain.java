package study.jpql;

import study.jpql.domain.Address;
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
      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      em.persist(member);

      em.flush();
      em.clear();

      List<Member> findMembers = em.createQuery("select m from Member m", Member.class).getResultList();

      // 조회된 결과가 영속성 컨텍스트에서 관리됨
      Member findMember = findMembers.get(0);
      findMember.setAge(20);

      // 편해보이지만 실무에서 JOIN은 성능과 직결되므로, 명시적으로 표현해주는 것이 좋음
      List<Team> result = em.createQuery("select m.team from Member m", Team.class).getResultList();
      List<Team> better = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

      // 임베디드 타입 프로젝션
      List<Address> embedded = em.createQuery("select o.address from Order o", Address.class).getResultList();

      // 스칼라 타입
      List scalar = em.createQuery("select distinct m.username, m.age from Member m").getResultList();

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
