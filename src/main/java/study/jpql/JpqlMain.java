package study.jpql;

import study.jpql.domain.Member;
import study.jpql.domain.MemberDto;

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
      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      em.persist(member);

      em.flush();
      em.clear();

      // 여러 값 조회 1
      List findMembers = em.createQuery("select m.username, m.age from Member m").getResultList();
      Object o = findMembers.get(0);
      Object[] result = (Object[]) o;
      System.out.println("result[0] = " + result[0]);
      System.out.println("result[1] = " + result[1]);

      em.flush();
      em.clear();

      // 여러 값 조회 2
      List<Object[]> findMembers2 =
          em.createQuery("select m.username, m.age from Member m", Object[].class).getResultList();
      Object[] objects = findMembers2.get(0);
      System.out.println("objects[0] = " + objects[0]);
      System.out.println("objects[1] = " + objects[1]);

      em.flush();
      em.clear();

      // 여러 값 조회 3
      // 문자열로 쿼리를 만들기 때문에 패키지명을 적어야하며, 추후 QueryDSL을 사용하면 해결됨
      List<MemberDto> findMembers3 =
          em.createQuery("select new study.jpql.domain.MemberDto(m.username, m.age) from Member m")
              .getResultList();
      MemberDto memberDto = findMembers3.get(0);
      System.out.println("memberDto.getUsername() = " + memberDto.getUsername());
      System.out.println("memberDto.getAge() = " + memberDto.getAge());

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
