package controller;

import model.PFModel;
import controller.Utils;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase será la encargada de realizar todas las operaciones respectivas al cálculo del PF y hacer que estos cambios se reflejen en el modelo.
 * 
 */
@ManagedBean(name = "pf")// Registramos la clase con JSF y le etiquetamos con un nombre, en este caso "pf", a través del cual se vinculará con los componentes de las vistas JSF. Es decir, las páginas JSF mediante dichas etiquetas pueden acceder al ManagedBean (ya sea a sus propiedades o métodos).
@ViewScoped //Indica que las instancias de la clase serán creadas y gestionadas por el framework JSF.
public class PFController {

    private PFModel pfModel;//Instancia de PFModel
    private Utils util;//Instancia de Utils

    /**

     * Constructor PFController, su principal función es crear una instancia del modelo PFModel y de la clase Utils.

     */
    public PFController() {
        pfModel = new PFModel();
        util = new Utils();
    }

// PAS0 01
    // Valores por defecto de cada nivel complejidad de los factores de ponderación.
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
    private int subTotalEE = 0; //Suma de todas las Entradas Externas
    @Getter
    @Setter
    private int subTotalSE = 0; //Suma de todas las Salidas Externas
    @Getter
    @Setter
    private int subTotalCE = 0; //Suma de todas las Consultas Externas
    @Getter
    @Setter
    private int subTotalALI = 0; //Suma de todos los Archivos Lógicos Internos
    @Getter
    @Setter
    private int subTotalAIE = 0; //Suma de todos los Archivos de Interfaz Externos
    
     /**

     * Método para calcular subtotales de Dominios

     */
    public void calcularSubtotalesDominios() {
        subTotalEE = eeSimple * 3 + eePromedio * 4 + eeComplejo * 6;
        subTotalSE = seSimple * 4 + sePromedio * 5 + seComplejo * 7;
        subTotalCE = ceSimple * 3 + cePromedio * 4 + ceComplejo * 6;
        subTotalALI = aliSimple * 7 + aliPromedio * 10 + aliComplejo * 15;
        subTotalAIE = aieSimple * 5 + aiePromedio * 7 + aieComplejo * 10;
    }
    
    /**

     * Método para obtener el valor de los Puntos de Función NO Ajustados
     
     * @return Puntos de Función NO Ajustados

     */
    public int getPFNA() {
        return pfModel.getPFNA();
    }

    /**

     * Método para realizar la suma final de todos los puntos función No Ajustados parciales y los reflejará en el modelo

     */
    public void actualizarPFNA() {
        calcularSubtotalesDominios();
        pfModel.setPFNA(subTotalEE + subTotalSE + subTotalCE + subTotalALI + subTotalAIE);
    }

    
// PAS0 02
    //Valores por defecto de calificación de preguntas para Ajustar los Puntos de Función
    @Getter
    @Setter
    private int p1Califiacion = 0, p2Califiacion = 0, p3Califiacion = 0, p4Califiacion = 0;
    @Getter
    @Setter
    private int p5Califiacion = 0, p6Califiacion = 0, p7Califiacion = 0, p8Califiacion = 0;
    @Getter
    @Setter
    private int p9Califiacion = 0, p10Califiacion = 0, p11Califiacion = 0, p12Califiacion = 0;
    @Getter
    @Setter
    private int p13Califiacion = 0, p14Califiacion = 0;
    
    //Valores por defecto para la suma total de las calificaciones
    @Getter
    @Setter
    private int totalCalifiaciones = 0;

    /**

     * Método para realizar la suma final de todas las calificaciones para Ajuste de los Puntos de Función

     */
    public void calcularSumaCalifiaciones() {
        totalCalifiaciones = p1Califiacion + p2Califiacion + p3Califiacion + p4Califiacion + p5Califiacion + p6Califiacion + p7Califiacion + p8Califiacion + p9Califiacion + p10Califiacion + p11Califiacion + p12Califiacion + p13Califiacion + p14Califiacion;
    }

    /**

     * Método para obtener el Factor de Ajuste del Modelo PFModel
     
     * @return double retorna el valor del Factor de Ajuste

     */
    public double getFactorAjuste() {
        return pfModel.getFactorDeAjuste();
    }
    
    /**

     * Método para calcular el Factor de Ajuste
     
     * @param totalCalifiaciones Valor para calcular el Factor de Ajuste

     */
    public void setFactorAjuste(double totalCalifiaciones) {
        pfModel.setFactorDeAjuste(util.redondear2Decimales(totalCalifiaciones * 0.01 + 0.65));
    }
    
    /**

     * Método para calcular los puntos de función

     */
    public void ajustarPuntoDeFuncion() {
        pfModel.setPFA(util.redondear2Decimales(pfModel.getPFNA() * pfModel.getFactorDeAjuste()));
    }

