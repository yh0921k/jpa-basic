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
      Address homeAddress = new Address("city", "street", "zipcode");

      Member member1 = new Member();
      member1.setUsername("Hello");
      member1.setHomeAddress(homeAddress);
      member1.setWorkPeriod(new Period());
      em.persist(member1);

      Address copyAddress = new Address(homeAddress.getCity(), homeAddress.getStreet(), homeAddress.getZipcode());

      Member member2 = new Member();
      member2.setUsername("Hello");
      member2.setHomeAddress(copyAddress);
      member2.setWorkPeriod(new Period());
      em.persist(member2);

      member1.getHomeAddress().setCity("newCity");

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
