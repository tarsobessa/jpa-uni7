package br.edu.uni7.persistence;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TBL_DEPARTAMENTOS")
public class Departamento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PK_DEP_ID")
	private Long id;
	
	@Embedded
	private Endereco endereco;
	
	@Column(name="NM_NAME")
	String nome;
	
	@Column(name="NM_ORCAMENTO")
	BigDecimal orcamento;
	
	@OneToMany(mappedBy="departamento")
	private List<Empregado> empregados;
	
	public List<Empregado> getEmpregados() {
		return empregados;
	}
	public void setEmpregados(List<Empregado> empregados) {
		this.empregados = empregados;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public BigDecimal getOrcamento() {
		return orcamento;
	}
	public void setOrcamento(BigDecimal orcamento) {
		this.orcamento = orcamento;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departamento other = (Departamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Departamento [id=" + id + ", nome=" + nome + "]";
	}
	

}
