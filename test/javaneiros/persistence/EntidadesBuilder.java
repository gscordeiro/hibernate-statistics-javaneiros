package javaneiros.persistence;

import java.util.List;

import javaneiros.entity.Carro;
import javaneiros.entity.Marca;
import javaneiros.entity.Modelo;
import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;

public class EntidadesBuilder {

	public EntidadesBuilder() {
		Fixture.of(Marca.class).addTemplate("default", new Rule(){{
			add("id", random(Integer.class, range(1, 200)));
			add("nome", random("Ford", "Chevrolet", "Fiat", "Volkswagem"));
		}});
		
		Fixture.of(Modelo.class).addTemplate("default", new Rule(){{
			add("id", random(Integer.class, range(1, 200)));
			add("nome", random("Punto", "Corsa", "Monza", "Fusca", "Kombi"));
			add("marca", fixture(Marca.class, "default"));
		}});
		
		Fixture.of(Carro.class).addTemplate("default", new Rule(){{
		    add("id", random(Integer.class, range(1, 200)));
		    add("anoFabricacao", random(Integer.class, range(2000, 2012)));
		    add("anoModelo", random(Integer.class, range(2000, 2013)));
		    add("modelo", fixture(Modelo.class, "default"));
		}});
	}
	
	public <T> T criarUm(Class<T> clazz){
		return Fixture.from(clazz).gimme("default");
	}
	
	public <T> List<T> criar(int numero, Class<T> clazz){
		return Fixture.from(clazz).gimme(numero, "default");
	}
}
