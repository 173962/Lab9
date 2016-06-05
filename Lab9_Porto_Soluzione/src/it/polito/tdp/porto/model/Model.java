package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {

	List<Articolo> articoli;
	List<Autore> autori;
	PortoDAO portoDAO;

	// Creo un grafo semplice
	// UndirectedGraph<Autore, Articolo> graph;

	// Credo un multigrafo
	Multigraph<Autore, Articolo> graph;

	public Model() {
		this.portoDAO = new PortoDAO();

		// Utilizzo un grafo semplice
		graph = new Multigraph<Autore, Articolo>(Articolo.class);

		// Utilizzo un multigrafo
		// TODO

		this.setupGraph();
	}

	public void setupGraph() {

		autori = portoDAO.getAllAutori();
		articoli = portoDAO.getAllArticoli();

		// Aggiungo tutti i vertici
		Graphs.addAllVertices(graph, autori);

		// Aggiungo tutti gli archi (Articoli)
		for (Autore autoreSource : autori) {

			portoDAO.fillArticoliInAutori(autoreSource, articoli);

			for (Articolo articolo : autoreSource.getArticoli()) {
				portoDAO.fillAutoriInArticoli(articolo, autori);

				for (Autore autoreDestination : articolo.getAutori()) {
					if (!autoreSource.equals(autoreDestination)) {
						graph.addEdge(autoreSource, autoreDestination, articolo);
					}
				}
			}
		}

		System.out.println("Building graph finished!");
	}

	public List<Autore> getAutori() {
		if (autori == null)
			autori = new LinkedList<Autore>();
		return this.autori;
	}

	public List<Autore> getAutoriNeighbours(Autore autore) {
		return Graphs.neighborListOf(graph, autore);
	}

	public int getNumberOfConnectedSubgraphs() {

		// Creo una copia della lista di autori.
		List<Autore> autoriDuplicati = new ArrayList<Autore>(autori);
		int clusterCounter = 0;

		while (!autoriDuplicati.isEmpty()) {

			System.out.println("\nCluster " + String.valueOf(clusterCounter + 1));

			// Ottengo il primo autore
			Autore autoreSource = autoriDuplicati.get(0);

			// Utilizzo DepthFirstIterator, in alternativa potrei utilizzare un
			// BreadthFirstIterator<V, E> o una funzione ricorsiva custom.
			GraphIterator<Autore, Articolo> dfi = new DepthFirstIterator<Autore, Articolo>(graph, autoreSource);
			while (dfi.hasNext()) {
				Autore autoreTarget = dfi.next();
				System.out.println(autoreTarget);
				autoriDuplicati.remove(autoriDuplicati.indexOf(autoreTarget));
			}
			clusterCounter++;
		}
		return clusterCounter;
	}

	public List<Articolo> getArticoloCamminoMinimo(Autore autoreSource, Autore autoreTarget) {
		DijkstraShortestPath<Autore, Articolo> dijstraShortestPath = new DijkstraShortestPath<Autore, Articolo>(graph,
				autoreSource, autoreTarget);
		return dijstraShortestPath.getPathEdgeList();
	}

	public List<List<Articolo>> get5CamminiAriticolo(Autore autoreSource, Autore autoreTarget) {

		// Limito la ricerca di tutti i cammini disponibili tra la sorgente e la
		// destinazione a 5.

		Integer counter = 0;
		List<Articolo> currentPath = new ArrayList<Articolo>();
		List<List<Articolo>> fivePaths = new ArrayList<List<Articolo>>();

		recursiveFivePaths(currentPath, fivePaths, counter, autoreSource, autoreTarget);

		System.out.println("Numero di path trovati: " + fivePaths.size());

		return fivePaths;

	}

	private boolean recursiveFivePaths(List<Articolo> currentPath, List<List<Articolo>> fivePaths, Integer counter,
			Autore autoreCurrent, Autore autoreTarget) {

		// Condizione di terminazione:
		if (autoreCurrent.equals(autoreTarget) || currentPath.size() == graph.vertexSet().size()) {

			counter++;
			// Deep copy del contenuto di currentPath
			fivePaths.add(new ArrayList<Articolo>(currentPath));
			// Aggiungo la lista di currentPath ai fivePaths
			if (counter == 5) {
				return true;
			}
			return false;
		}

		// Itero sui vicini dell'Autore corrente
		for (Autore autoreVicino : Graphs.neighborListOf(graph, autoreCurrent)) {

			// add a new article
			currentPath.add(graph.getEdge(autoreCurrent, autoreVicino));

			if (recursiveFivePaths(currentPath, fivePaths, counter, autoreCurrent, autoreTarget) == true)
				return true;

			// remove an article
			currentPath.remove(graph.getEdge(autoreCurrent, autoreVicino));
		}

		return false;
	}

}
