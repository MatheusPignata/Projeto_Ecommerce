package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;

import org.json.JSONArray;

import controller.PedidoProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PedidoORM1;

@WebServlet("/pedidosview")
public class PedidoView extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private int indice = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String id = req.getParameter("id_pedido");
		try {
			PedidoProcess.carregarORM1();
			if (id != null) {
				if (PedidoProcess.pedidosORM1.contains(new PedidoORM1(id))) {
					indice = PedidoProcess.pedidosORM1.indexOf(new PedidoORM1(id));
					out.print(PedidoProcess.pedidosORM1.get(indice).toJSON());
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				JSONArray ja = new JSONArray();
				PedidoProcess.pedidosORM1.forEach(p -> ja.put(p.toJSON()));
				// Resposta
				out.print(ja);
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}
	}
}