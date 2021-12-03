package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ClienteDAO;
import model.Cliente;

public class ClienteProcess {

	public static ClienteDAO cd;
	public static ArrayList<Cliente> clientes;
	public static Cliente cliente;
	private static JSONObject jo;

	public static void listarTodos() throws SQLException {
		cd = new ClienteDAO();
		clientes = cd.readAll();
	}
	
	public static Cliente login(String usuario, String senha) throws SQLException {
		cd = new ClienteDAO();
		Cliente cli = cd.login(usuario, senha);
		return cli;
	}

	public static int create(String body) throws SQLException {
		cd = new ClienteDAO();
		try {
			jo = new JSONObject(body);
			cliente = new Cliente();
			cliente.setCpf(jo.getString("cpf"));
			cliente.setnomeCompleto(jo.getString("nome_completo"));
			cliente.setEmail(jo.getString("email"));
			cliente.setSenha(jo.getString("senha"));
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return cd.create(cliente);
	}
	
	public static boolean update(String body) throws SQLException {
		cd = new ClienteDAO();
		try {
			jo = new JSONObject(body);
			cliente = new Cliente();
			cliente.setidCliente(jo.getInt("id_cliente"));
			//Verifica se possui CPF E se não é Nulo
			if (jo.has("cpf") && !jo.isNull("cpf"))
				cliente.setCpf(jo.getString("cpf"));
			else //Caso seja nulo configura o modelo com comprimenro zero ""
				cliente.setCpf("");
			cliente.setidCliente(jo.getInt("id_cliente"));
			cliente.setCpf(jo.getString("cpf"));
			cliente.setnomeCompleto(jo.getString("nome_completo"));
			cliente.setEmail(jo.getString("email"));
			cliente.setSenha(jo.getString("senha"));
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return cd.update(cliente) > 0;
	}

	public static boolean delete(String id) throws SQLException {
		cd = new ClienteDAO();
		return cd.delete(id);
	}
}