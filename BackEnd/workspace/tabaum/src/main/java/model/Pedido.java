package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Pedido {
	// Atributos da classe
	private int idPedido;
	private Cliente idCliente;
	private Date data;
	private Double valor;
	private String status;
	private ArrayList<Item> itens = new ArrayList<Item>();

	// Atributos auxiliares (Formatação)
	private SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");

	public Pedido() {
	}

	public Pedido(String idPedido) {
		this.idPedido = Integer.valueOf(idPedido);
	}
	
	public Pedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public Pedido(int idPedido, int idCliente, Date data, double valor, String status) {
		this.idPedido = idPedido;
		this.idCliente = new Cliente(idCliente);
		this.data = data;
		this.valor = valor;
		this.status = status;
	}

	public Pedido(String idPedido, int idCliente, String data, String valor, String status) {
		this.idPedido = Integer.valueOf(idPedido);
		this.idCliente = new Cliente(idCliente);
		this.status = status;
		try {
			this.data = d.parse(data);
			this.valor = Double.valueOf(valor);
		} catch (ParseException e) {
			System.out.println("Erro converter datas/horas: " + e);
		}
	}

	public void setItem(Item item) {
		itens.add(item);
	}
	
	public ArrayList<Item> getItem(){
		return this.itens;
	}
	
	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
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

	public String getData() {
		return d.format(this.data).toString();
	}

	public void setData(String data) throws ParseException {
		this.data = new SimpleDateFormat("yyyy-MM-dd").parse(data);
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SimpleDateFormat getD() {
		return d;
	}

	public void setD(SimpleDateFormat d) {
		this.d = d;
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
		Pedido other = (Pedido) obj;
		return idPedido == other.idPedido;
	}

	@Override
	public String toString() {
		return idPedido + "\t" + getIdCliente() + "\t" + d.format(data) + "\t" + valor + "\t" + status + "\n";
	}

	public JSONObject toJSON() {
		JSONArray arr = new JSONArray();
		for(Item i : itens) {
			arr.put(i.toJSONSimplificado());
		}
		JSONObject json = new JSONObject();
		try {
			json.put("id_pedido", idPedido);
			if (idCliente != null)
				json.put("cliente", idCliente.getnomeCompleto());
			if (idCliente != null)
				json.put("cliente", idCliente.getidCliente());
			if (data != null)
				json.put("data", d.format(data));
			if (valor != null)
				json.put("valor", valor);
			if (status != null)
				json.put("status", status);
			
			json.put("itens", arr);
		} catch (JSONException e) {
			System.out.println("Erro ao converter JSON: " + e);
		}
		return json;
	}
}