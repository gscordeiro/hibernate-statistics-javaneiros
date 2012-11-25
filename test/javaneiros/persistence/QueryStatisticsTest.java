package javaneiros.persistence;

import java.util.List;

import javaneiros.entity.Acessorio;
import javaneiros.entity.Carro;
import javaneiros.entity.Concessionaria;
import javaneiros.entity.Marca;
import javaneiros.entity.Modelo;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.stat.Statistics;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unused")
public class QueryStatisticsTest {

	private EntityManager em;
	private Statistics statistics;
	private static int contador;
	
	@Before
	public void iniciaTransacao(){
		System.out.println("======= " + (++contador) + " =========");
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
	public void deveBuscarUmObjetoComRelacionamentoLazy(){
		
		Marca volks = new Marca("Volkswagem");
		volks.adicionaModelo(new Modelo("Gol"));
		volks.adicionaModelo(new Modelo("Polo"));
		volks.adicionaModelo(new Modelo("Fusca"));
		
		Marca fiat = new Marca("Fiat");
		fiat.adicionaModelo(new Modelo("Punto"));
		fiat.adicionaModelo(new Modelo("Bravo"));
		fiat.adicionaModelo(new Modelo("Palio"));
		
		em.persist(volks);
		em.persist(fiat);
		em.clear();
		
		TypedQuery<Marca> query = em.createQuery("select m from Marca m", Marca.class);
		
		List<Marca> marcas = query.getResultList();
		
		Assert.assertEquals(1, statistics.getQueryExecutionCount());
		Assert.assertEquals(2, statistics.getEntityLoadCount());
		
		Assert.assertEquals(0, statistics.getCollectionLoadCount());
		
	}
	
	
	@Test
	public void deveBuscarUmObjetoComRelacionamentoEager(){
		
		Carro carro = new Carro();
		carro.adicionaAcessorio(new Acessorio("Roda de liga leve"));
		carro.adicionaAcessorio(new Acessorio("Teto solar"));
		carro.adicionaAcessorio(new Acessorio("Comandos de som no volante"));
		
		em.persist(carro);
		em.clear();
		
		TypedQuery<Carro> query = em.createQuery("select c from Carro c", Carro.class);
		
		List<Carro> carros = query.getResultList();
		
		Assert.assertEquals(1, statistics.getQueryExecutionCount());
		Assert.assertEquals(4, statistics.getEntityLoadCount());
		
		Assert.assertEquals(1, statistics.getCollectionLoadCount());
		
	}
	
	
	@Test
	public void deveBuscarUmObjetoComRelacionamentoLazyCarregandoComNmais1Query(){
		
		Marca volks = new Marca("Volkswagem");
		volks.adicionaModelo(new Modelo("Gol"));
		volks.adicionaModelo(new Modelo("Polo"));
		volks.adicionaModelo(new Modelo("Fusca"));
		
		Marca fiat = new Marca("Fiat");
		fiat.adicionaModelo(new Modelo("Punto"));
		fiat.adicionaModelo(new Modelo("Bravo"));
		fiat.adicionaModelo(new Modelo("Palio"));
		
		Marca chevrolet = new Marca("Chevrolet");
		chevrolet.adicionaModelo(new Modelo("Celta"));
		chevrolet.adicionaModelo(new Modelo("Omega"));
		chevrolet.adicionaModelo(new Modelo("Cruze"));
		
		
		
		em.persist(volks);
		em.persist(fiat);
		em.persist(chevrolet);
		em.clear();
		
		TypedQuery<Marca> query = em.createQuery("select m from Marca m", Marca.class);
		
		List<Marca> marcas = query.getResultList();
		
		Assert.assertEquals(1, statistics.getQueryExecutionCount());
		Assert.assertEquals(3, statistics.getEntityLoadCount());
		Assert.assertEquals(0, statistics.getCollectionFetchCount());
		
		for (Marca marca : marcas) {
			
			for(Modelo modelo : marca.getModelos()){
				System.out.println(modelo);
			}
			
		}
		
		Assert.assertEquals(12, statistics.getEntityLoadCount());
		Assert.assertEquals(3, statistics.getCollectionFetchCount());
	}
	
	@Test
	public void deveBuscarUmObjetoComRelacionamentoLazyUsandoJoinFetch(){
		
		Marca volks = new Marca("Volkswagem");
		volks.adicionaModelo(new Modelo("Gol"));
		volks.adicionaModelo(new Modelo("Polo"));
		volks.adicionaModelo(new Modelo("Fusca"));
		
		Marca fiat = new Marca("Fiat");
		fiat.adicionaModelo(new Modelo("Punto"));
		fiat.adicionaModelo(new Modelo("Bravo"));
		fiat.adicionaModelo(new Modelo("Palio"));
		
		em.persist(volks);
		em.persist(fiat);
		em.clear();
		
		TypedQuery<Marca> query = em.createQuery("select m from Marca m join fetch m.modelos modelo", Marca.class);
		
		List<Marca> marcas = query.getResultList();
		
		Assert.assertEquals(1, statistics.getQueryExecutionCount());
		Assert.assertEquals(8, statistics.getEntityLoadCount());
		
		Assert.assertEquals("Deveria buscar na mesma query via 'join fetch'", 0, statistics.getCollectionFetchCount());
		Assert.assertEquals("A lista não foi buscada no banco, mas foi carregada para as entidades", 2, statistics.getCollectionLoadCount());
		
	}
	
	@Test
	public void deveBuscarUmObjetoComRelacionamentoLazyCarregandoComBatchFetchSize(){
		
		
		Marca volks = new Marca("Volkswagem");
		volks.adicionaConcessionaria(new Concessionaria("Volks MS 1"));
		volks.adicionaConcessionaria(new Concessionaria("Volks MS 2"));
		
		Marca fiat = new Marca("Fiat");
		fiat.adicionaConcessionaria(new Concessionaria("Fiat MS 1"));
		fiat.adicionaConcessionaria(new Concessionaria("Fiat MS 2"));
		
		Marca ford = new Marca("Ford");
		ford.adicionaConcessionaria(new Concessionaria("Ford MS 1"));
		ford.adicionaConcessionaria(new Concessionaria("Ford MS 2"));
		
		Marca chevrolet = new Marca("Chevrolet");
		chevrolet.adicionaConcessionaria(new Concessionaria("Chevrolet MS 1"));
		chevrolet.adicionaConcessionaria(new Concessionaria("Chevrolet MS 2"));
		
		Marca bmw = new Marca("BMW");
		bmw.adicionaConcessionaria(new Concessionaria("BMW MS 1"));
		bmw.adicionaConcessionaria(new Concessionaria("BMW MS 2"));
		
		Marca ferrari = new Marca("Ferrari");
		ferrari.adicionaConcessionaria(new Concessionaria("Ferrari MS 1"));
		ferrari.adicionaConcessionaria(new Concessionaria("Ferrari MS 2"));
		
		em.persist(volks);
		em.persist(fiat);
		em.persist(ford);
		em.persist(chevrolet);
		em.persist(bmw);
		em.persist(ferrari);
		em.clear();
		
		TypedQuery<Marca> query = em.createQuery("select m from Marca m", Marca.class);
		
		List<Marca> marcas = query.getResultList();
		
		Assert.assertEquals(1, statistics.getQueryExecutionCount());
		Assert.assertEquals(6, statistics.getEntityLoadCount());
		
		Assert.assertEquals(0, statistics.getCollectionFetchCount());
		Assert.assertEquals(0, statistics.getCollectionLoadCount());
		
		
		for (Marca marca : marcas) {
			
			for(Concessionaria concessionaria : marca.getConcessionarias()){
				System.out.println(concessionaria);
			}
			
		}
		
		Assert.assertEquals(18, statistics.getEntityLoadCount());
		//6 collections buscando de 2 em 2 = 3 idas ao banco
		Assert.assertEquals(3, statistics.getCollectionFetchCount());
		Assert.assertEquals(6, statistics.getCollectionLoadCount());
		
	}


}
