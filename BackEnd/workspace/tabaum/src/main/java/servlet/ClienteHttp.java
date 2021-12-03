package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.json.JSONArray;

import controller.ClienteProcess;
import model.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/clientes")
public class ClienteHttp extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PrintWriter out;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		// READ caso seja enviado um 'id_cliente' pesquisa o mesmo senão mostra todos
		String id = req.getParameter("id_cliente");
		try {
			ClienteProcess.listarTodos();
			if (id != null)
				if(ClienteProcess.clientes.contains(new Cliente(id))) {
					int indice = ClienteProcess.clientes.indexOf(new Cliente(id));
					out.print(ClienteProcess.clientes.get(indice).toJSON());
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}else {
					JSONArray ja = new JSONArray();
					for (Cliente c : ClienteProcess.clientes) {
						ja.put(c.toJSON());
					}
					out.print(ja);
				}
		} catch (SQLException e) {
			System.out.println("Erro ao carregar dados do BD: " + e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		out = resp.getWriter();
		try {
			System.out.println(body);
			int idCliente = ClienteProcess.create(body);
			if (idCliente > 0) {
				resp.setStatus(HttpServletResponse.SC_CREATED);
				out.print("{ \"id_cliente\":" + idCliente + "}");
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
		String body = req.getReader().readLine();// Lê apenas a primeira linha do corpo da req.
		out = resp.getWriter(); // Configura out como saída de resp.
		if (body != null) { // Se a primeira linha não for nula
			req.getReader().reset(); // Reseta a leitura das linhas do corpo da req
			// Lê todas as linhas do corpo e converte em uma String
			body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
			try {// Tenta enviar os dados para o controller processar
				if (ClienteProcess.update(body)) {// Se os dados foram registrados no banco
					// Responde com status http 410 alterado.
					resp.setStatus(HttpServletResponse.SC_GONE);
				} else {
					resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
				}
			} catch (SQLException e) {
				out.print("{ \"erro\":\"Erro ao conectar ao SGBD: " + e + "\"}");
				resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			}
		} else {//Se o corpo não foi devidamente preenchido
			//Responde com requisição não aceita erro http 406
			resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			//Envia a mensagem de como deve ser o corpo JSON da requisição
			out.print(
					"{\"id_cliente\":\"1\",\"cpf\":null,\"nome_completo\":\"Algum nome\",\"email\":\"email@email.com\",\"senha\":\"********\"}");
		}
	}
	

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		out = resp.getWriter();
		String idCliente = req.getParameter("id_cliente");
		if (idCliente != null) {
			try {
				if (ClienteProcess.delete(idCliente)) {
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
