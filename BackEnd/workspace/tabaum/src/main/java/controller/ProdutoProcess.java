package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ProdutoDAO;
import model.Produto;

public class ProdutoProcess {

	public static ProdutoDAO pd;
	public static ArrayList<Produto> produtos;
	public static Produto produto;
	private static JSONObject jo;
	
	//Metodo carregar todos
	public static void listarTodos() throws SQLException {
		pd = new ProdutoDAO();
		produtos = pd.readAll();
	}
	
	//Metodo create
	public static int create(String body) throws SQLException {
		pd = new ProdutoDAO();
		try {
			jo = new JSONObject(body);
			produto = new Produto();
			produto.setImagem(jo.getString("imagem"));
			produto.setNome(jo.getString("nome"));
			produto.setValor(jo.getDouble("valor"));
			produto.setPromocao(jo.getDouble("promocao"));
			produto.setDescricao(jo.getString("descricao"));
			produto.setCategoria(jo.getString("categoria"));
			produto.setMarca(jo.getString("marca"));
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return pd.create(produto);
	}
	
	public static boolean update(String body) throws SQLException {
		pd = new ProdutoDAO();
		try {
			jo = new JSONObject(body);
			produto = new Produto();
			produto.setIdProduto(jo.getInt("id_produto"));
			if (jo.has("nome") && !jo.isNull("nome"))
				produto.setNome(jo.getString("nome"));
			else
				produto.setNome("nome");
			produto.setIdProduto(jo.getInt("id_produto"));
			produto.setImagem(jo.getString("imagem"));
			produto.setNome(jo.getString("nome"));
			produto.setValor(jo.getDouble("valor"));
			produto.setPromocao(jo.getDouble("promocao"));
			produto.setDescricao(jo.getString("descricao"));
			produto.setCategoria(jo.getString("categoria"));
			produto.setMarca(jo.getString("marca"));
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return pd.update(produto) > 0;
	}
	
	//Metodo delete
	public static boolean delete(String id) throws SQLException {
		pd = new ProdutoDAO();
		return pd.delete(id);
	}
}