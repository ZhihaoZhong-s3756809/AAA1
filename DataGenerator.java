import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import java.util.ArrayList;
import joptsimple.OptionParser;
import joptsimple.OptionSet;



public class DataGenerator
{
	private static String[] edge = new String[40000];
	private static String[] edge1 = new String[40000];
	private static int size = 0;
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
		Random r = new Random();
		
		long start = System.currentTimeMillis();
		int count = 0;

		for(int i = 0; i < max; i++) {
			int k = intGenerator(r,0,max);
			int m = intGenerator(r,0,max);
			char scrC = edge[i].charAt(0);
			char tarC = edge[i].charAt(2);
			String scr = String.valueOf(scrC);
			String tar = String.valueOf(tarC);
			graph.deleteEdge(scr, tar);
	//		edge[i] = String.valueOf(i);
			count++;
		}

		
		long end = System.currentTimeMillis();
		
		System.out.println("Processing Time of deleting " + count + " edges is : " + (end - start) + "ms");
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
		vertexAddiction(graph, 3);
		PrintWriter os = new PrintWriter(System.out, true);
		edgeAddiction(graph,50,3);
//		graph.printVertices(os);
//		vertexDeletion(graph,3,3);
//		graph.printVertices(os);
		edgeDeletion(graph,50);
//		graph.printEdges(os);
	//	khopTesting(graph,1,1000);
	//	khopTesting(graph,3,1000);
//		khopTesting(graph,6,1000);
//		khopTesting(graph,9,1000);
//		khopTesting(graph,12,1000);
//		khopTesting(graph,15,1000);
	} // end of main()

} // end of class RmitCovidModelling

