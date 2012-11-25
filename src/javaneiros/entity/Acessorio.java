package javaneiros.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Acessorio {

	@Id @GeneratedValue
	private Integer id;
	private String descricao;
	@ManyToOne
	private Carro carro;
	
	public Acessorio() {
	}
	
	public Acessorio(String descricao) {
		this.descricao = descricao;
	}
	public Acessorio(String descricao, Carro carro) {
		this(descricao);
		this.carro = carro;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Carro getCarro() {
		return carro;
	}
	public void setCarro(Carro carro) {
		this.carro = carro;
	}
	
	
}
