package controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.PedidoDAO;
import model.Cliente;
import model.Item;
import model.Pedido;
import model.PedidoORM1;

public class PedidoProcess {

	public static PedidoDAO pd;
	public static ArrayList<Pedido> pedidos;
	public static ArrayList<PedidoORM1> pedidosORM1;
	public static Pedido pedido;
	private static JSONObject jo;
	private static JSONArray ja;

	public static void carregarDados() throws SQLException, ParseException {
		pd = new PedidoDAO();
		pedidos = pd.readAll();
	}
	
	public static void carregarORM1() throws SQLException, ParseException {
		pd = new PedidoDAO();
		pedidosORM1 = pd.readORM1();
	}

	// Metodo create
	public static int create(String body) throws SQLException, ParseException {
		pd = new PedidoDAO();
		try {
			jo = new JSONObject(body);
			pedido = new Pedido();
			Cliente cli = new Cliente(jo.getInt("id_cliente"));
			pedido.setIdCliente(cli);
			pedido.setData(jo.getString("data"));
			pedido.setValor(jo.getDouble("valor"));
			pedido.setStatus(jo.getString("status"));
			ja = new JSONArray(jo.getString("produtos"));
			for (int i = 0; i < ja.length(); i++) {
				JSONObject obj = ja.getJSONObject(i);
				Item item = new Item(obj.getInt("id_prod"), obj.getInt("qntd")); 
				pedido.setItem(item);
			}
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return pd.create(pedido);
	}

	// Metodo delete
	public static boolean delete(String idPedido) throws SQLException {
		pd = new PedidoDAO();
		return pd.delete(idPedido);
	}
}