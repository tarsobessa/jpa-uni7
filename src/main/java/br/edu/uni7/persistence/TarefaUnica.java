package br.edu.uni7.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TBL_TAREFA_UNICA")
@DiscriminatorValue("TU") 
@PrimaryKeyJoinColumn(name="FK_TAR")
public class TarefaUnica extends Tarefa {
	
	@Column(name="DT_DATAEXECUCAO")
	@Temporal(TemporalType.DATE)
	Date dataExecucao;

	public Date getDataExecucao() {
		return dataExecucao;
	}

	public void setDataExecucao(Date dataExecucao) {
		this.dataExecucao = dataExecucao;
	}
	
}
