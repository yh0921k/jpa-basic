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
      Member member = new Member();
      member.setUsername("member1");
      member.setHomeAddress(new Address("city", "street", "zipcode"));

      member.getFavoriteFood().add("치킨");
      member.getFavoriteFood().add("족발");
      member.getFavoriteFood().add("피자");

      member.getAddressHistory().add(new Address("city2", "street2", "zipcode2"));
      member.getAddressHistory().add(new Address("city3", "street3", "zipcode3"));

      em.persist(member);

      em.flush();
      em.clear();

      System.out.println("===================================");
      Member findMember = em.find(Member.class, member.getId());
      System.out.println("===================================");

      findMember.getFavoriteFood().remove("치킨");
      findMember.getFavoriteFood().add("한식");

      // equals(), hashcode()의 중요성
      findMember.getAddressHistory().remove(new Address("city2", "street2", "zipcode2"));
      findMember.getAddressHistory().add(new Address("new city 1", "street2", "zipcode2"));

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
