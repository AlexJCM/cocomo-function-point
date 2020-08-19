package topdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.html2pdf.HtmlConverter;

/**
 * Esta clase tendrá los métodos para generación del PDF
 */
public class ToPDF {

    /**
     * Método devuelve un String HTML
     */
    public static final String HTML = "<h1>Hello</h1>"
            + "<p>This was created using iText</p>"
            + "<a href='hmkcode.com'>hmkcode.com</a>";

    /**
     * Constructor
     * 
     * @param args String
     * @throws FileNotFoundException Para evitar errores de documentos nulos
     * @throws IOException Exepciones Varias
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        HtmlConverter.convertToPdf(HTML, new FileOutputStream("string-to-pdf.pdf"));

        System.out.println("PDF Created :)!");
    }
}
