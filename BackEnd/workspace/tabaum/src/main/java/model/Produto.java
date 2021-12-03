package model;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Produto {
	private int idProduto;
	private String imagem;
	private String nome;
	private double valor;
	private double promocao;
	private String descricao;
	private String categoria;
	private String marca;

	public Produto() {

	}
	
	public Produto(String idProduto) {
		this.idProduto = Integer.valueOf(idProduto);
	}

	public Produto(String idProduto, double valor) {
		this.idProduto = Integer.valueOf(idProduto);
		this.valor = valor;
	}
	public Produto(int idProduto, String imagem, String nome, double valor, double promocao, String descricao, String categoria,  String marca) {
		this.idProduto = idProduto;
		this.imagem = imagem;
		this.nome = nome;
		this.valor = valor;
		this.promocao = promocao;
		this.descricao = descricao;
		this.categoria = categoria;
		this.categoria = marca;
	}
	
	public Produto(String idProduto, String imagem, String nome, String valor, String promocao, String descricao, String categoria, String marca) {
		this.idProduto = Integer.valueOf(idProduto);
		this.imagem = imagem;
		this.nome = nome;
		this.valor = Double.valueOf(valor);
		this.promocao = Double.valueOf(promocao);
		this.descricao = descricao;
		this.categoria = categoria;
		this.categoria = marca;
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getPromocao() {
		return promocao;
	}

	public void setPromocao(double promocao) {
		this.promocao = promocao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idProduto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return idProduto == other.idProduto;
	}
	
	@Override
	public String toString() {
		return idProduto + "\t" + imagem + "\t" + nome + "\t" + valor + "\t" + promocao + "\t" + descricao + "\t" + categoria + "\t" + marca + "\n";
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("id_produto", idProduto);
			json.put("imagem", imagem);
			json.put("nome", nome);
			json.put("valor", valor);
			json.put("promocao", promocao);
			json.put("descricao", descricao);
			json.put("categoria", categoria);
			json.put("marca", marca);
		} catch (JSONException e) {
			System.out.println("Erro ao converter JSON: " + e);
		}
		return json;
	}
}