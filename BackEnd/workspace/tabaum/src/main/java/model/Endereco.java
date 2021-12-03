package model;

import org.json.JSONException;
import org.json.JSONObject;

public class Endereco {
	private Cliente idCliente;
	private Cidade idCidade;
	private Estado idUf;
	private String cep;
	private String logradouro;
	private String bairro;

	public Endereco() {

	}

	public Endereco(Cliente idCliente, Cidade idCidade, Estado idUf, String cep, String logradouro, String bairro) {
		this.idCliente = idCliente;
		this.idCidade = idCidade;
		this.idUf = idUf;
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
	}

	public Endereco(String idCliente, String idCidade, String idUf, String cep, String logradouro, String bairro) {
		this.idCliente = new Cliente(idCliente);
		this.idCidade = new Cidade(idCidade);
		this.idUf = new Estado(idUf);
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
	}

	public Endereco(String idCliente) {

	}
	
	public int getIdCliente() {
		return idCliente.getidCliente();
	}

	public Cliente getCliente() {
		return idCliente;
	}

	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}
	
	public Cidade getCidade() {
		return idCidade;
	}
	
	public int getIdCidade() {
		return idCidade.getIdCidade();
	}

	public void setIdCidade(Cidade idCidade) {
		this.idCidade = idCidade;
	}

	public Estado getEstado() {
		return idUf;
	}
	
	public int getIdUf() {
		return idUf.getIdUf();
	}

	public void setIdUf(Estado idUf) {
		this.idUf = idUf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Override
	public String toString() {
		return getIdCliente() + "\t" + getIdCidade() + "\t" + getIdUf() + "\t" + cep + "\t"
				+ logradouro + "bairro" + "\n";
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("cliente", idCliente.toJSON());
			json.put("cidade", idCidade.toJSON());
			json.put("estado", idUf.toJSON());
			json.put("cep", cep);
			json.put("logradouro", logradouro);
			json.put("bairro", bairro);
		} catch (JSONException e) {
			System.out.println("Erro ao converter JSON: " + e);
		}
		return json;
	}
}