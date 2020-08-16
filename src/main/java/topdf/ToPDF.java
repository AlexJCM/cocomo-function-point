package topdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.html2pdf.HtmlConverter;

public class ToPDF {

    public static final String HTML = "<h1>Hello</h1>"
            + "<p>This was created using iText</p>"
            + "<a href='hmkcode.com'>hmkcode.com</a>";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        HtmlConverter.convertToPdf(HTML, new FileOutputStream("string-to-pdf.pdf"));

        System.out.println("PDF Created :)!");
    }
    
}
