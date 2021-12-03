package model;

import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class Estado {
	private int idUf;
	private String nome;
	private String uf;

	public Estado() {

	}

	public Estado(int idUf) {
		this.idUf = idUf;
	}
	public Estado(String idUf) {
		this.idUf = Integer.valueOf(idUf);
	}

	public Estado(int idUf, String uf, String nome) {
		this.idUf = idUf;
		this.uf = uf;
		this.nome = nome;
	}

	public int getIdUf() {
		return idUf;
	}

	public void setIdUf(int idUf) {
		this.idUf = idUf;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idUf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return idUf == other.idUf;
	}

	// Sa�das
	@Override
	public String toString() {
		return idUf + "\t" + nome + "\t" + uf + "\n";
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("iduf", this.idUf);
			json.put("uf", this.uf);
			json.put("nome", this.nome);
		} catch (JSONException e) {
			System.out.println("Erro na conversão JSON " + e);
		}
		return json;
	}
}