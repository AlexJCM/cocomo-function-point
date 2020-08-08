package controller;

import model.PFModel;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Esta clase sera la encargada de realizar todas las operaciones respectivas al
 * calculo de PF y hacer que estos cambios se reflejen en el modelo
 */
//registramos la clase con JSF y le asignamos un nombre, en este caso "pf",
//a traves delcual se vinculara con los componentes de las vistas JSF.  Indica que las 
// instancias de la clase seran creadas y gestionadas por el framework JSF.
@ManagedBean(name = "pf") 
@RequestScoped
public class PFController {        
   
    private PFModel pfModel;

    private int subTotalEE = 0; //suma de todas las Entradas Externas
    private int subTotalSE = 0; //suma de todas las Salidas Externas
    private int subTotalCE = 0; //suma de todas las Consultas Externas
    private int subTotalALI = 0; //suma de todos los Archivos Logicos Internos
    private int subTotalAIE = 0; //uma de todos los Archivos de Interfaz Externos

    // Valores por defecto de cada nivel  comlejiddad de los factores de ponderacion
    private int eeSimple = 0, eePromedio = 0, eeComplejo = 0;
    private int seSimple = 0, sePromedio = 0, seComplejo = 0;
    private int ceSimple = 0, cePromedio = 0, ceComplejo = 0;
    private int aliSimple = 0, aliPromedio = 0, aliComplejo = 0;
    private int aieSimple = 0, aiePromedio = 0, aieComplejo = 0;

    //Valores por defecto de calificacion de preguntas
    private String p1Califiacion="0";
    private String p2Califiacion="0";
    private String p3Califiacion="0";
    private String p4Califiacion="0";
    private String p5Califiacion="0";
    private String p6Califiacion="0";
    private String p7Califiacion="0";
    private String p8Califiacion="0";
    private String p9Califiacion="0";
    private String p10Califiacion="0";
    private String p11Califiacion="0";
    private String p12Califiacion="0";
    private String p13Califiacion="0";
    private String p14Califiacion="0";
    private int totalCalifiaciones=0;
    
