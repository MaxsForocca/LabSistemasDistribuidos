<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Error</title></head>
<body>
    <h2>Ocurrió un error</h2>
    <%
        if (exception != null) {
            out.println("<p>Excepción: " + exception.getClass().getName() + "</p>");
            out.println("<p>Mensaje: " + exception.getMessage() + "</p>");
        } else {
            java.util.List<String> errores = (java.util.List<String>) request.getAttribute("errores");
            if (errores != null) {
                for (String msg : errores) {
                    out.println("<p>" + msg + "</p>");
                }
            } else {
                out.println("<p>Error desconocido.</p>");
            }
        }
    %>
</body>
</html>
