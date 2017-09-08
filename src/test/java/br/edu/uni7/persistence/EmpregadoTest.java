package br.edu.uni7.persistence;

import java.math.BigDecimal;

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
		Empregado empregadoGerenciado = entityManager.merge(empregado);
		entityManager.getTransaction().commit();
		
		//Remove todos os objetos gerenciados do EM. 
		//Usado aqui apenas para controlar a execução do teste.
		entityManager.clear();
		
		Assert.assertEquals("Novo Nome", entityManager.find(Empregado.class, empregadoGerenciado.getId()).getNome());		
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
		
		String nomeAntesDaMudanca = empregado.getNome();
		
		entityManager.detach(empregado);
		empregado.setNome("Novo nome");
		
		entityManager.getTransaction().commit();	
		entityManager.clear();
		
		Empregado empregadoDb = entityManager.find(Empregado.class, empregado.getId());
		Assert.assertEquals(nomeAntesDaMudanca, empregadoDb.getNome());		
	}
	
	@Test
	public void testAssociarDepartamento() {
		entityManager.getTransaction().begin();
		
		Empregado emp = criarEmpregado();		
		Departamento dep = criarDepartamento();
		entityManager.persist(dep);
		
		emp.setDepartamento(dep);		
		entityManager.persist(emp);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Empregado empregadoDb = entityManager.find(Empregado.class, emp.getId());		
		Assert.assertNotNull(empregadoDb.getDepartamento());
		Assert.assertNotNull(empregadoDb.getDepartamento().getId());
	}
	
	@Test
	public void testAssociarProjetoOwningSide(){
		entityManager.getTransaction().begin();
		
		Empregado emp = criarEmpregado();		
		
		Projeto proj = new Projeto();
		proj.setNome("MARS");
		entityManager.persist(proj);
		
		emp.getProjetos().add(proj);
		entityManager.persist(emp);
		
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Empregado empregado = entityManager.find(Empregado.class, emp.getId());
		Assert.assertTrue(empregado.getProjetos().size() == 1);		
		Assert.assertTrue(empregado.getProjetos().get(0).getEmpregados().size() == 1);
	}
	
	@Test
	public void testAssociarProjetoMappedBySide(){
		entityManager.getTransaction().begin();

		Empregado emp = criarEmpregado();
		entityManager.persist(emp);
		
		Projeto proj = new Projeto();
		proj.setNome("MARS");				
		proj.getEmpregados().add(emp);		
		entityManager.persist(proj);
		
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Empregado empregado = entityManager.find(Empregado.class, emp.getId());
		Assert.assertTrue(empregado.getProjetos().size() == 0);		
	}
	
	
	@Test
	public void testPersistAddress(){
		entityManager.getTransaction().begin();

		Empregado emp = criarEmpregado();
		Endereco end = new Endereco();
		end.setRua("Av Pontes");
		end.setCidade("Fortaleza");
		end.setCep(60100000L);
		emp.setEndereco(end);
		
		entityManager.persist(emp);
		
		entityManager.getTransaction().commit();
		
		Assert.assertNotNull(emp.getId());
		Assert.assertNotNull(emp.getEndereco());
	}
	
	@Test
	public void testAdicionarDoc(){
		Empregado emp = criarEmpregado();		
		
		Documento doc = new Documento();
		doc.setNumero(898989898L);
		
		emp.setDocumento(doc);
		
		entityManager.getTransaction().begin();
		entityManager.persist(emp);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Empregado empregado = entityManager.find(Empregado.class, emp.getId());
		Assert.assertNotNull(empregado);
		Assert.assertNotNull(empregado.getDocumento().getId());		
		
	}
	
	@Test
	public void testRemoveDoc(){
		Empregado emp = criarEmpregado();		
		Documento doc = new Documento();
		doc.setNumero(898989898L);		
		emp.setDocumento(doc);
		
		entityManager.getTransaction().begin();
		entityManager.persist(emp);
		
		entityManager.remove(emp);
		
		entityManager.getTransaction().commit();
		entityManager.clear();

		Assert.assertNull( entityManager.find(Documento.class, emp.getDocumento().getId()));		
		
	}


	private Departamento criarDepartamento() {
		Departamento dep1 = new Departamento();
		int random = (int)(Math.random()*100000);
		dep1.setNome("Departamento " + random);
		dep1.setOrcamento( new BigDecimal (random * (Math.random()*10)));
		return dep1;
	}
	
	private Empregado criarEmpregado() {
		Empregado employee = new Empregado();		
		employee.setNome("Empregado " + ((int)(Math.random()*1000)));	
		return employee;
	}
	
	
	
}

