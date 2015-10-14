/**
 * Compares two vertices based upon their distances from the center of the graph
 * 
 * @author Katherine Chan
 * @author Derek Wong
 */
public class ActorComparator implements Comparator<Vertex>{

	public ActorComparator() {
	}

	@Override
	public int compare(Vertex o1, Vertex o2) {
		if (o1.avg > o2.avg) {
			return 1;
		} if (o1.avg < o2.avg) {
			return -1;
		}
		return 0;
	}

}
