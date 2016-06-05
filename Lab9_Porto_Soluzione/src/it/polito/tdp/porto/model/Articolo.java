package it.polito.tdp.porto.model;

import java.util.LinkedList;
import java.util.List;

public class Articolo {

	private int id;
	private int year;
	private String title;
	private List<Autore> autori;
	
	public Articolo(int id, int year, String title) {
		this.id = id;
		this.year = year;
		this.title = title;
	}

	public Articolo(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Autore> getAutori() {
		if (autori == null)
			autori = new LinkedList<>();
		return autori;
	}

	public void setAutori(List<Autore> autori) {
		this.autori = autori;
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
		Articolo other = (Articolo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return title;
	}
	
//	@Override
//	public String toString() {
//		return "Articolo [id=" + id + ", year=" + year + ", title=" + title + ", autori=" + autori + "]";
//	}
	
}
