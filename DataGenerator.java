import java.util.List;
import java.util.Random;
import java.io.PrintWriter;
import java.util.ArrayList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;



public class DataGenerator
{
	private static String[] edge = new String[40000];
//	private static String[] edge1 = new String[40000];
	private static int size = 0;
	@SuppressWarnings("unused")
	private static String[] vertices = new String[size];
	
	public static int intGenerator(Random r, int min, int max) {
		return r.nextInt(max - min) + min;
	}

	public static void vertexAddiction(ContactsGraph graph, int amount) {
		long start = System.currentTimeMillis();
		for(int i = 0; i< amount; i++) {
			graph.addVertex(String.valueOf(i));
		}
		size = amount;
		long end = System.currentTimeMillis();
		
		System.out.println("Processing Time of Adding " + amount + " vertex is :" + (end - start) + "ms");
	}
	
	public static void khopTesting(ContactsGraph graph, int k, int amount) {
		Random r = new Random();
		long start = System.currentTimeMillis();
		int m = intGenerator(r,0,amount);
		String[] neighbours = graph.kHopNeighbours(k, String.valueOf(m));
		
		long end = System.currentTimeMillis();

		System.out.println("Processing Time of Finding khopneighbour of " + m+ " with length of "+ neighbours.length  + " is :" + (end - start) + "ms");
	}
	
	public static void edgeAddiction(ContactsGraph graph,int size, int vertices) {
		long start = System.currentTimeMillis();
		Random r = new Random();
		

		for(int i = 0; i < size; i++) {
			int k = intGenerator(r,0,vertices);
			int m = intGenerator(r,0,vertices);
			if(k != m) {
			graph.addEdge(String.valueOf(k), String.valueOf(m));
			}
			edge[i] =String.valueOf(k) + " " + String.valueOf(m);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Processing Time of adding " + size + " edges is : " + (end - start) + "ms");

	}
	public static void vertexDeletion(ContactsGraph graph, int max, int total) {
		
		long start = System.currentTimeMillis();
		for(int i = 0; i<max; i++) {
			graph.deleteVertex(String.valueOf(i));
		}		
		long end = System.currentTimeMillis();
		
		System.out.println("Processing Time of deleting " + max + " vertex is : " + (end - start) + "ms");
	} // end of processOperations()

	public static void edgeDeletion(ContactsGraph graph, int max) {
		
		long start = System.currentTimeMillis();
		int count = 0;

		for(int i = 0; i < max; i++) {
			char scrC = edge[i].charAt(0);
			char tarC = edge[i].charAt(2);
			String scr = String.valueOf(scrC);
			String tar = String.valueOf(tarC);
			graph.deleteEdge(scr, tar);
			count++;
		}

		
		long end = System.currentTimeMillis();
		
		System.out.println("Processing Time of deleting " + count + " edges is : " + (end - start) + "ms");
	}
	
	public static void sirSimulation(ContactsGraph graph, int seedMaxNum, int seedsNum) {
		String seeds[] = new String[seedsNum];
		Random r = new Random();
		for(int i=0; i<seedsNum; i++) {
			int seed = intGenerator(r,0,seedMaxNum);
			seeds[i] = String.valueOf(seed);
		}
		float infectionProb = (float) 0.8;
		float recoverProb = (float) 0.5;
		PrintWriter pw = new PrintWriter(System.out,true);
		SIRModel sir = new SIRModel();
		graph.toggleVertexState("0");
		
		long start = System.currentTimeMillis();
		
		sir.runSimulation(graph, seeds, infectionProb, recoverProb, pw);
		
		long end = System.currentTimeMillis();
		
		System.out.println("Processing Time of simulating the SIR model is : " + (end - start) + "ms");
	}

	/**
	 * Main method.  Determines which implementation to test and processes command line arguments.
	 */
	public static void main(String[] args) {
		// parse command line options
		OptionParser parser = new OptionParser("f:o:");
		OptionSet options = parser.parse(args);
		// non option arguments
		List<?> tempArgs = options.nonOptionArguments();
		List<String> remainArgs = new ArrayList<String>();
		for (Object object : tempArgs) {
			remainArgs.add((String) object);
		}

		// check number of non-option command line arguments
		if (remainArgs.size() > 1 || remainArgs.size() < 1) {
			System.err.println("Incorrect number of arguments.");
		}

		// parse non-option arguments
		String implementationType = remainArgs.get(0);

		// determine which graph implementation to test
		ContactsGraph graph = null;
		switch(implementationType) {
			case "adjlist":
				graph = new AdjacencyList();
				break;
			case "adjmat":
				graph = new AdjacencyMatrix();
				break;
			case "incmat":
				graph = new IncidenceMatrix();
				break;
		}
		vertexAddiction(graph, 300);
		edgeAddiction(graph,1000,300);
		sirSimulation(graph,300,50);
	} // end of main()

} // end of class RmitCovidModelling

