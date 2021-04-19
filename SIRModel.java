import java.io.PrintWriter;
import java.util.Random;

/**
 * SIR model.
 *
 * @author Jeffrey Chan, 2021.
 */
public class SIRModel
{

    /**
     * Default constructor, modify as needed.
     */
    public SIRModel() {

    } // end of SIRModel()


    /**
     * Run the SIR epidemic model to completion, i.e., until no more changes to the states of the vertices for a whole iteration.
     *
     * @param graph Input contracts graph.
     * @param seedVertices Set of seed, infected vertices.
     * @param infectionProb Probability of infection.
     * @param recoverProb Probability that a vertex can become recovered.
     * @param sirModelOutWriter PrintWriter to output the necessary information per iteration (see specs for details).
     */
    public void runSimulation(ContactsGraph graph, String[] seedVertices,
        float infectionProb, float recoverProb, PrintWriter sirModelOutWriter)
    {    	
    	for(String vertex: graph.getMap().keySet()) {
    		if(graph.getMap().get(vertex).equals("I") || graph.getMap().get(vertex).equals("R")){
    			graph.getMap().put(vertex, "S");
    		}
    	} // Set all individual to Susceptible at first
    	
    	for(int k=0; k<seedVertices.length; k++) {
    		for(String vertex: graph.getMap().keySet()) {
    			if(vertex.equals(seedVertices[k])) {
    				graph.getMap().put(vertex, "I");
    			}
    		}
    	}
    	
    	StringBuilder sb = new StringBuilder();
        boolean IorRExisting = true; // check if the individual with status Infected or Recovered still existing
        int countIterations = 1;
        while(IorRExisting && countIterations < 11) {
//        	String[] infectedInThisRound = new String[1];
    	    boolean noIRExisting = true; // check if no individual with status Infected or Recovered still existing
    	    for(String vertex: graph.getMap().keySet()) {
    		    if(graph.getMap().get(vertex).equals("I") || graph.getMap().get(vertex).equals("R")) {
    			    noIRExisting = false;
    		    }
    	    }
    	    if(noIRExisting == true) {
    		    IorRExisting = false;
    		    break;
    	    }
    	    
    	    sb.append(countIterations + ": [");
    	    for(int i=0; i<seedVertices.length; i++) {
    	    	String[] neighbours = graph.kHopNeighbours(1, seedVertices[i]);
                for(int j=0; j<neighbours.length; j++) {
              	    if(graph.getMap().get(neighbours[j]).equals("S")) {
              		    Random random = new Random();
              		    double prob = random.nextDouble();
              		    if(infectionProb > prob) {
              			    graph.toggleVertexState(graph.kHopNeighbours(1, seedVertices[i])[j]);
                			sb.append(graph.kHopNeighbours(1, seedVertices[i])[j] + " ");
              		    }
              	    }
                }
    	    }
    	    sb.append("] : [");
    	    for(String vertex: graph.getMap().keySet()) {
    	    	if(graph.getMap().get(vertex).equals("I")) {
    	    		Random random = new Random();
            		double prob = random.nextDouble();
            		if(recoverProb > prob) {
            			graph.getMap().put(vertex, "R");
            			sb.append(vertex+ " ");
            		}
    	    	}
    	    }
    	    sb.append("]\n");
    	    countIterations++;
        }
        sirModelOutWriter.println(sb);
    } // end of runSimulation()
} // end of class SIRModel
