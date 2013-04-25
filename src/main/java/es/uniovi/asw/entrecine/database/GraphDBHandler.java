package es.uniovi.asw.entrecine.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Customer;
import models.Movie;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.IteratorUtil;

public class GraphDBHandler {

	private GraphDatabaseService graphDb;
	private ExecutionEngine exEngine;
	

	private final String USER_LOGIN = "Login";
	private final String MOVIE_INDEX = "Rel_id";
	private final String MOVIE_TITLE = "Titulo";

	private static GraphDBHandler graphHandler;

	private GraphDBHandler() {
		graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase("../data/graphDB/");
		registerShutdownHook(graphDb);
		exEngine = new ExecutionEngine(graphDb);
	}
	// Testing constructor
	private GraphDBHandler(GraphDatabaseService testDb) {
		graphDb = testDb;
		registerShutdownHook(graphDb);
		exEngine = new ExecutionEngine(graphDb);
	}

	public static GraphDBHandler getConnection() {
		// TODO Auto-generated method stub
		if (graphHandler == null) {
			graphHandler = new GraphDBHandler();
		}
		return graphHandler;
	}
	//For testing purpose
	public static GraphDBHandler getConnection( GraphDatabaseService testDb) {
		// TODO Auto-generated method stub
		graphHandler = new GraphDBHandler(testDb);
		return graphHandler;
	}

	public void registerSale(Customer user, Movie movie) throws Exception {

		Transaction tx = graphDb.beginTx();
		try {
			Node userNode = findUserNode(user);
			Node movieNode = findMovieNode(movie);
			Relationship rel = getSeenRelationshipTo(userNode, movieNode);
			if (rel == null) {
				userNode.createRelationshipTo(movieNode, RelTypes.VISTO);
			}
			tx.success();
		} catch (Exception e) {
			throw e;
		} finally {
			tx.finish();
		}

	}

	public List<Movie> getRecommendations(Customer user) {
		Transaction tx = graphDb.beginTx();
		Node userNode;
		List<Movie> pelis=new ArrayList<Movie>();
		try {
			userNode = findUserNode(user);
			tx.success();
		} finally {
			tx.finish();
		}

		ExecutionResult result = exEngine
				.execute("start n=node("
						+ userNode.getId()
						+ ") match n-->()<--o, n-->()<--o, o-->k where length(n--k)=0 return distinct k");
		 Iterator<Node> n_column = result.columnAs( "k" );
	        for ( Node node : IteratorUtil.asIterable( n_column ) )
	        {
	            // note: we're grabbing the name property from the node,
	            // not from the n.name in this case.
	        	int id=(Integer)node.getProperty(MOVIE_INDEX);
	        	String titulo=(String) node.getProperty(MOVIE_TITLE);
	        	Movie peli= new Movie(id, titulo, null, null);
	            pelis.add(peli);
	        }
		return pelis;
	}

	private Relationship getSeenRelationshipTo(Node userNode, Node movieNode) {
		for (Relationship rel : userNode.getRelationships(RelTypes.VISTO)) {
			if (rel.getOtherNode(userNode).equals(movieNode)) {
				return rel;
			}
		}
		return null;
	}

	private Node findUserNode(Customer user) {
		Index<Node> userIndex = graphDb.index().forNodes("usuarios");
		Node userNode;
		userNode = userIndex.get(USER_LOGIN, user.getLogin()).getSingle();
		if (userNode == null) {
			userNode = graphDb.createNode();
			userNode.setProperty(USER_LOGIN, user.getLogin());
			userIndex.add(userNode, USER_LOGIN, user.getLogin());
		}
		return userNode;
	}

	private Node findMovieNode(Movie movie) {
		Index<Node> movieIndex = graphDb.index().forNodes("peliculas");
		Node movieNode;
		movieNode = movieIndex.get(MOVIE_INDEX, movie.getId()).getSingle();
		if (movieNode == null) {
			movieNode = graphDb.createNode();
			movieNode.setProperty(MOVIE_INDEX, movie.getId());
			movieNode.setProperty(MOVIE_TITLE, movie.getName());
			movieIndex.add(movieNode, MOVIE_INDEX, movie.getId());
		}
		return movieNode;
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
}

enum RelTypes implements RelationshipType {
	VISTO;
}
