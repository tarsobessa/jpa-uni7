package br.edu.uni7.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


@NamedQueries({
	@NamedQuery(name="Empregado.findByCidade", 
			query="select e from Empregado e where e.endereco.cidade = :cidade"),
	
	@NamedQuery(name="Empregado.findByNome", 
	query="select e from Empregado e where e.nome = :nome")	
})

@NamedNativeQueries({
	@NamedNativeQuery(name="Empregado.byDocumento", 
					query="select e.* from tbl_empregados e inner join tbl_documentos d on e.FK_DOC = d.PK_DOC "
							+ "WHERE d.NU_NUMERO = ?1", 
					resultClass=Empregado.class)
})

@Entity
@Table(name = "TBL_EMPREGADOS")
public class Empregado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PK_EMP")
	private Long id;

	@Column(name = "NM_NAME")
	@Size(max=40, min=3)
	@NotNull
	private String nome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DEP")
	private Departamento departamento;
	
	@Column(name="NM_EMAIL")
	@Email
	private String email;
	
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;
	
	@Column(name="NU_SALARIO")	
	@DecimalMin("900.00")
	private BigDecimal salario;

	@Embedded
	@AttributeOverrides(value = { 
			@AttributeOverride(name = "cep", column = @Column(name = "NU_COD_POSTAL"))})
	private Endereco endereco;

	@OneToOne(cascade = { CascadeType.REMOVE, CascadeType.PERSIST },
			fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_DOC")
	@Valid
	private Documento documento;

	@ManyToMany
	@JoinTable(name = "TBL_EMP_PRJS", 
	joinColumns = @JoinColumn(name = "FK_EMP") , 
	inverseJoinColumns = @JoinColumn(name = "FK_PROJ") )
	private List<Projeto> projetos = new ArrayList<Projeto>();
	
	@Version
	@Column(name="NU_VERSAO")
	private Long versao;
	
	@PrePersist @PreUpdate
	public void validarCampos() {
		if(nome == null){
			throw new IllegalStateException("O atributo nome é obrigatório");
		}
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}


	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		Empregado other = (Empregado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}	

}
