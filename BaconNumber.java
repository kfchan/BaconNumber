/**
 * Bacon Number thing wooo!
 * 
 * @author Katherine Chan 
 * @author Derek Wong
 *
 */
public class BaconNumber {

    static Vertex center;
    static MyGraph g;
    static int max;

    /**
     * This is the main method that loads in the actors and movies
     * 
     * @param args
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Loading ..");
		Scanner sc = new Scanner(System.in);
		g = new MyGraph();
		Scanner s = new Scanner(new URL(args[0]).openStream());

		while (s.hasNext()) {
		    String line = s.nextLine();
		    String[] split = line.split("\\|");
		    String actor = split[0];
		    String movie = split[1];
		    g.insertVertex(actor, false);
		    g.insertVertex(movie, true);
		    g.insertEdge(actor, movie);
		    g.insertEdge(movie, actor);
		}

		center = g.getVertex("kevin bacon (i)");

		setDistances();
		System.out.println("Done!");

		while(true) {

		    System.out.print("Type in a command (type 'no' to exit) : ");
		    String userInput = sc.nextLine();

		    String[] inputs = new String[2];

		    Scanner sc2 = new Scanner(userInput);

		    inputs[0] = sc2.next();
		    inputs[1] = "";
		    abbakadaba = inputs[0];

		    if (sc2.hasNext()) {
				inputs[1] = sc2.next();
		    }

		    while(sc2.hasNext() ){
				inputs[1] += " " + sc2.next();
		    }

		    if(inputs[0].equalsIgnoreCase("find")) {
				Vertex node = find(inputs[1].toLowerCase());
				System.out.println();
				if (node != null) {
				    found(node);
				} else {
				    System.out.println("No bacon number for " + inputs[1] + " ah!");
				}
		    } else if(inputs[0].equalsIgnoreCase("recenter")) {
				recenter(inputs[1].toLowerCase());
		    } else if(inputs[0].equalsIgnoreCase("avgdist")) {
				double[] array = avgdist();
				avgdistPrint(array[0],(int) array[1], (int) array[2]);
		    } else if(inputs[0].equalsIgnoreCase("topcenter")) {
				int i = Integer.parseInt(inputs[1]);
				topcenter(i);
		    } else if(inputs[0].equalsIgnoreCase("table")) {
				table();
		    } else if(inputs[0].equalsIgnoreCase("movies")) {
				movies(inputs[1]);
		    } else if(inputs[0].equalsIgnoreCase("most")) {
				most();
		    } else if(inputs[0].equalsIgnoreCase("findall")) {
				findall();
		    } else if(inputs[0].equalsIgnoreCase("longest")) {
				longest();
		    } else if (inputs[0].equalsIgnoreCase("no")){
				System.out.println();
				System.out.println("Bye!");
				break;
		    } else {
				System.out.println();
				System.out.println("Nope, that wasn't a function. Try again!");
		    }
		    System.out.println();
		    System.out.println();
		}
    }

    /**
     * Calculates distances for all vertices according to whatever the center is
     */
    public static void setDistances() {
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();
		while (it.hasNext()) {
		    Vertex woo = it.next();
		    woo.distance = Integer.MAX_VALUE;
		    woo.isVisited = false;
		}
		MyPriorityQueue<Vertex> q = new MyPriorityQueue<Vertex>(new VertexComparator());;
		int counter = 0;
		center.distance = 0;
		q.add(center);
		while (!q.isEmpty() && counter < g.V.size()) {
		    Vertex node = q.poll();
		    if (node.isVisited) {
				continue;
		    }
		    node.visited();
		    counter++;
		    ArrayList<Edge> outb = g.incidentEdges(node);
		    Iterator<Edge> it2 = outb.iterator();
		    while (it2.hasNext()) {
				Edge edge = it2.next();
				Vertex node2 = g.getVertex(edge.dest);
				if ((node.distance + edge.weight) < node2.distance) {
				    node2.distance = node.distance + edge.weight;
				    if (node2.distance > max) {
						max = node2.distance;
				    }
				    node2.prev = node;
				    q.add(node2); 
				}
		    }
		}
    }


    /**
     * Recenters the center to a different actor.
     * 
     * @param name
     */
    public static void recenter(String name) {
		try {
		    Vertex b = g.getVertex(name.toLowerCase());
		    center = b;
		    setDistances();
		} catch (NullPointerException e) {
			    name += " (i)";
			    if (g.getVertex(name) == null) {
				System.out.println(name + "is not in the database.");
		    }
		    Vertex b = g.getVertex(name.toLowerCase());
		    center = b;
		    setDistances();
		}
    }

    /**
     * Finds a short path or something idk
     * 
     * @param poopybutt
     */
    public static Vertex find(String poopybutt) {
		try {
		    Vertex node = g.getVertex(poopybutt.toLowerCase());
		    if (node.prev != null) {
				return node;
		    }
		    return null;
		} catch (NullPointerException e) {
		    poopybutt += " (i)";
		    Vertex node = g.getVertex(poopybutt.toLowerCase());
		    if (node.prev != null) {
				return node;
		    }
		    return null;
		}
    }

    /**
     * helper method?
     * 
     * @param node
     */
    public static void found(Vertex node) {
		int n = node.distance;
		String s = node.name;

		while (node.prev != null) {
		    s += " => " + node.prev.name;
		    node = node.prev;
		}

		s += " (" + n + ")";
		System.out.println(s);
    }

