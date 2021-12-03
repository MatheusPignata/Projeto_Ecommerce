package model;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Cidade {
	private int idCidade;
	private String nome;
	private Estado idUf;

	public Cidade() {
	}
	
	public Cidade(int idCidade) {
		this.idCidade = idCidade;
	}
	public Cidade(String idCidade) {
		this.idCidade= Integer.valueOf(idCidade);
	}

	public Cidade(int idCidade, String cidade, int idUf) {
		this.idCidade = idCidade;
		this.nome = cidade;
		this.idUf = new Estado (idUf);
	}
	
	public Cidade(String idCidade, String nome, String idUf) {
		this.idCidade = Integer.valueOf(idCidade);
		this.nome = nome;
		this.idUf=  new Estado(idUf);
		}

	public int getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(int idCidade) {
		this.idCidade = idCidade;
	}
	
	public String getNome() {
		return nome;
	}

	public void setCidade(String nome) {
		this.nome = nome;
	}
	
	public int getIdUf() {
		return idUf.getIdUf();
	}
	
	public Estado getEstado() {
		return idUf;
	}
	public void setIdUf(Estado idUf) {
		this.idUf = idUf;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idCidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		return idCidade == other.idCidade;
	}

	// Sa�das
	@Override
	public String toString() {
		return idCidade + "\t" + nome + "\t" + idUf + "\n";
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("idCidade", this.idCidade);
			json.put("nome", this.nome);
			json.put("idUf", this.idUf);
		} catch (JSONException e) {
			System.out.println("Erro na convers�o JSON " + e);
		}
		return json;
	}
}