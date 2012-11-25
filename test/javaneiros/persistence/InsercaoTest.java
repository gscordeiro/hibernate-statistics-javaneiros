package javaneiros.persistence;

import javaneiros.entity.Marca;
import javaneiros.entity.Modelo;

import javax.persistence.EntityManager;

import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InsercaoTest {

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
	public void deveInserirUmObjetoSimples(){
		
		Marca volks = new Marca("Volks");
		
		em.persist(volks);
		
		Assert.assertEquals(1, statistics.getEntityInsertCount());
		
	}
	
	@Test
	public void deveInserirUmObjetoComCascade(){
		
		Marca volks = new Marca("Volks");
		Modelo fusca = new Modelo("Fusca", volks);
		
		em.persist(fusca);
		
		Assert.assertEquals(2, statistics.getEntityInsertCount());
		
	}
}
