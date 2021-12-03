package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.stream.Collectors;

import org.json.JSONArray;

import controller.PedidoProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Pedido;

@WebServlet("/pedidos")
public class PedidoHttp extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private int indice = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String id = req.getParameter("id_pedido");
		try {
			PedidoProcess.carregarDados();
			if (id != null) {
				if (PedidoProcess.pedidos.contains(new Pedido(id))) {
					indice = PedidoProcess.pedidos.indexOf(new Pedido(id));
					out.print(PedidoProcess.pedidos.get(indice).toJSON());
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				JSONArray ja = new JSONArray();
				PedidoProcess.pedidos.forEach(p -> ja.put(p.toJSON()));
				// Resposta
				out.print(ja);
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		PrintWriter out = resp.getWriter();
		try {
			int id_pedido = PedidoProcess.create(body);
			if (id_pedido > 0) {
				resp.setStatus(HttpServletResponse.SC_CREATED);
				out.print("{ \"id_pedido\":" + id_pedido + "}");
			} else {
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		} catch (SQLException | ParseException e) {
			System.out.println("Erro ao conectar com SGBD: kakaka " + e);
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String idPedido = req.getParameter("id_pedido");
		if (idPedido != null) {
			try {
				if (PedidoProcess.delete(idPedido)) {
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