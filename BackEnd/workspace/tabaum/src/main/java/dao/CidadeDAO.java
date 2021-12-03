package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Cidade;
import model.Estado;

public class CidadeDAO {
	// Atributos B�sicos
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<Cidade> cidades;
	private Cidade cidade;

	// Listar todos
	public ArrayList<Cidade> readAll() throws SQLException {
		cidades = new ArrayList<>();
		String query = "select * from cidades";
		con = ConnectionDB.getConnection(); // Obtem conex�o
		ps = con.prepareStatement(query); // Prepara a execu��o da Query
		ResultSet rs = ps.executeQuery(); // Executa e retorna na ResultSet
		while (rs.next()) {
			cidade = new Cidade();
			cidade.setIdCidade(rs.getInt("id_cidade"));
			cidade.setCidade(rs.getString("nome"));
			cidade.setIdUf(new Estado(rs.getInt("id_uf")));
			cidades.add(cidade);
		}
		con.close(); // Fechar a conex�o
		return cidades;
	}
	
	public int create(Cidade cidade) throws SQLException {
		String sql = "insert into cidades(nome, id_uf) value (?, ?);";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, cidade.getNome());
		ps.setInt(2, cidade.getIdUf());
		if(ps.executeUpdate() > 0) {
			rs = ps.getGeneratedKeys();
			rs.next();
			con.close();
			return rs.getInt(1);	
		}else {
			con.close();
			return 0;
		}
	}
	
	public boolean delete(String id_cidade) throws SQLException {
		String sql = "delete from cidades where id_cidade = ?;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, Integer.valueOf(id_cidade));
		if(ps.executeUpdate() > 0) {
			con.close();
			return true;
		}else {
			con.close();
			return false;			
		}
	}
}