    /**
     * Gives the average distance of the current node to other vertices.
     * 
     * 
     * @return an array.  [0] contains the average distance of the current node, [1] contains the total number of things.
     */
    public static double[] avgdist() {
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();

		int total = 0; //total distance
		int reached = 0; //actors reachable
		int numactors = 0; //total actors

		while (it.hasNext()) {
		    Vertex node = it.next();

		    if (!node.isMovie) {
			numactors++;
				if (node.distance != Integer.MAX_VALUE && !node.isMovie) {
				    total += node.distance;
				    reached++;
				} 
		    }
		}

		if (reached == 0) {
		    double[] we = new double[3];
		    we[0] = 0; 
		    we[1] = numactors;
		    we[2] = 0;
		    return we;
		}

		double[] array = new double[3];

		array[0] = (double) total / (double) reached;
		array[1] = numactors;
		array[2] = reached;

		return array;
    }

    /**
     * Helper method to print out stuffs.
     * 
     * @param average
     * @param total
     */
    public static void avgdistPrint(double average, int numactors, int reached){
	System.out.printf("%8f", (average));
	System.out.print("  " + center.name + "  (");
	System.out.printf("%d", reached);
	System.out.print(",");
	System.out.printf("%d", numactors-reached);
	System.out.print(")");
    }

    /**
     * Diplays a table of the number of links to all other actors
     */
    public static void table() {
		int[] table = new int[100];
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();
		int max = 0;
		int unreachable = 0;
		while (it.hasNext()) {
		    Vertex node = it.next();
		    if (node.distance != Integer.MAX_VALUE && !node.isMovie) {
				if (node.distance > max) {
				    max = node.distance;
				}
				table[node.distance] = table[node.distance] + 1;
		    } else if (node.distance == Integer.MAX_VALUE) {
				unreachable++;
		    }
		}
		System.out.println();
		System.out.println("Table of distances for " + center.name + ":");

		for (int i = 0; i < max + 1; i++) {
		    System.out.print("Number");
		    System.out.printf("%5d: %8d", i, table[i]);
		    System.out.println();
		}

		System.out.print("Unreachable:");
		System.out.printf("%9d", unreachable);
    }

    /**
     * prints out the best centers 
     * 
     * @param n must be a value >0
     */
    public static void topcenter(int n) {
		PriorityQueue<Vertex> k = new PriorityQueue<Vertex>(10,new ActorComparator());
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();
		Vertex oldcen = center;
		while (it.hasNext()) {
		    Vertex vertex = it.next();
		    if (vertex.distance<Integer.MAX_VALUE && !vertex.isMovie) {
				recenter(vertex.name);
				double[] rtn = avgdist();
				vertex.avg = rtn[0];
				vertex.used = (int) rtn[2];
				vertex.notused = (int) (rtn[1] - rtn[2]);
				k.offer(vertex);
		    }
		}
		recenter(oldcen.name);

		System.out.println();
		System.out.println("The best " + n + " centers areee:");
		System.out.println();

		for (int i = 0; i < n; i++) {
		    Vertex node = k.poll();
		    System.out.printf("%10s (%d,%d): %8f ", node.name, node.used, node.notused, node.avg);
		    System.out.println();
		}
    }

    /**
     * prints who has the most links to movies.
     */
    public static void most() {
		int most = 0;
		Vertex v = null;
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();
		while (it.hasNext()) {
		    Vertex vertex = it.next();
		    if (!vertex.isMovie) {
			ArrayList<Edge> edge = g.incidentEdges(vertex);
				if (edge.size() > most) {
				    most = edge.size();
				    v = vertex;
				}
		    }
		}
		System.out.println();
		System.out.println(v.name + " has the most film credits!");
		System.out.println("(or one of the ones with the most)");
    }

    /**
     * returns which movies a passed-in name has acted in.
     * @param name
     */
    public static void movies(String name) {
		try {
		    Vertex vertex = g.getVertex(name);
		    ArrayList<Edge> k = g.incidentEdges(vertex);
		    System.out.println(k);
		    for (int i = 0; i < k.size(); i++) {
				Edge h = k.get(i);
				System.out.println(h.dest);
		    }
		} catch (NullPointerException m) {
		    name += " (i)";
		    Vertex vertex = g.getVertex(name);
		    ArrayList<Edge> k = g.incidentEdges(vertex);
		    System.out.println();
		    System.out.println(vertex.name + " is in:");
		    System.out.println();
		    for (int i = 0; i < k.size(); i++) {
				Edge h = k.get(i);
				System.out.println(h.dest);
		    }
		}
    }

    /**
     * Iterates through all actors and actresses and performs a find operation on them.
     */
    public static void findall() {
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();
		while (it.hasNext()) {
		    Vertex node = it.next();
		    if (!node.isMovie) {
			Vertex n = find(node.name);
				if (n != null) {
				    found(node);
				} else {
				    System.out.println("No path for " + node.name);
				}
		    }
		}
    }

    /**
     * Finds a vertex with the highest bacon number
     */
    public static void longest() {
		Collection<Vertex> a = g.V.values();
		Iterator<Vertex> it = a.iterator();
		Vertex longnode = null;
		while (it.hasNext()) {
		    Vertex node = it.next();
		    if (node != null && !node.isMovie) {
				if (node.distance == max) {
				    max = node.distance;
				    longnode = node;
				    break;
				}
		    }
		}
		Vertex node = find(longnode.name);
		found(node);
    }

}
