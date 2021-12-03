package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Endereco;
import model.Estado;
import model.Cidade;
import model.Cliente;

public class EnderecoDAO {
	// Atributos da classe
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<Endereco> enderecos;
	private Endereco endereco;

	// Listar Todos
	public ArrayList<Endereco> readAll() throws SQLException {
		enderecos = new ArrayList<>();
		String query = "select * from enderecos;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		while (rs.next()) {
			endereco = new Endereco();
			Cliente cli = new Cliente(rs.getString("id_cliente"));
			endereco.setIdCliente(cli);
			Cidade cid = new Cidade(rs.getString("id_cidade"));
			endereco.setIdCidade(cid);
			Estado est = new Estado(rs.getString("id_uf"));
			endereco.setIdUf(est);			
			endereco.setCep(rs.getString("cep"));
			endereco.setLogradouro(rs.getString("logradouro"));
			endereco.setBairro(rs.getString("bairro"));
			enderecos.add(endereco);
		}
		con.close();
		return enderecos;
	}

	public int create(Endereco endereco) throws SQLException {
		String sql = "insert into enderecos(id_cliente,id_cidade,id_uf,cep,logradouro,bairro) value (?,?,?,?,?,?);";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, endereco.getIdCliente());
		ps.setInt(2, endereco.getIdCidade());
		ps.setInt(3, endereco.getIdUf());
		ps.setString(4, endereco.getCep());
		ps.setString(5, endereco.getLogradouro());
		ps.setString(6, endereco.getBairro());
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

	public void update(Cliente cliente) {

	}
	public boolean delete(String id) throws SQLException {
		String sql = "delete from clientes where id_cliente = ?;";
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