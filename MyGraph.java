/**
 * My Graph!
 * @author Katherine Chan
 * @author Derek Wong
 *
 */
public class MyGraph {
    Map<String,ArrayList<Edge>> E;
    Map<String,Vertex> V;

    /**
     * Constructor: makes new vertex and edge arraylists
     */
    public MyGraph() {
    	V = new HashMap<String,Vertex>();
    	E = new HashMap<String,ArrayList<Edge>>();
    }

    /**
     * Inserts vertex containing the name of an actor or movie
     * 
     * @param label can be either the Movie title or the Actor name
     * @param isMovie refers to the fact whether or not label is an actor's name or a movie title.
     * @return true if inserted, false if not 
     */
    public boolean insertVertex(String label, boolean isMovie) {
    	Vertex v = new Vertex(label, isMovie);
    	Vertex l = getVertex(label);
    	if (l == null) {
    	    V.put(label.toLowerCase(),v);
    	    return true;
    	}
    	return false;
    }

    /**
     * Inserts edges between an origin and destination and determines its weight (1 or 0)
     * Origin and destination must already be in vertex arraylist
     * @param o : the origin
     * @param d : the destination
     * @return true if inserted, false if not
     */
    public boolean insertEdge(String o, String d) {

    	if (getVertex(o) == null || getVertex(d) == null) {
    	    return false;
    	}

    	Vertex Ori = getVertex(o.toLowerCase());
    	Vertex Des = getVertex(d.toLowerCase());
    	int w;

    	if (Ori.isMovie == Des.isMovie) {
    	    return false;
    	}
    	if (Ori.isMovie) {
    	    w = 1;
    	} else {
    	    w = 0;
    	}

    	Edge e = new Edge(o,d,w);
    	ArrayList<Edge> n = E.get(o.toLowerCase());
    	if (n == null) {
    	    ArrayList<Edge> a = new ArrayList<Edge>();
    	    a.add(e);
    	    E.put(o.toLowerCase(),a);
    	    return true;
    	}
    	n.add(e);
    	return true;
    }


    /**
     * Finds all the edges to a vertex
     * @param vertex
     * @return list of edges with origin vertex
     */
    public ArrayList<Edge> incidentEdges(Vertex vertex) {
    	ArrayList<Edge> a = E.get(vertex.name.toLowerCase());
    	return a;
    }

    /**
     * Gets the vertex of string meow
     * 
     * @param meow 
     * @return vertex v if available, null if not
     */
    public Vertex getVertex(String meow) {
    	Vertex v = V.get(meow.toLowerCase());
    	return v;
    }

    /**
     * Mainly for d bugging purposes
     * 
     * @return string representation of the whole thing
     */
    public String toString() {
	String s = "";
	Collection<Vertex> a = V.values();
	Iterator<Vertex> it = a.iterator();
	while (it.hasNext()) {
	    Vertex v = it.next();
	    s += v.name + ", ";
	}
	return s;
    }
}
