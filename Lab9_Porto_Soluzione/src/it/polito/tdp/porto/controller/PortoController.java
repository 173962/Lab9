package it.polito.tdp.porto.controller;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ComboBox<Autore> combo1;

	@FXML
	private ComboBox<Autore> combo2;

	@FXML
	private Button btnCoautori;

	@FXML
	private Button btnCluster;

	@FXML
	private Button btnArticoli;

	@FXML
	private TextArea txtArea;

	@FXML
	void cercaArticoli(ActionEvent event) {

		txtArea.clear();

		Autore autoreSource = combo1.getValue();
		Autore autoreTarget = combo2.getValue();

		try {
			List<Articolo> listaArticoli = model.getArticoloCamminoMinimo(autoreSource, autoreTarget);
			if (listaArticoli != null) {
				txtArea.setText("Gli autori sono collegati da " + listaArticoli.size() + " articoli:\n");

				for (Articolo articolo : listaArticoli) {
					txtArea.appendText(articolo.toString() + "\n");
				}
			} else {
				txtArea.setText("I due autori selezionati non risultano collegati");
			}

		} catch (RuntimeException e) {
			txtArea.setText("Si è verificato un errore durante la ricerca degli articoli.");
		}
		
//		try {
//			List<List<Articolo>> listeArticoli = model.get5CamminiAriticolo(autoreSource, autoreTarget);
//			if (listeArticoli != null) {
//				txtArea.setText("Gli autori sono collegati da " + listeArticoli.size() + "articoli:\n");
//
//				for (List<Articolo> listaArticoli : listeArticoli){
//					for (Articolo articolo : listaArticoli) {
//						txtArea.appendText(articolo.toString() + "\n");
//					}
//					txtArea.appendText("\n");
//				}
//				
//			} else {
//				txtArea.setText("I due autori selezionati non risultano collegati");
//			}
//
//		} catch (RuntimeException e) {
//			txtArea.setText("Si è verificato un errore durante la ricerca degli articoli.");
//		}

	}

	@FXML
	void cercaCluster(ActionEvent event) {
		
		try{
			int clusterNumber = model.getNumberOfConnectedSubgraphs();
			txtArea.setText("Found: " + clusterNumber);
			
		} catch (RuntimeException e) {
			txtArea.setText("Si è verificato un errore durante la ricerca degli articoli.");
		}
	}

	@FXML
	void cercaCoautori(ActionEvent event) {

		try{
		
			Autore autoreSelezionato = null;
	
			if (combo1.getValue() != null) {
				autoreSelezionato = combo1.getValue();
			} else if (combo2.getValue() != null) {
				autoreSelezionato = combo1.getValue();
			}
	
			if (autoreSelezionato == null) {
				txtArea.setText("Seleziona un autore");
				return;
	
			} else {
				Set<Autore> coautori = model.getAutoriNeighbours(autoreSelezionato);
				txtArea.setText("Trovati " + coautori.size() + " coautori.\n");
				for (Autore coautore : coautori) {
					txtArea.appendText(coautore + "\n");
				}
			}
		
		} catch (RuntimeException e) {
			txtArea.setText("Si è verificato un errore durante la ricerca degli articoli.");
		}
	}

	@FXML
	void initialize() {
		assert combo1 != null : "fx:id=\"combo1\" was not injected: check your FXML file 'Porto.fxml'.";
		assert combo2 != null : "fx:id=\"combo2\" was not injected: check your FXML file 'Porto.fxml'.";
		assert btnCoautori != null : "fx:id=\"btnCoautori\" was not injected: check your FXML file 'Porto.fxml'.";
		assert btnCluster != null : "fx:id=\"btnCluster\" was not injected: check your FXML file 'Porto.fxml'.";
		assert btnArticoli != null : "fx:id=\"btnArticoli\" was not injected: check your FXML file 'Porto.fxml'.";
		assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'Porto.fxml'.";
	}

	public void setModel(Model model) {

		this.model = model;

		List<Autore> autori = model.getAutori();
		Collections.sort(autori);

		combo1.getItems().addAll(autori);
		combo2.getItems().addAll(autori);
	}
}
