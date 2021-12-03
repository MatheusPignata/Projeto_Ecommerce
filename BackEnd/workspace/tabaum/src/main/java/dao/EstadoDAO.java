package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Estado;

public class EstadoDAO {
	// Atributos B�sicos
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<Estado> estados;
	private Estado estado;

	// Listar todos
	public ArrayList<Estado> readAll() throws SQLException {
		estados = new ArrayList<>();
		String query = "select * from estados";
		con = ConnectionDB.getConnection(); // Obtem conex�o
		ps = con.prepareStatement(query); // Prepara a execu��o da Query
		ResultSet rs = ps.executeQuery(); // Executa e retorna na ResultSet
		while (rs.next()) {
			estado = new Estado();
			estado.setIdUf(rs.getInt("id_uf"));
			estado.setNome(rs.getString("nome"));
			estado.setUf(rs.getString("uf"));
			estados.add(estado);
		}
		con.close(); // Fechar a conex�o
		return estados;
	}
	
	//Metodo create
	public int create(Estado estado) throws SQLException {
		String sql = "insert into estados (uf,nome) value (?,?);";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, estado.getUf());
		ps.setString(2, estado.getNome());
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

	//Metodo delete
	public boolean delete(String id) throws SQLException {
		String sql = "delete from estados where id_uf = ?;";
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