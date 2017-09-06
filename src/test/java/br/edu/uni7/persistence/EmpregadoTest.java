package br.edu.uni7.persistence;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmpregadoTest {
	
	private static EntityManager entityManager;

	@BeforeClass
	public static void startup(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cursojpa");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
		
	@Test
	public void testPersist(){
		entityManager.getTransaction().begin();		
		Empregado empregado = criarEmpregado();
		entityManager.persist(empregado);
		entityManager.getTransaction().commit();
		Assert.assertNotNull(empregado.getId());
	}

	
	@Test
	public void testMerge(){
		entityManager.getTransaction().begin();		
		Empregado empregado = criarEmpregado();
		entityManager.persist(empregado);
		entityManager.getTransaction().commit();
				
		empregado.setNome("Novo Nome");
		entityManager.getTransaction().begin();
		Empregado empregadoDb = entityManager.merge(empregado);
		entityManager.getTransaction().commit();		
		
		Assert.assertEquals("Novo Nome", empregadoDb.getNome());		
	}
	
	@Test
	public void testRemove(){
		entityManager.getTransaction().begin();		
		Empregado empregado = criarEmpregado();
		entityManager.persist(empregado);
		entityManager.remove(empregado);
		entityManager.getTransaction().commit();
		
		Assert.assertNull(entityManager.find(Empregado.class, empregado.getId()));		
	}
	
	@Test
	public void testDetach(){
		entityManager.getTransaction().begin();		
		
		Empregado empregado = criarEmpregado();
		entityManager.persist(empregado);
		
		String nome = empregado.getNome();
		
		entityManager.detach(empregado);
		empregado.setNome("Novo nome");
		
		entityManager.getTransaction().commit();	
		entityManager.clear();
		
		Empregado empregado2 = entityManager.find(Empregado.class, empregado.getId());
		Assert.assertEquals(nome, empregado2.getNome());		
	}
		
	private Empregado criarEmpregado() {
		Empregado employee = new Empregado();
		int random = ((int)(Math.random()*1000));
		employee.setNome("Empregado " + random);
		employee.setEmail("email"+random+"@email.com");
		employee.setDataNascimento(new Date());
		employee.setSalario(new BigDecimal((Math.random()*20000)));
		return employee;
	}
	
	
	
}

