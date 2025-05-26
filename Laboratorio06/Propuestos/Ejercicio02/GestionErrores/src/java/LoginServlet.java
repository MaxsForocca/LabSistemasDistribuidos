import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        System.out.println("usuario recibido: [" + usuario + "]");
        System.out.println("clave recibida: [" + clave + "]");

        try {
            if ("admin".equals(usuario) && "1234".equals(clave)) {
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("bienvenido.jsp").forward(request, response);
        }

         else {
                List<String> errores = new ArrayList<>();
                errores.add("Usuario o contraseña incorrectos.");
                request.setAttribute("errores", errores);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("javax.servlet.error.exception", e);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
