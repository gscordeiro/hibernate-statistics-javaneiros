Código da palestra ["Hibernate Statistics e pitadas de performance com JPA" - Javaneiros 2012](http://www.javaneiros.com.br/2012/11/16/gilliard-cordeiro/)
============

Palestra com o objetivo de apresentar o HibernateStatistics como uma forma de nos ajudar a cuidar da camada de persistência da nossa aplicação.
Para que as estatísticas façam mais sentido, serão apresentados conceitos como lazy load e problemas como o N+1 queries.

A ideia de usar os testes é porque fica bem mais fácil analisar os cenários.

Nesse projeto testamos:

- Número de inserts simples
- Número de inserts com cascade
- Load entities by id simples
- Load entities by id relacionamento eager
- Load entities by id relacionamento lazy
- Load entities query simples lazy
- Load entities query simples lazy eager
- Load entities query join fetch
- Load entities query batch fetch size
- Load entities usando o size() em lista lazy
- Load entities usando o size() com extra lazy

Na classe GeradorDeCarros uso o projeto [Fixture Factory](https://github.com/aparra/fixture-factory)

No persistence.xml e nas libs tem a opção de rodar o projeto usando MySQL. 
Mas o projeto vem configurado com o HSQLDB, assim é só baixar e rodar  \o/
