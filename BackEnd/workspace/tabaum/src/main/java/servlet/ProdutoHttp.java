package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONArray;

import controller.ProdutoProcess;
import model.Produto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produtos")

public class ProdutoHttp extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	private int indice = 0;
	private JSONArray ja;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		// READ caso seja enviado um 'id_produto' pesquisa o mesmo senão mostra todos
		String id = req.getParameter("id_produto");
		try {
			ProdutoProcess.listarTodos();
			if (id != null) {
				if (ProdutoProcess.produtos.contains(new Produto(id))) {
					indice = ProdutoProcess.produtos.indexOf(new Produto(id));
					out.print(ProdutoProcess.produtos.get(indice).toJSON());
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				ja = new JSONArray();
				for (Produto p : ProdutoProcess.produtos) {
					ja.put(p.toJSON());
				}
				out.print(ja);
			}
		} catch (SQLException e) {
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			out.print("{ \"erro\":\"Erro ao carregar dados do BD: " + e + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		PrintWriter out = resp.getWriter();
		try {
			int idProduto = ProdutoProcess.create(body);
			if (idProduto > 0) {
				resp.setStatus(HttpServletResponse.SC_CREATED);
				out.print("{ \"id_produto\":" + idProduto + "}");
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao conectar com SGBD: " + e);
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().readLine();
		out = resp.getWriter(); 
		if (body != null) { 
			req.getReader().reset();
			body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			try {
				if (ProdutoProcess.update(body)) {
					resp.setStatus(HttpServletResponse.SC_GONE);
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				}
			} catch (SQLException e) {
				out.print("{ \"erro\":\"Erro ao conectar ao SGBD: " + e + "\"}");
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		} else {
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			out.print("preencha corretamente");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		String idProduto = req.getParameter("id_produto");
		if (idProduto != null) {
			try {
				if (ProdutoProcess.delete(idProduto)) {
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
