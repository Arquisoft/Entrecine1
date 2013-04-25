package es.uniovi.asw.entrecine;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import models.Customer;
import models.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.test.TestGraphDatabaseFactory;

import es.uniovi.asw.entrecine.database.GraphDBHandler;

public class GraphDatabaseTest {

	private GraphDatabaseService graphDb;
	private Customer user1;
	private Customer user2;
	private Customer user3;
	private Movie mov1;
	private Movie mov2;
	private Movie mov3;
	private Movie mov4;

	@Before
	public void prepareTestDatabase() {
		graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();

		user1 = new Customer(0, null, null, null, null, "Pepito", null);
		user2 = new Customer(1, null, null, null, null, "Juanjo", null);
		user3 = new Customer(2, null, null, null, null, "Prueba", null);
		mov1 = new Movie(0, "Godzilla 9000", null, null);
		mov2 = new Movie(1, "Shakalaka Ultimate 3", null, null);
		mov3 = new Movie(2, "No la he visto", null, null);
		mov4 = new Movie(3, "La de la prueba", null, null);

		try {
			GraphDBHandler.getConnection(graphDb).registerSale(user1, mov1);
			GraphDBHandler.getConnection(graphDb).registerSale(user1, mov2);
			GraphDBHandler.getConnection(graphDb).registerSale(user2, mov1);
			GraphDBHandler.getConnection(graphDb).registerSale(user2, mov2);
			GraphDBHandler.getConnection(graphDb).registerSale(user2, mov3);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void destroyTestDatabase() {
		graphDb.shutdown();
	}

	@Test
	public void checkRegisterSale() {
		Index<Node> userIndex = graphDb.index().forNodes("usuarios");
		Index<Node> movieIndex = graphDb.index().forNodes("peliculas");
		try {
			GraphDBHandler.getConnection().registerSale(user3, mov4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node userNode = userIndex.get("Login", user3.getLogin()).getSingle();
		Node movieNode = movieIndex.get("Rel_id", mov4.getId()).getSingle();
		for(Relationship rel: userNode.getRelationships()){
			assertThat(rel.getOtherNode(userNode).equals(movieNode));
		}
		
	}
	
	@Test
	public void checkRecommendations(){
		List<Movie> lista = GraphDBHandler.getConnection().getRecommendations(user1);
		assertThat(lista.get(0).equals(mov3));
	}
	
	@Test
	public void checkNoRecommendations(){
		List<Movie> lista = GraphDBHandler.getConnection().getRecommendations(user2);
		assertThat(lista.isEmpty());
	}

}
