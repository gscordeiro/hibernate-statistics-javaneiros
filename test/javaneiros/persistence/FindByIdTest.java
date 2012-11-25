package javaneiros.persistence;

import javaneiros.entity.Marca;
import javaneiros.entity.Modelo;

import javax.persistence.EntityManager;

import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unused")
public class FindByIdTest {

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
	public void deveBuscarUmObjetoPorId(){
		
		Marca marca = new Marca("Volks");
		
		em.persist(marca);
		em.clear();
		
		Marca volks = em.find(Marca.class, marca.getId());
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
		
		System.out.println("faz qualquer coisa");
		
		Marca volks2 = em.find(Marca.class, marca.getId());
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
	}
	
	@Test
	public void deveBuscarUmObjetoPorIdViaQuery(){
		
		Marca marca = new Marca("Volks");
		
		em.persist(marca);
		em.clear();
		
		Marca volks = em.createQuery("select m from Marca m where m.id = :id", Marca.class)
						.setParameter("id", marca.getId())
						.getSingleResult();
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
	}
	
	
	
	@Test
	public void deveBuscarUmObjetoPorIdCarregandoRelacionamentoEager(){
		
		Marca volks = new Marca("Volks");
		Modelo fusca = new Modelo("Fusca", volks);
		
		em.persist(fusca);
		em.clear();
		
		Modelo modelo = em.find(Modelo.class, fusca.getId());
		
		Assert.assertEquals(2, statistics.getEntityLoadCount());
		
		
	}
	
	@Test
	public void deveBuscarUmObjetoPorIdSemCarregarRelacionamentoLazy(){
		
		Marca volks = new Marca("Volks");
		Modelo fusca = new Modelo("Fusca", volks);
		
		em.persist(fusca);
		em.clear();
		
		Marca marca = em.find(Marca.class, volks.getId());
		
		Assert.assertEquals(1, statistics.getEntityLoadCount());
		
		System.out.println(marca.getModelos());
		
		Assert.assertEquals(2, statistics.getEntityLoadCount());
		
	}
}
