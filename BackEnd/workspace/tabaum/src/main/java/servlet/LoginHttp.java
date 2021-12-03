package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import controller.ClienteProcess;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cliente;

@WebServlet("/login")
public class LoginHttp extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		
		String usuario = req.getParameter("usuario");
		String senha = req.getParameter("senha");
		
		try {
			Cliente cli =  ClienteProcess.login(usuario, senha);
			pw.write(cli.toJSON().toString());
		} catch (SQLException e) {
			pw.write(e.toString());
		}		
	}
	
}
