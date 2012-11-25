package javaneiros.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Concessionaria {

	@Id @GeneratedValue
	private Integer id;
	private String nome;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Marca marca;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="concessionaria")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private List<Funcionario> funcionarios;
	
	public Concessionaria() {
		funcionarios = new ArrayList<>();
	}
	
	public Concessionaria(String nome) {
		this();
		this.nome = nome;
	}
	
	public Concessionaria(String nome, Marca marca) {
		this(nome);
		this.marca = marca;
	}
	
	public void adicionaFuncionario(Funcionario funcionario){
		funcionarios.add(funcionario);
		funcionario.setConcessionaria(this);
	}
	
	@Override
	public String toString() {
		return "Concessionaria [id=" + id + ", nome=" + nome + ", marca=" + marca + "]";
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

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	
}
