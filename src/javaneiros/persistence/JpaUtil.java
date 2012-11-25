package javaneiros.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.stat.Statistics;

/**
 * Essa classe pode ser usada junto com o OpenSessionInView dos projetos web.
 *
 */
public class JpaUtil {

	private static final String PERSISTENCE_UNIT_NAME = "default";

	private static ThreadLocal<EntityManager> threadLocal = new ThreadLocal<EntityManager>();

	private static EntityManagerFactory emf;

	private JpaUtil() {
	}

	public static EntityManager getEntityManager() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		EntityManager m = threadLocal.get();
		if (m == null) {
			m = emf.createEntityManager();
			threadLocal.set(m);
		}
		return m;
	}
	
	public static Statistics getStatistics(EntityManager em){
		return ((Session)em.getDelegate()).getSessionFactory().getStatistics();
	}

	public static void closeEntityManager() {
		EntityManager em = threadLocal.get();
		if (em != null) {
			em.close();
			threadLocal.set(null);
		}
	}
	
	public static void closeEntityManagerFactory(){
		closeEntityManager();
		emf.close();
	}
}
