package br.edu.uni7.persistence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="TBL_TAREFA_RECORRENTE")
@DiscriminatorValue("TR") 
@PrimaryKeyJoinColumn(name="FK_TAR")
public class TarefaRecorrente extends Tarefa {
	
	@Enumerated(EnumType.STRING)
	@Column(name="NM_PERIODICIDADE")
	private Peridicidade periodicidade;

	public Peridicidade getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Peridicidade periodicidade) {
		this.periodicidade = periodicidade;
	}
	
}
