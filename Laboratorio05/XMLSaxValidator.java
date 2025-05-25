import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.helpers.DefaultHandler;
import java.io.File;

public class XMLSaxValidator {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("Ej01.xml");

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);

            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlFile, new DefaultHandler());

            System.out.println("XML valido con SAX y DTD interna.");
        } catch (Exception e) {
            System.out.println("Error al validar el XML:");
            e.printStackTrace();
        }
    }
}
