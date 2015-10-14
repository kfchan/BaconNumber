/**
 * An edge between two Vertices
 * 
 * @author Katherine Chan
 * @author Derek Wong
 *
 */
public class Edge {
	String origin;
	String dest;
	int weight;

	public Edge(String o, String d, int w) {
		origin = o;
		dest = d;
		weight = w;
	}

	public String toString() {
		return origin;
	}

}
