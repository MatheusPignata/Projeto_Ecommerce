package model;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class PedidoORM1 {

	private String nomeCompleto;
	private String idPedido;
	private String valor;
	private String data;
	private String quantidade;
	private String nome;

	public PedidoORM1() {
	}
	
	public PedidoORM1(String id) {
		this.idPedido = id;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idPedido);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoORM1 other = (PedidoORM1) obj;
		return Objects.equals(idPedido, other.idPedido);
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("nome_completo", nomeCompleto);
			json.put("id_pedido", idPedido);
			json.put("valor", valor);
			json.put("data", data);
			json.put("quantidade", quantidade);
			json.put("nome", nome);
		} catch (JSONException e) {
			System.out.println("Erro ao converter JSON: " + e);
		}
		return json;
	}

}
