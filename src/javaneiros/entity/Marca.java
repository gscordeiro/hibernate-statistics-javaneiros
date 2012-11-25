package javaneiros.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.BatchSize;

@Entity
public class Marca {

	@Id @GeneratedValue
	private Integer id;
	private String nome;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="marca")
	private List<Modelo> modelos;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true, mappedBy="marca")
	@BatchSize(size=2)
	private List<Concessionaria> concessionarias;
	
	public Marca() {
		modelos = new ArrayList<>();
		concessionarias = new ArrayList<>();
	}
	
	public Marca(String nome) {
		this();
		this.nome = nome;
	}
	
	
	public void adicionaModelo(Modelo modelo){
		this.modelos.add(modelo);
		modelo.setMarca(this);
	}
	
	public void adicionaConcessionaria(Concessionaria concessionaria){
		this.concessionarias.add(concessionaria);
		concessionaria.setMarca(this);
	}
	
	@Override
	public String toString() {
		return "Marca [id=" + id + ", nome=" + nome + "]";
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
	public List<Modelo> getModelos() {
		return modelos;
	}
	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}

	public List<Concessionaria> getConcessionarias() {
		return concessionarias;
	}

	public void setConcessionarias(List<Concessionaria> concessionarias) {
		this.concessionarias = concessionarias;
	}
	
}
