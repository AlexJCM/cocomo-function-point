package controller;

import java.util.ArrayList;
import model.PFModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase sera la encargada de realizar todas las operaciones respectivas al
 * calculo del PF y hacer que estos cambios se reflejen en el modelo.
 * @ManagedBean.- Registramos la clase con JSF y le etiquetamos con un nombre, en este caso "pf", a traves
 * del cual se vinculara con los componentes de las vistas JSF. Es decir las páginas JSF mediante dichas
 * etiquetas pueden accder al ManagedBean (ya sea a sus propiedades o metodos)
 * @RequestScoped.- Indica que las instancias de la clase seran creadas y gestionadas por el framework JSF.
 */
@ManagedBean(name = "pf")
@ViewScoped //@RequestScoped
public class PFController {

    private PFModel pfModel;

    @Getter
    @Setter
    private int subTotalEE = 0; //suma de todas las Entradas Externas
    @Getter
    @Setter
    private int subTotalSE = 0; //suma de todas las Salidas Externas
    @Getter
    @Setter
    private int subTotalCE = 0; //suma de todas las Consultas Externas
    @Getter
    @Setter
    private int subTotalALI = 0; //suma de todos los Archivos Logicos Internos
    @Getter
    @Setter
    private int subTotalAIE = 0; //uma de todos los Archivos de Interfaz Externos

    // Valores por defecto de cada nivel  comlejiddad de los factores de ponderacion
    @Getter
    @Setter
    private int eeSimple = 0, eePromedio = 0, eeComplejo = 0;
    @Getter
    @Setter
    private int seSimple = 0, sePromedio = 0, seComplejo = 0;
    @Getter
    @Setter
    private int ceSimple = 0, cePromedio = 0, ceComplejo = 0;
    @Getter
    @Setter
    private int aliSimple = 0, aliPromedio = 0, aliComplejo = 0;
    @Getter
    @Setter
    private int aieSimple = 0, aiePromedio = 0, aieComplejo = 0;

    //Valores por defecto de calificacion de preguntas
    // TODO: Implmentar las siguientes variablesen de calificacion en un arreglo
    private int p1Califiacion = 0;
    private int p2Califiacion = 0;
    private int p3Califiacion = 0;
    private int p4Califiacion = 0;
    private int p5Califiacion = 0;
    private int p6Califiacion = 0;
    private int p7Califiacion = 0;
    private int p8Califiacion = 0;
    private int p9Califiacion = 0;
    private int p10Califiacion = 0;
    private int p11Califiacion = 0;
    private int p12Califiacion = 0;
    private int p13Califiacion = 0;
    private int p14Califiacion = 0;
    private int totalCalifiaciones = 0;

    @Getter
    @Setter
    private String LC = "0";
    @Getter
    @Setter
    private double valorLC = 0;
    @Getter
    @Setter
    private ArrayList<Integer> listaCalificaciones;
    @Getter
    @Setter
    private ArrayList<String> listaLenguajes;

