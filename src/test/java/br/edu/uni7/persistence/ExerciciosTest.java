package br.edu.uni7.persistence;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExerciciosTest {
	
	private static EntityManager entityManager;

	@BeforeClass
	public static void startup(){
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("cursojpa");
		entityManager = entityManagerFactory.createEntityManager();
	}
	
		
	@Test
	public void testPersist(){
		entityManager.getTransaction().begin();		
		Empregado empregado = Utils.criarEmpregado();
		entityManager.persist(empregado);
		entityManager.getTransaction().commit();
		Assert.assertNotNull(empregado.getId());
	}

	
	@Test
	public void testMerge(){
		entityManager.getTransaction().begin();		
		Empregado empregado = Utils.criarEmpregado();
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
		Empregado empregado = Utils.criarEmpregado();
		entityManager.persist(empregado);
		entityManager.remove(empregado);
		entityManager.getTransaction().commit();
		
		Assert.assertNull(entityManager.find(Empregado.class, empregado.getId()));		
	}
	
	@Test
	public void testDetach(){
		entityManager.getTransaction().begin();		
		
		Empregado empregado = Utils.criarEmpregado();
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
		
		Empregado emp = Utils.criarEmpregado();		
		Departamento dep = Utils.criarDepartamento();
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
		
		Empregado emp = Utils.criarEmpregado();		
		
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

		Empregado emp = Utils.criarEmpregado();
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

		Empregado emp = Utils.criarEmpregado();
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
		Empregado emp = Utils.criarEmpregado();		
		
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
		Empregado emp = Utils.criarEmpregado();		
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
	
	@Test
	public void testFindByNome(){
		entityManager.getTransaction().begin();
		Empregado emp = Utils.criarEmpregado();
		entityManager.persist(emp);
		entityManager.getTransaction().commit();
		entityManager.clear();

		List<Empregado> empregados = entityManager.createNamedQuery("Empregado.findByNome", Empregado.class)
				.setParameter("nome", emp.getNome())
				.getResultList();
		
		Assert.assertEquals(1, empregados.size());
		
	}
	
	@Test
	public void testEmpregadosMaiorDep(){
		
		entityManager.getTransaction().begin();
		
		Utils.gerarDepartamentosComEmpregados(entityManager);
		
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		List<Object[]> resultList = entityManager.createNamedQuery("Departamento.quantosEmpregadosMaiorOrcamento", Object[].class)				
				.getResultList();
		
		Assert.assertTrue(!resultList.isEmpty());
		System.out.println(Arrays.asList(resultList.get(0)));
		
	}


	@Test
	public void testNativeByDocumento(){
		
		entityManager.getTransaction().begin();
		Empregado empregado = Utils.criarEmpregado();
		Documento doc = new Documento();
		doc.setNumero((long)(Math.random()*10000));
		empregado.setDocumento(doc);
		entityManager.persist(empregado);
		entityManager.getTransaction().commit();
		entityManager.clear();
		List<Empregado> list = entityManager.createNamedQuery("Empregado.byDocumento", Empregado.class)
					.setParameter(1, doc.getNumero())
					.getResultList();
		Assert.assertTrue(list.size()>=1);
	}
	
	@Test
	public void testDepartamentoComEmpregadosSemProjeto(){
		List<Departamento> list = entityManager.createNamedQuery("Departamento.semEmpregadosEmProjetos", Departamento.class).getResultList();
		Assert.assertTrue(list.size()>0);
	}

	@Test
	public void testCriarTarefas(){
		entityManager.getTransaction().begin();
		
		Empregado emp = Utils.criarEmpregado();		
		
		Projeto proj = new Projeto();
		proj.setNome("Cassiny");
		entityManager.persist(proj);
		
		emp.getProjetos().add(proj);
		entityManager.persist(emp);
		
		TarefaUnica tarefaUnica = new TarefaUnica();
		tarefaUnica.setNome("Importar Dados do DB");
		tarefaUnica.setDataCriacao(new Date());
		tarefaUnica.setProjeto(proj);
		tarefaUnica.setEmpregado(emp);
		
		entityManager.persist(tarefaUnica);
		
		TarefaRecorrente tarefaRecorrente = new TarefaRecorrente();
		tarefaRecorrente.setNome("Verificar E-mails");
		tarefaRecorrente.setDataCriacao(new Date());
		tarefaRecorrente.setProjeto(proj);
		tarefaRecorrente.setEmpregado(emp);
		tarefaRecorrente.setPeriodicidade(Peridicidade.DIARIO);
		
		entityManager.persist(tarefaRecorrente);
		
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		Projeto projetoDb = entityManager.find(Projeto.class, proj.getId());
		List<Tarefa> tarefas = projetoDb.getTarefas();
		for (int i = 0; i < tarefas.size(); i++) {
			Tarefa tarefa = tarefas.get(i);			
			System.out.println(String.format("Tarefa %d -> %s", i+1, tarefa.getNome()));
		}
		
		Assert.assertTrue(projetoDb.getTarefas().size()==2);		
		
	}
	
	
	
}

