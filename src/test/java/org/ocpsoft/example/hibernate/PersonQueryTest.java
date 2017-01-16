package org.ocpsoft.example.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ocpsoft.example.hibernate.model.Person;
import org.ocpsoft.example.hibernate.util.EntityManagerUtil;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class PersonQueryTest
{
   private EntityManager em;

   @Before
   public void beforeEach()
   {
      em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
   }

   @After
   public void afterEach()
   {
      em.close();
   }

   @Test
   public void testAutoIncrement()
   {
		Person personFind = em.find(Person.class, 1L);

		Query query = em.createQuery("SELECT p FROM Person p where p.id = :id");
		query.setParameter("id", 1L);
		try {
			Person personCreateQuery = (Person) query.getSingleResult();
		} catch (NoResultException e) {
			//ignore
		}
   }

}
