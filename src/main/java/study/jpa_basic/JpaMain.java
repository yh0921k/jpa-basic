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

      Address newAddress = new Address("NewCity", homeAddress.getStreet(), homeAddress.getZipcode());
      member1.setHomeAddress(newAddress);

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
