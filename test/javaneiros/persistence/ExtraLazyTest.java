package javaneiros.persistence;

import javaneiros.entity.Concessionaria;
import javaneiros.entity.Funcionario;
import javaneiros.entity.Marca;
import javaneiros.entity.Modelo;

import javax.persistence.EntityManager;

import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExtraLazyTest {

	private EntityManager em;
	private Statistics statistics;
	
	@Before
	public void iniciaTransacao(){
		em = JpaUtil.getEntityManager();
		statistics = JpaUtil.getStatistics(em);
		em.getTransaction().begin();
	}
	
	@After
	public void abortaTransacao(){
		em.getTransaction().rollback();
		statistics.clear();
	}
	
	@Test
	public void deveBuscarRelacionamentoAoUsarListaLazy(){
		
		Marca volks = new Marca("Volkswagem");
		volks.adicionaModelo(new Modelo("Gol"));
		volks.adicionaModelo(new Modelo("Polo"));
		volks.adicionaModelo(new Modelo("Fusca"));
		
		em.persist(volks);
		em.clear();
		
		Marca marca = em.find(Marca.class, volks.getId());
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
		Assert.assertEquals(0, statistics.getCollectionFetchCount());
		
		System.out.println(marca.getModelos().size());
		
		Assert.assertEquals(4, statistics.getEntityLoadCount());
		Assert.assertEquals(1, statistics.getCollectionFetchCount());
	}
	
	
	@Test
	public void naoDeveBuscarRelacionamentoAoUsarListaExtraLazy(){
		
		Concessionaria volks = new Concessionaria("Volkswagem MS 1");
		volks.adicionaFuncionario(new Funcionario("João"));
		volks.adicionaFuncionario(new Funcionario("Maria"));
		volks.adicionaFuncionario(new Funcionario("Xico"));
		
		em.persist(volks);
		em.clear();
		
		Concessionaria concessionaria = em.find(Concessionaria.class, volks.getId());
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
		Assert.assertEquals(0, statistics.getCollectionFetchCount());
		
		System.out.println(concessionaria.getFuncionarios().size());
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
		Assert.assertEquals(0, statistics.getCollectionFetchCount());
	}
}
