package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.List;

public class Autore implements Comparable {

	private int id;
	private String cognome;
	private String nome;
	private List<Articolo> articoli;

	public Autore(int id, String cognome, String nome) {
		this.id = id;
		this.cognome = cognome;
		this.nome = nome;
	}

	public Autore(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Articolo> getArticoli() {
		if (articoli == null)
			articoli = new ArrayList<Articolo>();
		return articoli;
	}

	public void setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autore other = (Autore) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int compareTo(Autore altroAutore) {
		return this.cognome.compareTo(altroAutore.cognome);
	}

	@Override
	public String toString() {
		return cognome;
	}

	@Override
	public int compareTo(Object obj) {
		if (this == obj)
			return 0;
		if (obj == null)
			return -1;
		if (getClass() != obj.getClass())
			return -1;

		Autore other = (Autore) obj;
		return (cognome.compareTo(other.cognome));
	}
}
