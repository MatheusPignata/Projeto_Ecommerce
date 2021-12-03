package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ItemDAO;
import model.Item;
import model.Pedido;
import model.Produto;

public class ItemProcess {

	public static ItemDAO id;
	public static ArrayList<Item> itens;
	public static Item item;
	private static JSONObject jo;

	public static void carregarDados() throws SQLException {
		id = new ItemDAO();
		itens = id.readAll();
	}

	// Metodo create
	public static int create(String body) throws SQLException {
			id = new ItemDAO();
			try {
				jo = new JSONObject(body);
				Pedido ped = new Pedido(jo.getString("id_pedido"));
				Produto prod = new Produto(jo.getString("id_produto"));
				item = new Item(ped, prod, Integer.parseInt(jo.getString("quantidade")));
			} catch (JSONException e) {
				System.out.println("Erro ao receber JSON:" + e);
			}
			return id.create(item);
		}

	// Metodo delete
	public static boolean delete(String idItem) throws SQLException {
		id = new ItemDAO();
		return id.delete(idItem);
	}
}