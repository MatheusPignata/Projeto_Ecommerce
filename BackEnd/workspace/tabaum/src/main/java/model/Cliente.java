package model;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Cliente {
	//Atributos da classe
	private int idCliente;
	private String cpf;
	private String nomeCompleto;
	private String email;
	private String senha;

	public Cliente() {

	}

	public Cliente(int idCliente, String cpf, String nomeCompleto, String email, String senha) {
		this.idCliente = idCliente;
		this.cpf = cpf;
		this.nomeCompleto = nomeCompleto;
		this.email = email;
		this.senha = senha;
	}

	public Cliente(String idCliente) {
		this.idCliente = Integer.valueOf(idCliente);
	}
	
	public Cliente (int idCliente) {
		this.idCliente = idCliente;
	}

	public int getidCliente() {
		return idCliente;
	}

	public void setidCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getnomeCompleto() {
		return nomeCompleto;
	}

	public void setnomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idCliente);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return idCliente == other.idCliente;
	}

	@Override
	public String toString() {
		return idCliente + "\t" + nomeCompleto + "\t" + cpf + "\t" + email + "\t" + senha + "\n";
	}
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("id_cliente", idCliente);
			json.put("nomeCompleto", nomeCompleto);
			json.put("cpf", cpf);
			json.put("email", email);
			json.put("senha", senha);
		} catch (JSONException e) {
			System.out.println("Erro ao converter JSON: " + e);
		}
		return json;
	}
}