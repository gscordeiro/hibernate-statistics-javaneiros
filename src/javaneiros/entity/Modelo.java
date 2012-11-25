package javaneiros.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Modelo {

	@Id @GeneratedValue
	private Integer id;
	private String nome;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Marca marca;
	
	public Modelo() {
	}
	
	public Modelo(String nome) {
		this.nome = nome;
	}
	
	public Modelo(String nome, Marca marca) {
		this(nome);
		this.marca = marca;
	}
	
	@Override
	public String toString() {
		return "Modelo [id=" + id + ", nome=" + nome + ", marca=" + marca + "]";
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
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	
}
