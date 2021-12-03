package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.CidadeDAO;
import model.Cidade;
import model.Estado;

public class CidadeProcess {

	public static CidadeDAO cd;
	public static ArrayList<Cidade> cidades;
	public static Cidade cidade;
	private static JSONObject jo;

	public static void carregarDados() throws SQLException {
		cd = new CidadeDAO();
		cidades = cd.readAll();
	}

	// Metodo create
	public static int create(String body) throws SQLException {
		cd = new CidadeDAO();
		try {
			jo = new JSONObject(body);
			cidade = new Cidade();
			cidade.setCidade(jo.getString("nome"));
			cidade.setIdUf(new Estado(jo.getInt("id_uf")));
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return cd.create(cidade);
	}

	// Metodo delete
	public static boolean delete(String id) throws SQLException {
		cd = new CidadeDAO();
		return cd.delete(id);
	}
}