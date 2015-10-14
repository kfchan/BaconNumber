/**
 * Compares two vertices based upon their distances from the center of the graph
 * 
 * @author Katherine Chan
 * @author Derek Wong
 */
public class VertexComparator implements Comparator<Vertex>{

	public VertexComparator() {
	}

	@Override
	public int compare(Vertex o1, Vertex o2) {
		if (o1.distance > o2.distance) {
			return 1;
		} if (o1.distance < o2.distance) {
			return -1;
		}
		return 0;
	}
}
