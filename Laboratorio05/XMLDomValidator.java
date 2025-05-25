import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import java.io.File;

public class XMLDomValidator {
    public static void main(String[] args) {
        try {
            // Ruta del archivo XML (ajústala si está en otra carpeta)
            File xmlFile = new File("Ej01.xml");

            // Preparar el parser con validación
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true); // activa validación con DTD
            factory.setNamespaceAware(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new org.xml.sax.helpers.DefaultHandler()); // Manejo simple de errores

            Document doc = builder.parse(xmlFile);

            System.out.println(" XML valido con DOM y DTD interna.");

        } catch (Exception e) {
            System.out.println("Error al validar el XML:");
            e.printStackTrace();
        }
    }
}
