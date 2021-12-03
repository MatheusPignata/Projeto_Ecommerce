package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONArray;

import controller.EstadoProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Estado;

@WebServlet("/estados")
public class EstadoHttp extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PrintWriter out;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		try {
			EstadoProcess.carregarDados();
			JSONArray ja = new JSONArray();
			for (Estado c : EstadoProcess.estados) {
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
			int idEstado = EstadoProcess.create(body);
			if (idEstado > 0) {
				resp.setStatus(HttpServletResponse.SC_CREATED);
				out.print("{ \"id_estado\":" + idEstado + "}");
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
		String idEstado = req.getParameter("id_uf");
		if (idEstado != null) {
			try {
				if (EstadoProcess.delete(idEstado)) {
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
			out.print("{ \"erro\":\"Necess�rio o par�metro 'id' para exclus�o\"}");
		}
	}
}