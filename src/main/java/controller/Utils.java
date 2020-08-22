package controller;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
//****** Libraries for PDF Report *************
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.html2pdf.HtmlConverter;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
//********************************************

/**
 * Esta clase tendrá métodos variados y componentes que tienen un grado de reutilización
 * 
 */
@Named("util")
@SessionScoped
//@ManagedBean(name = "util")// Registramos la clase con JSF y le etiquetamos con un nombre, en este caso "util", a través del cual se vinculará con los componentes de las vistas JSF. Es decir, las páginas JSF mediante dichas etiquetas pueden acceder al ManagedBean (ya sea a sus propiedades o métodos)
//@ViewScoped //Indica que las instancias de la clase serán creadas y gestionadas por el framework JSF.
public class Utils implements Serializable {
    
    //ArrayList con la lista de los puntajes para calificar PF2
    @Getter
    @Setter
    private ArrayList<Integer> listaCalificaciones;
    //ArrayList con la lista de los lenguajes de programación y sus respectivos valores para seleccionar PF3
    @Getter
    @Setter
    private ArrayList<String> listaLenguajes;

    /**

     * Constructor Utils, su principal función es cargar los ArrayList

     */
    public Utils() {
        listaCalificaciones = new ArrayList<>();
        listaCalificaciones.add(0);
        listaCalificaciones.add(1);
        listaCalificaciones.add(2);
        listaCalificaciones.add(3);
        listaCalificaciones.add(4);
        listaCalificaciones.add(5);
        listaLenguajes = new ArrayList<>();
        listaLenguajes.add("ABAP (SAP) *28");
        listaLenguajes.add("ASP *51");
        listaLenguajes.add("Assembler *119");
        listaLenguajes.add("Brio *14");
        listaLenguajes.add("C *97");
        listaLenguajes.add("C++ *50");
        listaLenguajes.add("C# *54");
        listaLenguajes.add("COBOL *61");
        listaLenguajes.add("Cognos Impromptu Scripts *47");
        listaLenguajes.add("Cross System Products (CSP) *20");
        listaLenguajes.add("Cool:Gen/IEF *32");
        listaLenguajes.add("Datastage *71");
        listaLenguajes.add("Excel *209");
        listaLenguajes.add("Focus *43");
        listaLenguajes.add("FoxPro *36");
        listaLenguajes.add("HTML *34");
        listaLenguajes.add("J2EE *46");
        listaLenguajes.add("Java *53");
        listaLenguajes.add("JavaScript *47");
        listaLenguajes.add("JCL *62");
        listaLenguajes.add("LINC II *29");
        listaLenguajes.add("Lotus Notes *23");
        listaLenguajes.add("Natural *40");
        listaLenguajes.add(".NET *7");
        listaLenguajes.add("Oracle *37");
        listaLenguajes.add("PACBASE *35");
        listaLenguajes.add("Perl *24");
        listaLenguajes.add("PL/I *64");
        listaLenguajes.add("PL/SQL *37");
        listaLenguajes.add("Powerbuilder *26");
        listaLenguajes.add("REXX *77");
        listaLenguajes.add("Sabretalk *70");
        listaLenguajes.add("SAS *38");
        listaLenguajes.add("Siebel *59");
        listaLenguajes.add("SLOGAN *75");
        listaLenguajes.add("SQL *21");
        listaLenguajes.add("VB.NET *52");
        listaLenguajes.add("Visual Basic *42");
    }

    /**

     * Método para redondear números Double en dos decimales

     * @param adjustment El parámetro adjustment define el número que vamos a redondear
     
     * @return double retorna el número redondeado en dos decimales

     */
    public double redondear2Decimales(double adjustment) {
        adjustment = Math.round(adjustment * 100);
        adjustment = adjustment / 100;
        return adjustment;
    }

    /**

     * Método para dividir un String por '*' y luego convertir su parte decimal a Integer

     * @param LC El parámetro LC es el string completo
     
     * @return int retorna el número convertido

     */
    public int conversionLC(String LC) {
        String[] parts = LC.split("\\*");
        int vLC = Integer.parseInt(parts[1]);
        return vLC;
    }

    //Funcionalidad para generar archivo PDF
//    /**
//     * Método para generar un PDF a partir de una variable hardcoreada
//    
//     * @throws FileNotFoundException
//     * @throws IOException
//     
//     */
//    public void toPDF() throws FileNotFoundException, IOException {     
////        File directory = new File("./");
////        System.out.println(directory.getAbsolutePath());
//        //
//        // String directorioTrabajo = System.getProperty("user.dir");
//        // System.out.println("El directorio de trabajo es " + directorioTrabajo);
//        //
////        Path rutaRelativa = Paths.get("PFController.java");
////        System.out.println("ruta Relativa: " + rutaRelativa);
////        Path rutaAbsoluta = rutaRelativa.toAbsolutePath();
////        System.out.println("ruta Abasoluta: " + rutaAbsoluta);
//    }
//
//
////    public void htmlToPDF() throws FileNotFoundException, IOException {
////        HtmlConverter.convertToPdf(new FileInputStream("../../webapp/index.html"),
////                new FileOutputStream("index-to-pdf.pdf"));
////        System.out.println("PDF v2 Created :v!");
////    }
    /**
     * Método para convertir una página página html en PDF
     * @param costoTotal Variable que tiene el costo total del proyecto
     * @param tiempoTotal Variable que tiene la duración del proyecto 
     * @throws FileNotFoundException Para evitar errores de documentos nulos
     * @throws IOException Exepciones Varias
     */
    public void plantillaParaPDF(double costoTotal, double tiempoTotal) throws FileNotFoundException, IOException {
        String HTML = "<h1>REPORTE DE LA ESTIMACIÓN DE COSTOS</h1>"
                + "<p>A continuación se presenta un resumen de los resultados obtenidos luego de haber seguido <br></br>"
                + "los pasos indicados y llenado los datos dcorrespondientes. <br></br><br></br></p>"
                + "<p>" + "Costo Total Estimado: " + costoTotal + "</b>"
                + "<p>" + "Tiempo Total Estimado: " + tiempoTotal + " <br></br> </b>"
                + "<a href='https://enigmatic-coast-86151.herokuapp.com/faces/index.xhtml'>Clic aquí para volver a la aplicación</a>";
        HtmlConverter.convertToPdf(HTML, new FileOutputStream("my-second-pdf.pdf"));
        System.out.println("PDF created :)!");
    }
}