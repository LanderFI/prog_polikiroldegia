package api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import modelo.bean.Usuario;
import modelo.dao.ModeloUsuario;

/**
 * Servlet implementation class ApiCreateUsuario
 */
@WebServlet("/ApiCreateUsuario")
public class ApiCreateUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApiCreateUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		datuak jaso
		request.setCharacterEncoding("UTF-8"); //enieak eta ondo irakurtzeko
		String jsonUsuario = request.getParameter("usuario");
		
		
		
		System.out.println(jsonUsuario);
		JSONObject jsonObject = new JSONObject(jsonUsuario);
		
//		komiki objektua sortu
		Usuario usuario = new Usuario();
		usuario.setNombreApellido(jsonObject.getString("nombreApellido"));
		usuario.setCodigo(jsonObject.getString("codigo"));
		usuario.setDni(jsonObject.getString("dni"));
		
		ModeloUsuario mUsuario = new ModeloUsuario();
		
		if(usuario.getCodigo().length()==4 && usuario.getDni().length()==9){//exist falta da
			//balidazioa ok
			mUsuario.insert(usuario);
			
			try {
				mUsuario.getConexion().close();
			} catch (SQLException e) {
				System.out.println("Errorea conexioa ixtean");
				e.printStackTrace();
			}
			
			response.setHeader("Access-Control-Allow-Origin","*"); //jsonp deia denean ez da behar
			response.setCharacterEncoding("UTF-8");
			
		}else {//balidazioa NOK
//			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Balidatzean errorea");
		}
		
	}

}
