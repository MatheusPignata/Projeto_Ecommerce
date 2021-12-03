package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Cliente;

public class ClienteDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private ArrayList<Cliente> clientes;
	private Cliente cliente;

	// Listar Todos
	public ArrayList<Cliente> readAll() throws SQLException {
		clientes = new ArrayList<>();
		String query = "select * from clientes;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);
		rs = ps.executeQuery();
		while (rs.next()) {
			cliente = new Cliente();
			cliente.setidCliente(rs.getInt("id_cliente"));
			cliente.setCpf(rs.getString("cpf"));
			cliente.setnomeCompleto(rs.getString("nome_completo"));
			cliente.setEmail(rs.getString("email"));
			cliente.setSenha(rs.getString("senha"));
			clientes.add(cliente);
		}
		con.close();
		return clientes;
	}
	
	//Metodo login
	public Cliente login(String usuario, String senha) throws SQLException {
		cliente = new Cliente();

		String query = "SELECT id_cliente, nome_completo FROM clientes WHERE email = ? AND senha = ?";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(query);

		ps.setString(1, usuario);
		ps.setString(2, senha);

		rs = ps.executeQuery();

		while (rs.next()) {
			cliente.setidCliente(rs.getInt("id_cliente"));
			cliente.setnomeCompleto(rs.getString("nome_completo"));
		}

		return cliente;
	}

	// Metodo create
	public int create(Cliente cliente) throws SQLException {
		String sql = "insert into clientes(cpf,nome_completo,email,senha) value (?,?,?,?);";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, cliente.getCpf());
		ps.setString(2, cliente.getnomeCompleto());
		ps.setString(3, cliente.getEmail());
		ps.setString(4, cliente.getSenha());
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

	// Metodo update
	public int update(Cliente cliente) throws SQLException {
		String sql = "update clientes set cpf = ?, nome_completo = ?, email = ?, senha = ? where id_cliente = ?;";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		// Verifica se o cpf não possui comprimento zero
		if (cliente.getCpf().length() == 0)
			ps.setString(1, null);
		else
		ps.setString(1, cliente.getCpf());
		ps.setString(2, cliente.getnomeCompleto());
		ps.setString(3, cliente.getEmail());
		ps.setString(4, cliente.getSenha());
		ps.setInt(5, cliente.getidCliente());
		return ps.executeUpdate();
	}

	// Metodo delete
	public boolean delete(String id) throws SQLException {
		String sql = "delete from clientes where id_cliente = ?;";
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