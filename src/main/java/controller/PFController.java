package controller;

import model.PFModel;
import controller.Utils;
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

    //Modelo y Utilidades
    private PFModel pfModel;
    private Utils util;

    public PFController() {
        pfModel = new PFModel();    
        util = new Utils();
    }
    
    public double getFactorAjuste() {
        return pfModel.getFactorDeAjuste();
    }

    public void setFactorAjuste(double aux) {
        pfModel.setFactorDeAjuste(util.redondear2Decimales(aux * 0.01 + 0.65));
    }

    public double getPFA() {
        return pfModel.getPFA();
    }

    public int getPFNA() {
        return pfModel.getPFNA();
    }
    
// PAS0 1
    // Valores por defecto de cada nivel comlejiddad de los factores de ponderacion
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
    
    //Realizará la suma final de todos los puntos funcion No Ajustados parciales y los reflejará en el modelo
    public void actualizarPFNA() {
        calcularSubtotalesDominios();
        pfModel.setPFNA(subTotalEE + subTotalSE + subTotalCE + subTotalALI + subTotalAIE);
    }
    
    //Calcular subtotales de Dominios
    public void calcularSubtotalesDominios() {
        subTotalEE = eeSimple * 3 + eePromedio * 4 + eeComplejo * 6;
        subTotalSE = seSimple * 4 + sePromedio * 5 + seComplejo * 7;
        subTotalCE = ceSimple * 3 + cePromedio * 4 + ceComplejo * 6;
        subTotalALI = aliSimple * 7 + aliPromedio * 10 + aliComplejo * 15;
        subTotalAIE = aieSimple * 5 + aiePromedio * 7 + aieComplejo * 10;
    }
    
// PAS0 2
    //Valores por defecto de calificacion de preguntas
    @Getter
    @Setter
    private int p1Califiacion = 0;
    @Getter
    @Setter
    private int p2Califiacion = 0;
    @Getter
    @Setter
    private int p3Califiacion = 0;
    @Getter
    @Setter
    private int p4Califiacion = 0;
    @Getter
    @Setter
    private int p5Califiacion = 0;
    @Getter
    @Setter
    private int p6Califiacion = 0;
    @Getter
    @Setter
    private int p7Califiacion = 0;
    @Getter
    @Setter
    private int p8Califiacion = 0;
    @Getter
    @Setter
    private int p9Califiacion = 0;
    @Getter
    @Setter
    private int p10Califiacion = 0;
    @Getter
    @Setter
    private int p11Califiacion = 0;
    @Getter
    @Setter
    private int p12Califiacion = 0;
    @Getter
    @Setter
    private int p13Califiacion = 0;
    @Getter
    @Setter
    private int p14Califiacion = 0;
    @Getter
    @Setter
    private int totalCalifiaciones = 0;
    
    //Suma de las calificaciones 
    public void calcularSumaCalifiaciones() {
        totalCalifiaciones = p1Califiacion + p2Califiacion + p3Califiacion + p4Califiacion + p5Califiacion + p6Califiacion + p7Califiacion + p8Califiacion + p9Califiacion + p10Califiacion + p11Califiacion + p12Califiacion + p13Califiacion + p14Califiacion;
    }
    
    //Realizará el ajuste de los PF segun la ecuación predefinidae
    public void ajustarPuntoDeFuncion() {
        pfModel.setPFA(util.redondear2Decimales(pfModel.getPFNA() * pfModel.getFactorDeAjuste()));
    }
    
    //Realizara la suma final de todas las calificaciones y ajustar el PF
    public void actualizarPFA() {
        actualizarPFNA();
        calcularSumaCalifiaciones();
        setFactorAjuste(Double.valueOf(totalCalifiaciones));
        ajustarPuntoDeFuncion();
    }

// PAS0 3    
    // Variables para calcular LOC
    @Getter
    @Setter
    private String LC = "0";
     @Getter
    @Setter
    private Integer LCM = 0;
    @Getter
    @Setter
    private double sLoC = 0.0;
    @Getter
    @Setter
    private double kLoC = 0.0;
    @Getter
    @Setter
    private double esf = 0.0;
    @Getter
    @Setter
    private double dur = 0.0;
    @Getter
    @Setter
    private Integer ch = 0;
    
    //Realizara la multiplicacion y actualizacion del las lineas de codigo con el PF
    public void actualizarLC() {
        actualizarPFNA();
        actualizarPFA();
        sLoC=util.redondear2Decimales(pfModel.getPFA() * util.conversionLC(LC));
    }
      //Realizara la multiplicacion y actualizacion del las lineas de codigo que se ingreso de manera manual con el PF
        public void actualizarLCManual() {
        actualizarPFNA();
        actualizarPFA();
        sLoC=util.redondear2Decimales(pfModel.getPFA() * LCM);
    }
         //calculamos el esfuerzo, duracion, cantidad personas
        public void generarResult() {
        slocTOkloc();
        esf=util.redondear2Decimales(2.94 * Math.pow(kLoC, feB) * fm );
         double expDur = 0.28+0.002*sumFE;
        dur= util.redondear2Decimales(3.67 * Math.pow(esf, expDur) );
        ch = (int) Math.round(util.redondear2Decimales(esf/dur));
        
        
    }
                 
    