    /**

     * Método para obtener el valor de los Puntos de Función Ajustados
     
     * @return Puntos de Función Ajustados

     */
    public double getPFA() {
        return pfModel.getPFA();
    }

    /**

     * Método para realizar un conjunto de llamados a otros métodos:
     *  Calculara la suma de todas las calificaciones
     *  Calculara el factor de ajuste
     *  Ajustara los Puntos de Función

     */
    public void actualizarPFA() {
        calcularSumaCalifiaciones();
        setFactorAjuste(Double.valueOf(totalCalifiaciones));
        ajustarPuntoDeFuncion();
    }

// PAS0 03    
    @Getter
    @Setter
    private String strLC = "Java *53";//Líneas de código en formato String para guardar el valor obtenido del Select
    @Getter
    @Setter
    private int LC = 0;//Valor de las seleccionada Líneas de Código
    @Getter
    @Setter
    private double sLoC = 0.0;//Valor de las Líneas de código fuente
    @Getter
    @Setter
    private double kLoC = 0.0;//Valor de las Kilo Líneas de código fuente
    
    /**

     * Método para realizar un conjunto de llamados a otros métodos:
     *  Multiplicación de los Puntos de Función Ajustados con las líneas de código seleccionadas

     */
    public void actualizarLC() {
        if(LC>0){
            sLoC = util.redondear2Decimales(pfModel.getPFA() * LC);
        }else{
            sLoC = util.redondear2Decimales(pfModel.getPFA() * util.conversionLC(strLC));
        }
        
    }

// PAS0 04 modelo basico    
    //Variables para calcular esfuerzo y duración
    @Getter
    @Setter
    private double spOab = 2.4, spObb = 1.05, spOcb = 2.5, spOdb = 0.38;
    @Getter
    @Setter
    private double spSab = 3.0, spSbb = 1.12, spScb = 2.5, spSdb = 0.35;
    @Getter
    @Setter
    private double spEab = 3.6, spEbb = 1.20, spEcb = 2.5, spEdb = 0.32;
    @Getter
    @Setter
    private int SP = 0;//Tipo de Proyecto de Software(Software Proyect)
    @Getter
    @Setter
    private double effort = 0.0;//Esfuerzo
    @Getter
    @Setter
    private double duration = 0.0;//Duración 

    /**

     * Método para calcular Kilo Líneas de código fuente
     *  Se realizar dividiendo sobre 1000 las Líneas de código fuente

     */
    public void slocTOkloc() {
        kLoC = (sLoC / 1000);
    }

    /**

     * Método para calcular el Esfuerzo y la Duración dependiendo de la elección de Proyecto de Software
     *  0 para PS Orgánico
     *  1 para PS Semi-Embebido
     *  2 para PS Incrustado

     */
    public void calcularED() {
        switch (SP) {
            case 0:
                effort = (util.redondear2Decimales(spOab * Math.pow(kLoC, spObb)));
                duration = (util.redondear2Decimales(spOcb * Math.pow(effort, spOdb)));
                break;
            case 1:
                effort = (util.redondear2Decimales(spSab * Math.pow(kLoC, spSbb)));
                duration = (util.redondear2Decimales(spScb * Math.pow(effort, spSdb)));
                break;
            case 2:
                effort = (util.redondear2Decimales(spEab * Math.pow(kLoC, spEbb)));
                duration = (util.redondear2Decimales(spEcb * Math.pow(effort, spEdb)));
                break;
        }
    }

     /**

     * Método para realizar un conjunto de llamados a otros métodos:
     *  Calculara las KLOC y LOC
     *  Calculara el Esfuerzo y Duración con relación al tipo de proyecto de software

     */
    public void actualizarSP() {
        slocTOkloc();
        calcularED();
    }
    
    /**
     * Método para actualizar todos los métodos de COCOMO 2 Modelo Básico
     *  Realizara la actualización del método PFNA del paso 01
     *  Realizara la actualización del método PFA del paso 02
     *  Realizara la actualización del método LC del paso 03
     *  Realizara la actualización del método SP del paso 04
     */
    public void acturalizarBasico() {
        actualizarPFNA();
        actualizarPFA();
        actualizarLC();
        actualizarSP();
    }

// PAS0 04
    @Getter
    @Setter
    private double prec = 0.0;//Precedentes
    @Getter
    @Setter
    private double flex = 0.0;//Flexibildad de Desarrollo
    @Getter
    @Setter
    private double resl = 0.0;//Resolución de Arquitectura/Riesgo
    @Getter
    @Setter
    private double team = 0.0;//Cohesión del equipo de trabajo
    @Getter
    @Setter
    private double pmat = 0.0;//Madurez del Proceso 
    @Getter
    @Setter
    private double sumFE = 0.0;//Suma total de las valoraciones
    @Getter
    @Setter
    private double feB = 0.0;//Factor de Escala 
    
