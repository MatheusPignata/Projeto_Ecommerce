package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.EstadoDAO;
import model.Estado;

public class EstadoProcess {

	public static EstadoDAO ed;
	public static ArrayList<Estado> estados;
	public static Estado estado;
	private static JSONObject jo;

	public static void carregarDados() throws SQLException {
		ed = new EstadoDAO();
		estados = ed.readAll();
	}

	public static int create(String body) throws SQLException {
		ed = new EstadoDAO();
		try {
			jo = new JSONObject(body);
			estado = new Estado();
			estado.setUf(jo.getString("uf"));
			estado.setNome(jo.getString("nome"));
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return ed.create(estado);
	}

	public static boolean delete(String id) throws SQLException {
		ed = new EstadoDAO();
		return ed.delete(id);
	}
}