    public PFController() {
        pfModel = new PFModel();        
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

    //Realizara la multiplicacion y actualizacion del las lineas de codigo con el PF
    public void actualizarLC() {
        actualizarPFNA();
        actualizarPFA();
        this.setValorLC(redondear2Decimales(pfModel.getPFA() * this.conversionLC(this.LC)));
    }

    //Realizara la suma final de todas las calificaciones y ajustar el PF
    public void actualizarPFA() {
        actualizarPFNA();
        calcularSumaCalifiaciones();
        this.setFactorAjuste(Double.valueOf(totalCalifiaciones));
        this.ajustarPuntoDeFuncion();
    }

    //Realizará la suma final de todos los puntos funcion No Ajustados parciales y los reflejará en el modelo
    public void actualizarPFNA() {
        calcularSubtotalesDominios();
        pfModel.setPFNA(subTotalEE + subTotalSE + subTotalCE + subTotalALI + subTotalAIE);
        System.out.println("************** pfModel.getPFNA():" + pfModel.getPFNA() + " ***************");
    }

    //Realizará el ajuste de los PF segun la ecuación predefinidae
    public void ajustarPuntoDeFuncion() {
        pfModel.setPFA(redondear2Decimales(pfModel.getPFNA() * pfModel.getFactorDeAjuste()));
    }

    //metodos a ser llamados desde la vista
    public double getFactorAjuste() {
        return pfModel.getFactorDeAjuste();
    }

    public void setFactorAjuste(double aux) {
        pfModel.setFactorDeAjuste(redondear2Decimales(aux * 0.01 + 0.65));
    }

    public double getPFA() {
        return pfModel.getPFA();
    }

    public int getPFNA() {
        return pfModel.getPFNA();
    }

    /**
     * ************** Calcular subtotales de Dominios  ***************
     */
    public void calcularSubtotalesDominios() {
        subTotalEE = eeSimple * 3 + eePromedio * 4 + eeComplejo * 6;
        subTotalSE = seSimple * 4 + sePromedio * 5 + seComplejo * 7;
        subTotalCE = ceSimple * 3 + cePromedio * 4 + ceComplejo * 6;
        subTotalALI = aliSimple * 7 + aliPromedio * 10 + aliComplejo * 15;
        subTotalAIE = aieSimple * 5 + aiePromedio * 7 + aieComplejo * 10;
    }

    //Suma de las calificaciones 
    public void calcularSumaCalifiaciones() {
        totalCalifiaciones = p1Califiacion + p2Califiacion + p3Califiacion + p4Califiacion + p5Califiacion + p6Califiacion + p7Califiacion + p8Califiacion + p9Califiacion + p10Califiacion + p11Califiacion + p12Califiacion + p13Califiacion + p14Califiacion;
    }

    //Redondear a 2 decimales en double
    public double redondear2Decimales(double adjustment) {
        adjustment = Math.round(adjustment * 100);
        adjustment = adjustment / 100;
        return adjustment;
    }

    //Conversion de un String x*12 a un numero en double
    public double conversionLC(String LC) {
        String[] parts = LC.split("\\*");
        double vLC = Double.valueOf(parts[1]);
        return vLC;
    }

    // <editor-fold defaultstate="collapsed" desc="GETTERS Y SETTERS de las 14 variables de las califiaciones de cada pregunta">
    public int getP1Califiacion() {
        return p1Califiacion;
    }

    public void setP1Califiacion(int p1Califiacion) {
        this.p1Califiacion = p1Califiacion;
    }

    public int getP2Califiacion() {
        return p2Califiacion;
    }

    public void setP2Califiacion(int p2Califiacion) {
        this.p2Califiacion = p2Califiacion;
    }

    public int getP3Califiacion() {
        return p3Califiacion;
    }

    public void setP3Califiacion(int p3Califiacion) {
        this.p3Califiacion = p3Califiacion;
    }

    public int getP4Califiacion() {
        return p4Califiacion;
    }

    public void setP4Califiacion(int p4Califiacion) {
        this.p4Califiacion = p4Califiacion;
    }

    public int getP5Califiacion() {
        return p5Califiacion;
    }

    public void setP5Califiacion(int p5Califiacion) {
        this.p5Califiacion = p5Califiacion;
    }

    public int getP6Califiacion() {
        return p6Califiacion;
    }

    public void setP6Califiacion(int p6Califiacion) {
        this.p6Califiacion = p6Califiacion;
    }

    public int getP7Califiacion() {
        return p7Califiacion;
    }

    public void setP7Califiacion(int p7Califiacion) {
        this.p7Califiacion = p7Califiacion;
    }

    public int getP8Califiacion() {
        return p8Califiacion;
    }

    public void setP8Califiacion(int p8Califiacion) {
        this.p8Califiacion = p8Califiacion;
    }

    public int getP9Califiacion() {
        return p9Califiacion;
    }

    public void setP9Califiacion(int p9Califiacion) {
        this.p9Califiacion = p9Califiacion;
    }

    public int getP10Califiacion() {
        return p10Califiacion;
    }

    public void setP10Califiacion(int p10Califiacion) {
        this.p10Califiacion = p10Califiacion;
    }

    public int getP11Califiacion() {
        return p11Califiacion;
    }

    public void setP11Califiacion(int p11Califiacion) {
        this.p11Califiacion = p11Califiacion;
    }

    public int getP12Califiacion() {
        return p12Califiacion;
    }

    public void setP12Califiacion(int p12Califiacion) {
        this.p12Califiacion = p12Califiacion;
    }

    public int getP13Califiacion() {
        return p13Califiacion;
    }

    public void setP13Califiacion(int p13Califiacion) {
        this.p13Califiacion = p13Califiacion;
    }

    public int getP14Califiacion() {
        return p14Califiacion;
    }

    public void setP14Califiacion(int p14Califiacion) {
        this.p14Califiacion = p14Califiacion;
    }

    public int getTotalCalifiaciones() {
        return totalCalifiaciones;
    }

    public void setTotalCalifiaciones(int totalCalifiaciones) {
        this.totalCalifiaciones = totalCalifiaciones;
    }

    // </editor-fold>
    
}