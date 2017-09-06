package br.edu.uni7.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {

	@Test
	public void testConnecionWithDb(){
		EntityManagerFactory entityManagerFactory = 
				Persistence.createEntityManagerFactory("cursojpa");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Assert.assertNotNull(entityManager);
	}
	
}
