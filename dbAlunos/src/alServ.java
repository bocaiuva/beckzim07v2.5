import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class alServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public alServ() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String incAl = request.getParameter("incAluno");
		String altAl = request.getParameter("altAluno");
		String oNameAl = request.getParameter("oNameAl");
		String killAl = request.getParameter("killAluno");

		String incMateria = request.getParameter("incMateria");
		String altMateria = request.getParameter("altMateria");
		String oNameMateria = request.getParameter("oNameMatri");
		String killMateria = request.getParameter("killMateria");

		String incMatricula = request.getParameter("incMatricula");
		String altMatricula = request.getParameter("altMatricula");
		String oNameMatricula = request.getParameter("oNameMater");
		String killMatricula = request.getParameter("killMatricula");

		Connection connection = null;
		PreparedStatement ps = null;

		try {
			connection = getConnection();

		} catch (SQLException e) {
			exibe(response, "Erro de SQL: " + e.getMessage());
		} catch (NamingException e) {
			exibe(response, "Erro na obtenção do contexto inicial: " + e.getMessage());
		} finally {
			try {
				if(ps != null) ps.close();
				if(connection != null) connection.close();
			} catch (Exception e) {
				exibe(response, e.getMessage());
			}
		}
		try {
			if (incAl != null) {
				inclusaoAluno(connection, ps, incAl);
			} else if (altAl != null) {
				alteracaoAluno(connection, ps, oNameAl, altAl);
			} else if (killAl != null) {
				killAluno(connection, ps, killAl);
			} else if (incMateria != null) {
				inclusaoMateria(connection, ps, incMateria);
			} else if (altMateria != null) {
				alteracaoMateria(connection, ps, oNameMateria, altMateria);
			} else if (killMateria != null) {
				killAluno(connection, ps, killMateria);
			} else if (incMatricula != null) {
				inclusaoMatricula(connection, ps, incMatricula);
			} else if (altMatricula != null) {
				alteracaoMatricula(connection, ps, oNameMatricula, altMatricula);
			} else if (killMatricula != null) {
				killAluno(connection, ps, killMatricula);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() throws NamingException,  SQLException {

		InitialContext ctx = new InitialContext();

		DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/xdb");
		if(ds == null)
			System.out.println("oop");

		Connection connection = ds.getConnection();
		if(connection == null)
			System.out.println("oop as well");

		return connection;	
	}

	public void inclusaoAluno(Connection connection,PreparedStatement ps, String aName) throws SQLException {
		ps = connection.prepareStatement("INSERT INTO aluno (name) VALUES (" + aName + ");");
		ps.executeQuery();

		System.out.println("added");
	}

	public void alteracaoAluno(Connection connection, PreparedStatement ps, String oName, String nName) throws SQLException {
		ps = connection.prepareStatement("UPDATE aluno SET nome = " + nName + "WHERE " + oName + " LIKE aluno.nome;");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("updated");
		}
	}

	public void killAluno(Connection connection, PreparedStatement ps, String nome) throws SQLException {
		ps = connection.prepareStatement("DELETE FROM aluno WHERE " + nome + " LIKE matricula.nome;");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("deleted");
		}
	}

	public ArrayList<String> stalkearAluno(Connection connection, PreparedStatement ps) throws SQLException {
		ArrayList<String> retorno = new ArrayList<String>();

		ps = connection.prepareStatement("select * from aluno");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			retorno.add(rs.getString(1) + "/" + rs.getString(2) + "<br>");
		}
		return retorno;
	}

	public void inclusaoMatricula(Connection connection, PreparedStatement ps, String mName) throws SQLException {
		ps = connection.prepareStatement("INSERT INTO matricula (name) VALUES (" + mName + ");");
		ps.executeQuery();

		System.out.println("added");
	}

	public void alteracaoMatricula(Connection connection, PreparedStatement ps, String nName, String oName) throws SQLException{
		ps = connection.prepareStatement("UPDATE aluno SET nome = " + nName + "WHERE " + oName + " LIKE matricula.nome;");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("updated");
		}
	}

	public void killMatricula(Connection connection, PreparedStatement ps, String nome) throws SQLException {
		ps = connection.prepareStatement("DELETE FROM matricula WHERE " + nome + " LIKE matricula.nome;");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("deleted");
		}
	}

	public ArrayList<String> stalkearMatricula(Connection connection, PreparedStatement ps) throws SQLException {
		ArrayList<String> retorno = new ArrayList<String>();

		ps = connection.prepareStatement("select * from matricula");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			retorno.add(rs.getString(1) + "/" + rs.getString(2) + "<br>");
		}
		return retorno;
	}

	public void inclusaoMateria(Connection connection, PreparedStatement ps, String mName) throws SQLException {
		ps = connection.prepareStatement("INSERT INTO materia (name) VALUES (" + mName + ");");
		ps.executeQuery();

		System.out.println("added");
	}

	public void alteracaoMateria(Connection connection, PreparedStatement ps, String nName, String oName) throws SQLException {
		ps = connection.prepareStatement("UPDATE aluno SET nome = " + nName + "WHERE " + oName + " LIKE aluno.nome;");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("updated");
		}
	}

	public void killMateria(Connection connection, PreparedStatement ps, String nome) throws SQLException {
		ps = connection.prepareStatement("DELETE FROM materia WHERE " + nome + "LIKE matricula.nome;");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("deleted");
		}
	}

	public ArrayList<String> stalkearMateria(Connection connection, PreparedStatement ps) throws SQLException {
		ArrayList<String> retorno = new ArrayList<String>();

		ps = connection.prepareStatement("select * from materia");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			retorno.add(rs.getString(1) + "/" + rs.getString(2) + "<br>");
		}
		return retorno;

	}	

	public void exibe(HttpServletResponse response, String mesagi) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html><head><title>");
		out.println("Alunos");
		out.println("</title></head><body>");
		out.println("<h1>Alunos</h1>");
		out.println("<form action=\"potencia\" method=\"post\">");

		out.println("Alterar aluno: <input type=\"text\" name=\"altAluno\">"); 
		out.println("<br>Incluir aluno: <input type=\"text\" name=\"incAluno\">");
		out.println("<br>Antigo nome: <input type=\"text\" name=\"oNameAl\">");
		out.println("<br>Excluir aluno: <input type=\"text\" name=\"killAluno\">");

		out.println("Alterar matricula: <input type=\"text\" name=\"altMatricula\">"); 
		out.println("<br>Incluir matricula: <input type=\"text\" name=\"incMatricula\">");
		out.println("<br>Antigo nome: <input type=\"text\" name=\"oNameMatri\">");
		out.println("<br>Excluir matricula: <input type=\"text\" name=\"killMatricula\">");

		out.println("Alterar materia: <input type=\"text\" name=\"altMateria\">"); 
		out.println("<br>Incluir materia: <input type=\"text\" name=\"incMateria\">");
		out.println("<br>Antigo nome: <input type=\"text\" name=\"oNameMater\">");
		out.println("<br>Excluir materia: <input type=\"text\" name=\"killMateria\">");

		out.println("<br><input type=\"submit\"></form>");
		out.println("<p>" + mesagi + "</p>");
		out.println("</body></html>");
	}
}
