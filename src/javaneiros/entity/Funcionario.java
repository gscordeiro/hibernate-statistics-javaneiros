package javaneiros.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Funcionario {

	@Id @GeneratedValue
	private Integer id;
	private String nome;
	@ManyToOne
	private Concessionaria concessionaria;
	
	public Funcionario() {
	}
	public Funcionario(String nome) {
		this.nome = nome;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Concessionaria getConcessionaria() {
		return concessionaria;
	}
	public void setConcessionaria(Concessionaria concessionaria) {
		this.concessionaria = concessionaria;
	}
}
