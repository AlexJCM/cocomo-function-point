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

    
    public PFController() {
        pfModel = new PFModel();
    }

    //Realizará la suma final de todos los puntos funcion No Ajustados parciales y los reflejará en el modelo
    public void actualizarPFNA() {
        calcularSubtotalesDominios();
        pfModel.setPFNA(subTotalEE + subTotalSE + subTotalCE + subTotalALI + subTotalAIE);
    }

    //Realizará el ajuste de los PF segun la ecuación predefinidae
    public void ajustarPuntoDeFuncion() {
        pfModel.setPFA(pfModel.getPFNA() * pfModel.getFactorDeAjuste());
    }

    //metodos a ser llamados desde la vista
    public double getFactorAjuste() {
        return pfModel.getFactorDeAjuste();
    }

    public void setFactorAjuste(double aux) {
        double adjustmentFactor = aux * 0.01 + 0.65;
        adjustmentFactor = Math.round(adjustmentFactor * 100);
        System.out.println("adjustmentFactor: " + adjustmentFactor);
        adjustmentFactor = adjustmentFactor / 100;
        System.out.println("adjustmentFactor: " + adjustmentFactor);

        pfModel.setFactorDeAjuste(adjustmentFactor);
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
    
    
}
