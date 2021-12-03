package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONArray;

import controller.CidadeProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cidade;

@WebServlet("/cidades")
public class CidadeHttp extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PrintWriter out;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		try {
			CidadeProcess.carregarDados();
			JSONArray ja = new JSONArray();
			for (Cidade c : CidadeProcess.cidades) {
				ja.put(c.toJSON());
			}
			out.print(ja);
		} catch (SQLException e) {
			System.out.println("Erro ao carregar dados do BD: " + e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		out = resp.getWriter();
		try {
			int idCidade = CidadeProcess.create(body);
			if (idCidade > 0) {
				resp.setStatus(HttpServletResponse.SC_CREATED);
				out.print("{ \"id_cidade\":" + idCidade + "}");
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao conectar com SGBD: " + e);
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		String idCidade = req.getParameter("id_cidade");
		if (idCidade != null) {
			try {
				if (CidadeProcess.delete(idCidade)) {
					resp.setStatus(HttpServletResponse.SC_OK);
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			} catch (SQLException e) {
				System.out.println("Erro ao conectar com SGBD: " + e);
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			out.print("{ \"erro\":\"Necessário o parâmetro 'id' para exclusão\"}");
		}
	}
}