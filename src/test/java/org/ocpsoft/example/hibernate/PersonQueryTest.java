package org.ocpsoft.example.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ocpsoft.example.hibernate.model.Address;
import org.ocpsoft.example.hibernate.model.Person;
import org.ocpsoft.example.hibernate.util.EntityManagerUtil;

public class PersonQueryTest {

    private EntityManagerFactory emf;

    @Before
    public void beforeEach()
    {
        emf = EntityManagerUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        // we need some data in the database to check how find and select work
        try {
            Address address = new Address();

            Person person = new Person();
            person.setName("Bob");
            person.setAddress(address);

            em.getTransaction().begin();
            em.persist(person);
            em.persist(address);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @After
    public void afterEach()
    {
        emf.close();
    }

    @Test
    public void testAutoIncrement()
    {


        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Before find");
            Person personFind = em.find(Person.class, 1L);
            System.out.println("After find");

            // clear the persistence context otherwise we will not see the address getting selected, because it was loaded with find
            em.clear();

            System.out.println("Before createQuery");
            Person person = em.createQuery("SELECT p FROM Person p where p.id = :id", Person.class)
                    .setParameter("id", 1L)
                    .getSingleResult();
            System.out.println("After createQuery");
        } finally {
            em.close();
        }

    }

}
