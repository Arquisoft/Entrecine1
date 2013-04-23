package es.uniovi.asw.entrecine.database;

import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

public class GraphDBHandler {
	
	private GraphDatabaseService graphDb; 
	
	private static GraphDBHandler graphHandler;
	
	private GraphDBHandler(){
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "../data/graphDB/" );
		registerShutdownHook( graphDb );
	}
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	public static GraphDBHandler getConnection() {
		// TODO Auto-generated method stub
		if(graphHandler==null){
			graphHandler=new GraphDBHandler();
		}
		return graphHandler;
	}
	
	public void registerSale(Usuario user, Pelicula movie){
		Index<Node> userIndex=graphDb.index().forNodes("usuarios");
		Index<Node> movieIndex=graphDb.index().forNodes("peliculas");
		
		Transaction tx=graphDb.beginTx();
		try{
			Node userNode = userIndex.get("USER_LOGIN", user.getLogin()).getSingle();
			Node movieNode = movieIndex.get("Titulo", movie.getTitulo()).getSingle();
			Relationship rel= getSeenRelationshipTo(userNode,movieNode);
			if(rel==null){
				userNode.createRelationshipTo(movieNode, RelTypes.VISTO);
			}
			tx.success();
		}catch(Exception e){
			throw e;
		}
		finally
        {
            tx.finish();
        }
		
	}
	

	public List<Pelicula> getRecommendations(){
		return null;
	}
	
	private Relationship getSeenRelationshipTo(Node userNode , Node movieNode )
    {
        for ( Relationship rel : userNode.getRelationships( RelTypes.VISTO ) )
        {
            if ( rel.getOtherNode( userNode ).equals( movieNode ) )
            {
                return rel;
            }
        }
        return null;
    }

}

enum RelTypes implements RelationshipType{
	VISTO;
}
