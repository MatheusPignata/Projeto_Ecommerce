package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import dao.EnderecoDAO;
import model.Endereco;

public class EnderecoProcess {

	public static EnderecoDAO ed;
	public static ArrayList<Endereco> enderecos;
	public static Endereco endereco;
	private static JSONObject jo;

	public static void carregarDados() throws SQLException {
		ed = new EnderecoDAO();
		enderecos = ed.readAll();
	}
	public static int create(String body) throws SQLException {
		ed = new EnderecoDAO();
		endereco = new Endereco();
		try {
			jo = new JSONObject(body);
			
			String idCli = jo.getString("id_cliente");
			String idCid = jo.getString("id_cidade");
			String idUf = jo.getString("id_uf");
			String cep = jo.getString("cep");
			String logradouro = jo.getString("logradouro");
			String bairro = jo.getString("bairro");
			
			endereco = new Endereco(idCli, idCid, idUf, cep, logradouro, bairro);
			
			/*Cliente cli = new Cliente(jo.getString("id_cliente"));
			endereco.setIdCliente(cli);
			Endereco end = new Endereco(jo.getString("id_endereco"));
			endereco.setIdEndereco(end);
			endereco.setIdUf(jo.getString("id_uf"));
			endereco.setCep(jo.getString("cep"));
			endereco.set(jo.getString("logradouro"));*/
			
				
		} catch (JSONException e) {
			System.out.println("Erro ao receber JSON:" + e);
		}
		return ed.create(endereco);
	}

	public static boolean delete(String id) throws SQLException {
		ed = new EnderecoDAO();
		return ed.delete(id);
	}
}