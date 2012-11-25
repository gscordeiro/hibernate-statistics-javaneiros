package javaneiros.main;

import java.util.List;

import javaneiros.entity.Carro;
import javaneiros.entity.Marca;
import javaneiros.entity.Modelo;
import br.com.fixturefactory.Fixture;
import br.com.fixturefactory.Rule;

/**
 * Não uso a classe nos testes, mas serve como referência para quando precisarmos criar dados complexos 
 * @author gilliard
 */
public class GeradorDeCarros {

	public static void main(String[] args) {
		
		Fixture.of(Marca.class).addTemplate("simples", new Rule(){{
			add("id", random(Integer.class, range(1, 200)));
			add("nome", random("Ford", "Chevrolet", "Fiat", "Volkswagem"));
		}});
		
		Fixture.of(Modelo.class).addTemplate("completo", new Rule(){{
			add("id", random(Integer.class, range(1, 200)));
			add("nome", random("Punto", "Corsa", "Monza", "Fusca", "Kombi"));
			add("marca", fixture(Marca.class, "simples"));
		}});
		
		Fixture.of(Carro.class).addTemplate("completo", new Rule(){{
		    add("id", random(Integer.class, range(1, 200)));
		    add("anoFabricacao", random(Integer.class, range(2000, 2012)));
		    add("anoModelo", random(Integer.class, range(2000, 2013)));
		    add("modelo", fixture(Modelo.class, "completo"));
		}});
		
		List<Carro> carros = Fixture.from(Carro.class).gimme(5, "completo");
		
		
		for (Carro carro : carros) {
			System.out.println(carro);
		}
		
	}
}
