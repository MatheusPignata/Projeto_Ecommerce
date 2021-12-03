package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Produto;

public class ProdutoDAO {
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<Produto> produtos;
	private Produto produto;
	
	//Metodo listar todos
	public ArrayList<Produto> readAll() throws SQLException {
		produtos = new ArrayList<>(); // Inicia a lista
		String query = "select * from produtos;"; // Executa a query no BD
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();

		// Percorre o ResultSet para preencher a lista
		while (rs.next()) {
			produto = new Produto();
			produto.setIdProduto(rs.getInt("id_produto"));
			produto.setImagem(rs.getString("imagem"));
			produto.setNome(rs.getString("nome"));
			produto.setValor(rs.getDouble("valor"));
			produto.setPromocao(rs.getDouble("promocao"));
			produto.setMarca(rs.getString("marca"));
			produto.setDescricao(rs.getString("descricao"));
			produto.setCategoria(rs.getString("categoria"));
			produtos.add(produto);
		}

		con.close(); // Fecha a conex�o com o BD
		return produtos; // Retorna a lista completa
	}
	
	//Metodo create
	public int create(Produto produto) throws SQLException {
		String sql = "insert into produtos(imagem, nome, valor, promocao, marca, descricao, categoria) value (?,?,?,?,?,?,?);";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, produto.getImagem());
		ps.setString(2, produto.getNome());
		ps.setDouble(3, produto.getValor());
		ps.setDouble(4, produto.getPromocao());
		ps.setString(5, produto.getMarca());
		ps.setString(6, produto.getDescricao());
		ps.setString(7, produto.getCategoria());
		if (ps.executeUpdate() > 0) {
			rs = ps.getGeneratedKeys();
			rs.next();
			con.close();
			return rs.getInt(1);
		} else {
			con.close();
			return 0;
		}
	}

	public int update(Produto produto) throws SQLException {
		String sql = "update produtos set imagem = ?, nome = ?, valor = ? , promocao = ?, marca = ?, descricao = ? , categoria = ? where id_produto = ?;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		// Verifica se o idProduto não possui comprimento zero
		if (produto.getIdProduto() == 0)
			ps.setString(1, null);
		else
			ps.setString(1, produto.getNome());
			ps.setString(2, produto.getImagem());
			ps.setDouble(3, produto.getValor());
			ps.setDouble(4, produto.getPromocao());	
			ps.setString(5, produto.getMarca());
			ps.setString(6, produto.getDescricao());
			ps.setString(7, produto.getCategoria());
			ps.setInt(8, produto.getIdProduto());
			return ps.executeUpdate();
	}

	public boolean delete(String id) throws SQLException {
		String sql = "delete from produtos where id_produto = ?;";
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