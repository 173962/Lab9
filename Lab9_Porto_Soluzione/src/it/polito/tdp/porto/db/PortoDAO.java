package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.porto.model.Articolo;
import it.polito.tdp.porto.model.Autore;

public class PortoDAO {

	public List<Autore> getAllAutori() {

		List<Autore> creators = new LinkedList<Autore>();
		Connection conn = DBConnect.getConnection();
		String sql = "SELECT * FROM creator";
		PreparedStatement st;
		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Autore tempC = new Autore(res.getInt("id_creator"), res.getString("family_name"),
						res.getString("given_name"));
				creators.add(tempC);
			}
			conn.close();
			res.close();
			return creators;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public List<Articolo> getAllArticoli() {

		List<Articolo> articles = new LinkedList<Articolo>();
		Connection conn = DBConnect.getConnection();
		String sql = "SELECT * FROM article";
		PreparedStatement st;

		try {
			st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Articolo tempA = new Articolo(res.getInt("eprintid"), res.getInt("year"), res.getString("title"));
				articles.add(tempA);
			}
			conn.close();
			res.close();
			return articles;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void fillArticoliInAutori(Autore autore, List<Articolo> articoli) {

		List<Articolo> listaArticoli = new LinkedList<Articolo>();
		Connection conn = DBConnect.getConnection();
		String sql = "SELECT authorship.eprintid FROM article, authorship WHERE authorship.eprintid = article.eprintid AND id_creator=?";
		PreparedStatement st;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, autore.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Articolo articolo = articoli.get(articoli.indexOf(new Articolo(res.getInt("eprintid"))));
				listaArticoli.add(articolo);
			}

			conn.close();
			res.close();
			autore.setArticoli(listaArticoli);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void fillAutoriInArticoli(Articolo articolo, List<Autore> autori) {

		List<Autore> listaAutori = new LinkedList<Autore>();
		Connection conn = DBConnect.getConnection();
		String sql = "SELECT authorship.id_creator FROM creator, authorship WHERE authorship.id_creator = creator.id_creator AND eprintid=?";
		PreparedStatement st;

		try {
			st = conn.prepareStatement(sql);
			st.setInt(1, articolo.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Autore autore = autori.get(autori.indexOf(new Autore(res.getInt("id_creator"))));
				listaAutori.add(autore);
			}

			res.close();
			conn.close();

			articolo.setAutori(listaAutori);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}