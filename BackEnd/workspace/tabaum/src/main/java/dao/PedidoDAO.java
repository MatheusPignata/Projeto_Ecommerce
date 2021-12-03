package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import model.Cliente;
import model.Item;
import model.Pedido;
import model.PedidoORM1;
import model.Produto;

public class PedidoDAO {
	// Atributos de conexão, envio e resultado do BD
	private Connection con; // Conexão
	private PreparedStatement ps; // Envio
	private ResultSet rs; // Resultado
	private ArrayList<Pedido> pedidos; // Atributos do Model Lista e �nico
	private Pedido pedido;
	private ArrayList<PedidoORM1> pedidosORM1; 
	private PedidoORM1 pedidoORM1;
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public ArrayList<Pedido> readAll() throws SQLException, ParseException { // Método para listar todos
		pedidos = new ArrayList<>(); // Inicia a lista vazia
		//Cria a query
		String query = "\r\n"
				+"SELECT pedidos.id_pedido, clientes.id_cliente, clientes.nome_completo, produtos.nome, itens.quantidade, produtos.promocao, produtos.valor, pedidos.data, pedidos.valor FROM pedidos \r\n"
				+"INNER JOIN itens ON itens.id_pedido = pedidos.id_pedido\r\n"
				+"INNER JOIN produtos ON itens.id_produto = produtos.id_produto\r\n"
				+"INNER JOIN clientes ON clientes.id_cliente = pedidos.id_cliente";

		// Conectar, executar e retornar os dados
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		// Percorrer o resultado preenchendo a lista Modelo
		int idPedido = 0;
		double total = 0;
		while (rs.next()) {
			if(rs.getInt("id_pedido") != idPedido) { 
				pedido = new Pedido();
				pedido.setIdPedido(rs.getInt("id_pedido"));
				pedido.setData(rs.getString("data"));
				pedido.setValor(rs.getDouble(9));
				Cliente cli = new Cliente();
				cli.setnomeCompleto(rs.getString("nome_completo"));
				cli.setidCliente(rs.getInt("id_cliente"));
				pedido.setIdCliente(cli);
				total = rs.getDouble(9);
				idPedido = rs.getInt("id_pedido");
			}
			
			Item item = new Item();
			item.setPedido(pedido);
			item.setQuantidade(rs.getInt("quantidade"));
			Produto prod = new Produto();
			prod.setPromocao(rs.getDouble("promocao"));
			prod.setValor(rs.getDouble(7));
			prod.setNome(rs.getString("nome"));
			item.setProduto(prod);
			
			pedido.setItem(item);

			double promocao = round(rs.getDouble("promocao"), 2);
			double valor = round(rs.getDouble(7), 2);

			if(promocao > 0) total -= promocao * item.getQuantidade();
			else total -= valor * item.getQuantidade();
			
			total = round(total, 2);
			
			if(total == 0) {
				pedidos.add(pedido);
			}
		}
		con.close(); // Fechar a conexão
		return pedidos;
	}
	
	//METODO BUSCAR E LISTAR PEDIDO VIEW
	public ArrayList<PedidoORM1> readORM1() throws SQLException, ParseException {
		pedidosORM1 = new ArrayList<>(); // Inicia a lista vazia
		String query = "select * from vw_pedidos_1;";  // Criar a query

		// Conectar, executar e retornar os dados
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		
		// Percorrer o resultado preenchendo a lista Modelo
		while (rs.next()) {
			pedidoORM1 = new PedidoORM1();
			pedidoORM1.setIdPedido(rs.getString("id_pedido"));
			pedidoORM1.setNomeCompleto(rs.getString("nome_completo"));
			pedidoORM1.setNome(rs.getString("nome"));
			pedidoORM1.setQuantidade(rs.getString("quantidade"));
			pedidoORM1.setValor(rs.getString("valor"));
			pedidoORM1.setData(rs.getString("data"));
			pedidosORM1.add(pedidoORM1);
		}
		con.close(); // Fechar a conexão
		return pedidosORM1;
	}

	// Metodo create
	public int create(Pedido pedido) throws SQLException {
		String sql = "insert into pedidos(id_cliente,data,valor,status) value (?,?,?,?);";
		int id = 0;
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, pedido.getIdCliente());
		ps.setString (2, pedido.getData());
		ps.setDouble(3, pedido.getValor());
		ps.setString(4, pedido.getStatus());
		if (ps.executeUpdate() > 0) {
			rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

			pedido.setIdPedido(id);
			for (Item i : pedido.getItem()) {

				sql = "insert into itens(id_pedido,id_produto,quantidade) value (?,?,?);";
				ps = con.prepareStatement(sql);
				
				ps.setInt(1, id);
				ps.setInt(2, i.getProduto().getIdProduto());
				ps.setInt(3, i.getQuantidade());
				
				ps.executeUpdate();
				
			}
			
			return id;
		} else {
			con.close();
			return 0;
		}
		
	}

	public void update(Pedido pedido) {

	}

	// Metodo delete
	public boolean delete(String id) throws SQLException {
		String sql = "delete from pedidos where id_pedido = ?;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.valueOf(id));
		if (ps.executeUpdate() > 0) {
			con.close();
			return true;
		} else {
			con.close();
			return false;
		}
	}
}