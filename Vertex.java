/**
 * Vertex representing name of movie/actor
 * and distance of movie/actor
 * 
 * @author Katherine Chan
 * @author Derek Wong
 *
 */
public class Vertex {
	String name;
	boolean isMovie;
	int distance;
	boolean isVisited;
	Vertex prev;
	boolean bacon;
	double avg;
	int used;
	int notused;

	public Vertex(String n, boolean movie) {
		name = n;
		isMovie = movie;
		distance = Integer.MAX_VALUE;
		isVisited = false;
		prev = null;
		bacon = false;
	}

	public void visited() {
		isVisited = true;
	}

	public String toString() {
		return name;
	}
	
}
