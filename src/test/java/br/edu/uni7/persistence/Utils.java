package br.edu.uni7.persistence;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

public class Utils {
	
	public static void gerarDepartamentosComEmpregados(EntityManager entityManager) {
		int maxDep = (int)(Math.random() * 15);
		
		for (int i = 0; i < maxDep; i++) {
			Departamento departamento = criarDepartamento();		
			entityManager.persist(departamento);
			
			int maxEmp = (int)(Math.random() * 40);
			for (int j = 0; j < maxEmp; j++) {
				Empregado emp = criarEmpregado();	
				emp.setDepartamento(departamento);
				entityManager.persist(emp);
			}						
		}
	}
	
	public static Departamento criarDepartamento() {
		Departamento dep1 = new Departamento();
		int random = (int)(Math.random()*100000);
		dep1.setNome("Departamento " + random);
		dep1.setOrcamento( new BigDecimal (random * (Math.random()*10)));
		return dep1;
	}
	
	public static Empregado criarEmpregado() {
		Empregado employee = new Empregado();		
		employee.setNome("Empregado " + ((int)(Math.random()*1000)));	
		return employee;
	}
}