    /**

     * Método para sumar todas las valoraciones de los Factores de escala

     */
    public void sumaCalifiacionesFE() {
        sumFE = (util.redondear2Decimales(prec + flex + resl + team + pmat));
    }

    /**

     * Método para calcular el factor de Escala utilizando la suma total de las valoraciones

     */
    public void factorEscala() {
        feB = (0.91 + 0.01 * sumFE);
    }

    /**

     * Método para realizar un conjunto de llamados a otros métodos:
     *  Se sumará el total de las valoraciones de cada factor
     *  Calculara el factor de escala a base de la suma total de los factores

     */
    public void actualizarFE5() {
        sumaCalifiacionesFE();
        factorEscala();
    }

// PAS0 05 
    @Getter
    @Setter
    private double rely = 1.0;//Fiabilidad requerida del software 
    @Getter
    @Setter
    private double data = 1.0;//Tamaño de la base de datos
    @Getter
    @Setter
    private double cplx = 1.0;//Complejidad del producto
    @Getter
    @Setter
    private double docu = 1.0;//Alcance de la documentación requerida
    @Getter
    @Setter
    private double ruse = 1.0;//Porcentaje requerido de componentes reutilizables
    @Getter
    @Setter
    private double time = 1.0;//Restricciones del tiempo de ejecución
    @Getter
    @Setter
    private double stor = 1.0;//Restricciones del almacenamiento principal
    @Getter
    @Setter
    private double virt = 1.0;//Inestabilidad de la máquina virtual
    @Getter
    @Setter
    private double turn = 1.0;//Tiempo de respuesta del computador
    @Getter
    @Setter
    private double acap = 1.0;//Capacidad del analista
    @Getter
    @Setter
    private double aexp = 1.0;//Experiencia en la aplicación
    @Getter
    @Setter
    private double pcap = 1.0;//Capacidad de los programadores
    @Getter
    @Setter
    private double vexp = 1.0;//Experiencia en S.O utilizado
    @Getter
    @Setter
    private double lexp = 1.0;//Experiencia en el Lenguaje de Programación
    @Getter
    @Setter
    private double modp = 1.0;//Uso de prácticas de programación modernas
    @Getter
    @Setter
    private double tool = 1.0;//Uso de herramientas software
    @Getter
    @Setter
    private double sced = 1.0;//Uso de herramientas software
    @Getter
    @Setter
    private double fm = 1.0;//Factor Multiplicativo (Multiplicador de Esfuerzo)

     /**

     * Método para calcular la multiplicación total de todos los factores compuestos

     */
    public void factorMultiplicativo() {
        fm = (util.redondear2Decimales(rely * data * cplx * docu * ruse * time * stor * virt * turn * acap * aexp * pcap * vexp * lexp * modp * tool * sced));
    }

    /**

     * Método para realizar un conjunto de llamados a otros métodos:
     *  Calculo del factor Multiplicativo (Multiplicador de Esfuerzo)

     */
    public void actualizarFEC() {
        factorMultiplicativo();
    }
    
//PASO 06
    @Getter
    @Setter
    private int personas = 0;//Número de personas
    @Getter
    @Setter
    private double imprevistos = 0;//Imprevisto
    @Getter
    @Setter
    private double sueldo = 0;//Sueldo de programadores
    @Getter
    @Setter
    private double costoTotal = 0;//Costo total del proyecto

    /**

     * Método para realizar un conjunto de llamados a otros métodos:
     *  Calculara el KLOC
     *  Calculamos el Esfuerzo, Duración, Personas y Costo total

     */
    public void acturalizarED() {
        slocTOkloc();
        effort = util.redondear2Decimales(2.94 * Math.pow(kLoC, feB) * fm);
        duration = util.redondear2Decimales(3.67 * Math.pow(effort, (0.28 + 0.002 * sumFE)));
        personas =(int) Math.ceil(util.redondear2Decimales(effort / duration));
        costoTotal = util.redondear2Decimales((sueldo * ((duration * 1.25) * (personas * 1.10)) + imprevistos));
    }
    
    /**
     * Método para actualizar todos los métodos de COCOMO 2 Modelo Completo
     *  Realizara la actualización del método PFNA del paso 01
     *  Realizara la actualización del método PFA del paso 02
     *  Realizara la actualización del método LC del paso 03
     *  Realizara la actualización del método FE5 del paso 04
     *  Realizara la actualización del método FEC del paso 05
     *  Realizara la actualización del método ED del paso 06
     */
    public void acturalizarComplejo() {
        actualizarPFNA();
        actualizarPFA();
        actualizarLC();
        actualizarFE5();
        actualizarFEC();
        acturalizarED();
    }
    
//Generación PDF    
    /**
      
     * Método para general PDF
     
     * @throws IOException Excepciones varias
     
     */
    public void generarPDF() throws IOException{
        util.plantillaParaPDF( pfModel.getPFA(), sLoC);
    }
}