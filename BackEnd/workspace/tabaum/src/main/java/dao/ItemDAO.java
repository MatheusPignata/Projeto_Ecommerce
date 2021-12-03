package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Item;
import model.Pedido;
import model.Produto;

public class ItemDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<Item> itens;
	private Item item;
	
	// Listar Todos
	public ArrayList<Item> readAll() throws SQLException {
		
		itens = new ArrayList<>();
		String query = "select * from itens;"; // Executa a query no BD
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			item = new Item();
			item.setPedido(new Pedido(rs.getString("id_pedido")));
			item.setProduto(new Produto(rs.getString("id_produto")));
			item.setQuantidade(rs.getInt("quantidade"));
			itens.add(item);
		}

		con.close();
		return itens;
	}
	
	public int create(Item item) throws SQLException {
		String sql = "insert into itens(id_pedido,id_produto,quantidade) value (?,?,?);";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		
		ps.setInt(1, item.getPedido().getIdPedido());
		ps.setInt(2, item.getProduto().getIdProduto());
		ps.setInt(3, item.getQuantidade());
		if(ps.executeUpdate() > 0) {
			rs = ps.getGeneratedKeys();
			rs.next();
			int id = rs.getInt(1);
			return id;	
		}else {
			con.close();
			return 0;
		}
	}

	public void update(Item item) {

	}
	
	//TEM QUE TERMINAR ESSE METODO, NÃO EXISTE TEM ID ITEM ??????????????????????
	public boolean delete(String id) throws SQLException {
		String sql = "delete from itens where id_item = ?;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.valueOf(id));
		if(ps.executeUpdate() > 0) {
			con.close();
			return true;
		}else {
			con.close();
			return false;			
		}
	}
}