// PAS0 4 modelo basico    
    //Variables para calcular esfuerzo y duracion
    @Getter
    @Setter
    private double spOab = 2.4,spObb = 1.05,spOcb = 2.5,spOdb = 0.38;
    @Getter
    @Setter
    private double spSab = 3.0,spSbb = 1.12,spScb = 2.5,spSdb = 0.35;
    @Getter
    @Setter
    private double spEab = 3.6,spEbb = 1.20,spEcb = 2.5,spEdb = 0.32;
    @Getter
    @Setter
    private int SP = 0;
    @Getter
    @Setter
    private double effort = 0.0;
    @Getter
    @Setter
    private double duration = 0.0;
    
    //Conversion de SLOC a KLOC
    public void slocTOkloc(){
        kLoC=(sLoC/1000);
    }
    
    //Calcula el esfuerzo y la duracion del proyecto de software
    public void calcularED(){
        switch(SP){
            case 0:
                effort=(util.redondear2Decimales(spOab*Math.pow(kLoC, spObb)));
                duration=(util.redondear2Decimales(spOcb*Math.pow(effort, spOdb)));
                break;
            case 1:
                effort=(util.redondear2Decimales(spSab*Math.pow(kLoC, spSbb)));
                duration=(util.redondear2Decimales(spScb*Math.pow(effort, spSdb)));
                break;
            case 2:
                effort=(util.redondear2Decimales(spEab*Math.pow(kLoC, spEbb)));
                duration=(util.redondear2Decimales(spEcb*Math.pow(effort, spEdb)));
                break;
        }
    }
    
    //Realizara la multiplicacion y actualizacion del las lineas de codigo con el PF
    public void actualizarSP() {
        actualizarPFNA();
        actualizarPFA();
        actualizarLC();
        slocTOkloc();
        calcularED();
    }
    
// PAS0 4 modelo completo
    @Getter
    @Setter
    private double prec = 0.0;
    @Getter
    @Setter
    private double flex = 0.0;
    @Getter
    @Setter
    private double resl = 0.0;
    @Getter
    @Setter
    private double team = 0.0;
    @Getter
    @Setter
    private double pmat = 0.0;
    @Getter
    @Setter
    private double sumFE = 0.0;
    
    //Suma de las calificaciones Factores de escala
    public void sumaCalifiacionesFE() {
       sumFE=(util.redondear2Decimales(prec+flex+resl+team+pmat));
    }
    
    //Realizara el calculo de el factor de escala 
    public void factorEscala(){
        feB=(0.91+0.01*sumFE);
    }
    
    //Realizara la multiplicacion y actualizacion del factor de escala 5
    public void actualizarFE5() {
        actualizarPFNA();
        actualizarPFA();
        actualizarLC();
        sumaCalifiacionesFE();
        factorEscala();
    }

// PAS0 5 
    @Getter
    @Setter
    private double feB = 0.0;
    @Getter
    @Setter
    private double rely = 1.0;
    @Getter
    @Setter
    private double data = 1.0;
    @Getter
    @Setter
    private double cplx = 1.0;
    @Getter
    @Setter
    private double docu = 1.0;
    @Getter
    @Setter
    private double ruse = 1.0;
    @Getter
    @Setter
    private double time = 1.0;
    @Getter
    @Setter
    private double stor = 1.0;
    @Getter
    @Setter
    private double virt = 1.0;
    @Getter
    @Setter
    private double turn = 1.0;
    @Getter
    @Setter
    private double acap = 1.0;
    @Getter
    @Setter
    private double aexp = 1.0;
    @Getter
    @Setter
    private double pcap = 1.0;
    @Getter
    @Setter
    private double vexp = 1.0;
    @Getter
    @Setter
    private double lexp = 1.0;
    @Getter
    @Setter
    private double modp = 1.0;
    @Getter
    @Setter
    private double tool = 1.0;
    @Getter
    @Setter
    private double sced = 1.0;
    @Getter
    @Setter
    private double fm = 1.0;

     //multiplicacion de las calificaciones de factores de esfuerzo compuesto
    public void factorMultiplicativo() {
       fm=(util.redondear2Decimales(rely*data*cplx*docu*ruse*time*stor*virt*turn*acap*aexp*pcap*vexp*lexp*modp*tool*sced));
    }
    
    //Realizara la multiplicacion y actualizacion del las lineas de codigo con el PF
    public void actualizarFEC() {
        actualizarFE5();
        actualizarPFNA();
        actualizarPFA();
        actualizarLC();
        factorMultiplicativo();
    }
}