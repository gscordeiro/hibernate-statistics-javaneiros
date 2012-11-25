package javaneiros.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Carro {

	@Id @GeneratedValue
	private Integer id;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	private Modelo modelo;
	private Integer anoFabricacao;
	private Integer anoModelo;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="carro")
	private List<Acessorio> acessorios;
	
	public Carro() {
		acessorios = new ArrayList<>();
	}
	
	public Carro(Modelo modelo, Integer anoFabricacao, Integer anoModelo) {
		this();
		this.modelo = modelo;
		this.anoFabricacao = anoFabricacao;
		this.anoModelo = anoModelo;
	}
	
	
	@Override
	public String toString() {
		return "Carro [id=" + id + ", modelo=" + modelo
				+ ", anoFabricacao=" + anoFabricacao + ", anoModelo="
				+ anoModelo + "]";
	}

	public void adicionaAcessorio(Acessorio acessorio){
		this.acessorios.add(acessorio);
		acessorio.setCarro(this);
	}
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Modelo getModelo() {
		return modelo;
	}
	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}
	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}
	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}
	public Integer getAnoModelo() {
		return anoModelo;
	}
	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(List<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}
}
