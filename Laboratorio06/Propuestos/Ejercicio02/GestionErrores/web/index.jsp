<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" %>
<html>
<head><title>Ingreso</title></head>
<body>
    <h2>Ingreso de Usuario</h2>
    <form action="LoginServlet" method="post">
        Usuario: <input type="text" name="usuario"><br>
        Contraseña: <input type="password" name="clave"><br>
        <input type="submit" value="Ingresar">
    </form>

</body>
</html>