    public PFController() {
        pfModel = new PFModel();
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
    
    /**************** PRUEBAS ****************/
    
    public void calcularSubtotalesDominios() {
        subTotalEE = eeSimple * 3 + eePromedio * 4 + eeComplejo * 6;
        subTotalSE = seSimple * 4 + sePromedio * 5 + seComplejo * 7;
        subTotalCE = ceSimple * 3 + cePromedio * 4 + ceComplejo * 6;
        subTotalALI = aliSimple * 7 + aliPromedio * 10 + aliComplejo * 15;
        subTotalAIE = aieSimple * 5 + aiePromedio * 7 + aieComplejo * 10;        
    }
    
    public void calcularSumaCalifiaciones(){
        totalCalifiaciones=Integer.parseInt(p1Califiacion)+Integer.parseInt(p2Califiacion)+Integer.parseInt(p3Califiacion)+Integer.parseInt(p4Califiacion)+Integer.parseInt(p5Califiacion)+Integer.parseInt(p6Califiacion)+Integer.parseInt(p7Califiacion)+Integer.parseInt(p8Califiacion)+Integer.parseInt(p9Califiacion)+Integer.parseInt(p10Califiacion)+Integer.parseInt(p11Califiacion)+Integer.parseInt(p12Califiacion)+Integer.parseInt(p13Califiacion)+Integer.parseInt(p14Califiacion);
    }
    
    public double redondear2Decimales(double adjustment){
        adjustment= Math.round(adjustment * 100);
        adjustment = adjustment / 100;
        return adjustment;
    }
    
    /*****************************************/    
    
    /**
     * Calcula el subtotal de cada uno de los valores de dominio ingresado en la
     * tabla     
     */
    public void calcularSubtotales(){
        
    }
    
    // <editor-fold defaultstate="collapsed" desc="GETTERS y SETTERS de las 15 variables de niveles de complejidad">
    public int getEeSimple() {
        return eeSimple;
    }

    public void setEeSimple(int eeSimple) {
        this.eeSimple = eeSimple;
    }

    public int getEePromedio() {
        return eePromedio;
    }

    public void setEePromedio(int eePromedio) {
        this.eePromedio = eePromedio;
    }

    public int getEeComplejo() {
        return eeComplejo;
    }

    public void setEeComplejo(int eeComplejo) {
        this.eeComplejo = eeComplejo;
    }

    public int getSeSimple() {
        return seSimple;
    }

    public void setSeSimple(int seSimple) {
        this.seSimple = seSimple;
    }

    public int getSePromedio() {
        return sePromedio;
    }

    public void setSePromedio(int sePromedio) {
        this.sePromedio = sePromedio;
    }

    public int getSeComplejo() {
        return seComplejo;
    }

    public void setSeComplejo(int seComplejo) {
        this.seComplejo = seComplejo;
    }

    public int getCeSimple() {
        return ceSimple;
    }

    public void setCeSimple(int ceSimple) {
        this.ceSimple = ceSimple;
    }

    public int getCePromedio() {
        return cePromedio;
    }

    public void setCePromedio(int cePromedio) {
        this.cePromedio = cePromedio;
    }

    public int getCeComplejo() {
        return ceComplejo;
    }

    public void setCeComplejo(int ceComplejo) {
        this.ceComplejo = ceComplejo;
    }

    public int getAliSimple() {
        return aliSimple;
    }

    public void setAliSimple(int aliSimple) {
        this.aliSimple = aliSimple;
    }

    public int getAliPromedio() {
        return aliPromedio;
    }

    public void setAliPromedio(int aliPromedio) {
        this.aliPromedio = aliPromedio;
    }

    public int getAliComplejo() {
        return aliComplejo;
    }

    public void setAliComplejo(int aliComplejo) {
        this.aliComplejo = aliComplejo;
    }

    public int getAieSimple() {
        return aieSimple;
    }

    public void setAieSimple(int aieSimple) {
        this.aieSimple = aieSimple;
    }

    public int getAiePromedio() {
        return aiePromedio;
    }

    public void setAiePromedio(int aiePromedio) {
        this.aiePromedio = aiePromedio;
    }

    public int getAieComplejo() {
        return aieComplejo;
    }

    public void setAieComplejo(int aieComplejo) {
        this.aieComplejo = aieComplejo;
    }
    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="GETTERS Y SETTERS  de los 5 variables de los subtotales de cada valor de dominio">
    public int getSubTotalEE() {
        return subTotalEE;
    }

    public void setSubTotalEE(int subTotalEE) {
        this.subTotalEE = subTotalEE;
    }

    public int getSubTotalSE() {
        return subTotalSE;
    }

    public void setSubTotalSE(int subTotalSE) {
        this.subTotalSE = subTotalSE;
    }

    public int getSubTotalCE() {
        return subTotalCE;
    }

    public void setSubTotalCE(int subTotalCE) {
        this.subTotalCE = subTotalCE;
    }

    public int getSubTotalALI() {
        return subTotalALI;
    }

    public void setSubTotalALI(int subTotalALI) {
        this.subTotalALI = subTotalALI;
    }

    public int getSubTotalAIE() {
        return subTotalAIE;
    }

    public void setSubTotalAIE(int subTotalAIE) {
        this.subTotalAIE = subTotalAIE;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="GETTERS Y SETTERS de las 14 variables de las califiaciones de cada pregunta">
    public String getP1Califiacion() {
        return p1Califiacion;
    }

    public void setP1Califiacion(String p1Califiacion) {
        this.p1Califiacion = p1Califiacion;
    }

    public String getP2Califiacion() {
        return p2Califiacion;
    }

    public void setP2Califiacion(String p2Califiacion) {
        this.p2Califiacion = p2Califiacion;
    }

    public String getP3Califiacion() {
        return p3Califiacion;
    }

    public void setP3Califiacion(String p3Califiacion) {
        this.p3Califiacion = p3Califiacion;
    }

    public String getP4Califiacion() {
        return p4Califiacion;
    }

    public void setP4Califiacion(String p4Califiacion) {
        this.p4Califiacion = p4Califiacion;
    }

    public String getP5Califiacion() {
        return p5Califiacion;
    }

    public void setP5Califiacion(String p5Califiacion) {
        this.p5Califiacion = p5Califiacion;
    }

    public String getP6Califiacion() {
        return p6Califiacion;
    }

    public void setP6Califiacion(String p6Califiacion) {
        this.p6Califiacion = p6Califiacion;
    }

    public String getP7Califiacion() {
        return p7Califiacion;
    }

    public void setP7Califiacion(String p7Califiacion) {
        this.p7Califiacion = p7Califiacion;
    }

    public String getP8Califiacion() {
        return p8Califiacion;
    }

    public void setP8Califiacion(String p8Califiacion) {
        this.p8Califiacion = p8Califiacion;
    }

    public String getP9Califiacion() {
        return p9Califiacion;
    }

    public void setP9Califiacion(String p9Califiacion) {
        this.p9Califiacion = p9Califiacion;
    }

    public String getP10Califiacion() {
        return p10Califiacion;
    }

    public void setP10Califiacion(String p10Califiacion) {
        this.p10Califiacion = p10Califiacion;
    }

    public String getP11Califiacion() {
        return p11Califiacion;
    }

    public void setP11Califiacion(String p11Califiacion) {
        this.p11Califiacion = p11Califiacion;
    }

    public String getP12Califiacion() {
        return p12Califiacion;
    }

    public void setP12Califiacion(String p12Califiacion) {
        this.p12Califiacion = p12Califiacion;
    }

    public String getP13Califiacion() {
        return p13Califiacion;
    }

    public void setP13Califiacion(String p13Califiacion) {
        this.p13Califiacion = p13Califiacion;
    }

    public String getP14Califiacion() {
        return p14Califiacion;
    }
    
    public void setP14Califiacion(String p14Califiacion) {
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
