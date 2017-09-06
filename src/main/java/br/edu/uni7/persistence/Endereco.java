package br.edu.uni7.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Endereco {
	
	@Column(name="NM_RUA")
	private String rua;
	@Column(name="NM_CIDADE")
	private String cidade;
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public Long getCep() {
		return cep;
	}
	public void setCep(Long cep) {
		this.cep = cep;
	}
	@Column(name="NU_CEP")
	private Long cep;
	
}
