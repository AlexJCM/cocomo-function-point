package controller;

import model.PFModel;
import controller.Utils;        
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase será la encargada de realizar todas las operaciones respectivas al
 * cálculo del PF y hacer que estos cambios se reflejen en el modelo.
 *
 */
//@Named("pf")
@ManagedBean(name = "pf")// Registramos la clase con JSF y le etiquetamos con un nombre, en este caso "pf", a través del cual se vinculará con los componentes de las vistas JSF. Es decir, las páginas JSF mediante dichas etiquetas pueden acceder al ManagedBean (ya sea a sus propiedades o métodos).
@ViewScoped //Indica que las instancias de la clase serán creadas y gestionadas por el framework JSF.
public class PFController  {

    //private static final long serialVersionUID = 1L;
    
    //New feature v2 by alex
    @Getter
    @Setter
    private PFModel pfModel;//Instancia de PFModel
    private Utils util;//Instancia de Utils
    
    /**
     * Constructor PFController, su principal función es crear una instancia del
     * modelo PFModel y de la clase Utils.
     *
     */
    public PFController() {
        pfModel = new PFModel();
        util = new Utils();
    }
    
    /**
     * Método para retornar el modelo 
     * @return Retorna el modelo
     */
    public PFModel getPfModel() {
        if (pfModel == null) {
            pfModel = new PFModel();
        }
        return pfModel;
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
     *
     * Método para calcular subtotales de Dominios
     *
     */
    public void calcularSubtotalesDominios() {
        subTotalEE = eeSimple * 3 + eePromedio * 4 + eeComplejo * 6;
        subTotalSE = seSimple * 4 + sePromedio * 5 + seComplejo * 7;
        subTotalCE = ceSimple * 3 + cePromedio * 4 + ceComplejo * 6;
        subTotalALI = aliSimple * 7 + aliPromedio * 10 + aliComplejo * 15;
        subTotalAIE = aieSimple * 5 + aiePromedio * 7 + aieComplejo * 10;
    }

    /**
     * Método para obtener el valor de los Puntos de Función NO Ajustados     *
     * @return Puntos de Función NO Ajustados
     */
    public int getPFNA() {
        return pfModel.getPFNA();
    }

    /**
     * Método para realizar la suma final de todos los puntos función No
     * Ajustados parciales y los reflejará en el modelo
     */
    public void actualizarPFNA() {
        if (pfModel == null) {
            throw new NullPointerException("pfModel tiene datos nulos o es nulo");
        }
        if (pfModel.getPFNA() < 0) {
            throw new NumberFormatException("El valor de PFNA debe ser  un valor positivo!");
        }
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
     *
     * Método para realizar la suma final de todas las calificaciones para
     * Ajuste de los Puntos de Función
     *
     * @return true si es que el totalCalificaciones es mayor a 70
     */
    public boolean calcularSumaCalifiaciones() {
        boolean superaLimite = true;
        totalCalifiaciones = p1Califiacion + p2Califiacion + p3Califiacion + p4Califiacion + p5Califiacion + p6Califiacion + p7Califiacion + p8Califiacion + p9Califiacion + p10Califiacion + p11Califiacion + p12Califiacion + p13Califiacion + p14Califiacion;
        superaLimite = totalCalifiaciones > 70;

        return superaLimite;
    }

    /**
     *
     * Método para obtener el Factor de Ajuste del Modelo PFModel
     *
     * @return double retorna el valor del Factor de Ajuste
     *
     */
//    public double getFactorAjuste() {
//        return pfModel.getFactorDeAjuste();
//    }

    /**
     *
     * Método para calcular el Factor de Ajuste
     *
     * @param totalCalifiaciones Valor para calcular el Factor de Ajuste
     *
     */
    public void setFactorAjuste(double totalCalifiaciones) {
        pfModel.setFactorDeAjuste(util.redondear2Decimales(totalCalifiaciones * 0.01 + 0.65));
    }

    /**
     *
     * Método para calcular los puntos de función
     *
     */
    public boolean ajustarPuntoDeFuncion() {
        boolean superaLimiteInferior = true;
        pfModel.setPFA(util.redondear2Decimales(pfModel.getPFNA() * pfModel.getFactorDeAjuste()));
        superaLimiteInferior = pfModel.getPFA() < 1.95;

        return superaLimiteInferior;
    }

    /**
     *
     * Método para obtener el valor de los Puntos de Función Ajustados
     *
     * @return Puntos de Función Ajustados
     *
     */
    public double getPFA() {
       return pfModel.getPFA();
   }

    /**
     *
     * Método para realizar un conjunto de llamados a otros métodos: Calculara
     * la suma de todas las calificaciones Calculara el factor de ajuste
     * Ajustara los Puntos de Función
     *
     */
    public void actualizarPFA() {
        if (pfModel == null) {
            throw new NullPointerException("pfModel tiene datos nulos o es nulo");
        }
        if (pfModel.getPFA() < 0) {
            throw new NumberFormatException("El valor de PFA debe ser  un valor positivo!");
        }
        if (calcularSumaCalifiaciones()) {
            throw new NumberFormatException("El valor de suma total de Calificaciones debe ser <= 70");
        } else {
            setFactorAjuste(Double.valueOf(totalCalifiaciones));
            //setea el valor de PFA          
            if (ajustarPuntoDeFuncion()) {
                System.out.println("***** pfModel.getPFA() es < 1.95 *******");
                throw new NumberFormatException("El valor minimo posible del PFA debe ser >= 1.95");
            }
        }
    }

// PAS0 03    
    @Getter
    @Setter
    private String strLC = "Java *53";//Líneas de código en formato String para guardar el valor obtenido del Select
    @Getter
    @Setter
    private int LC = 53;//Valor de las seleccionadaS Líneas de Código (por defecto tiene 53 por Java)
    @Getter
    @Setter
    private Boolean editar = false;//Boolean para conocer si el valor se editara
    @Getter
    @Setter
    private double sLoC = 0.0;//Valor de las Líneas de código fuente
    @Getter
    @Setter
    private double kLoC = 0.0;//Valor de las Kilo Líneas de código fuente

    /**
     *
     *  Multiplicación de los Puntos de Función Ajustados con las líneas de código 
     * seleccionadas depende de que valor se obtiene si el del select o del inputex se determina mediante un boolean.
     *  Multiplicación de los Puntos de Función Ajustados con las líneas de código seleccionadas depende de que valor se obtiene si el del select o del inputex se determina mediante un boolean.

     */
    public void actualizarLC() { 
        if (pfModel == null) {
            System.out.println("************ actualizarLC() pfModel es null *********");
            throw new NullPointerException("pfModel tiene datos nulos o es nulo");
        }  
        if (LC < 14 || LC > 209) {
            throw new NumberFormatException("El valor de LC debe ser  un valor >= 14 y <= 209");
        }
        if(editar==true){
            sLoC = util.redondear2Decimales(pfModel.getPFA() * LC);
        }else{
        LC=util.conversionLC(strLC);
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
     *
     * Método para calcular Kilo Líneas de código fuente Se realizar dividiendo
     * sobre 1000 las Líneas de código fuente
     *
     */
    public void slocTOkloc() {
        kLoC = (sLoC / 1000);
    }

    /**
     *
     * Método para calcular el Esfuerzo y la Duración dependiendo de la elección
     * de Proyecto de Software 0 para PS Orgánico 1 para PS Semi-Embebido 2 para
     * PS Incrustado
     *
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
     *
     * Método para realizar un conjunto de llamados a otros métodos: Calculara
     * las KLOC y LOC Calculara el Esfuerzo y Duración con relación al tipo de
     * proyecto de software
     *
     */
    public void actualizarSP() {
        slocTOkloc();
        calcularED();
    }

    /**
     * Método para actualizar todos los métodos de COCOMO 2 Modelo Básico
     * Realizara la actualización del método PFNA del paso 01 Realizara la
     * actualización del método PFA del paso 02 Realizara la actualización del
     * método LC del paso 03 Realizara la actualización del método SP del paso
     * 04
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
     *
     * Método para sumar todas las valoraciones de los Factores de escala
     *
     */
    public void sumaCalifiacionesFE() {
        sumFE = (util.redondear2Decimales(prec + flex + resl + team + pmat));
    }

    /**
     *
     * Método para calcular el factor de Escala utilizando la suma total de las
     * valoraciones
     *
     */
    public void factorEscala() {
        if (prec < 0.0 & flex < 0.0 & resl < 0.0 & team < 0.0 & pmat < 0.0) {
            throw new NumberFormatException("El factor de escala debe ser un valor positivo!");
        } else {
            feB = util.redondear2Decimales(0.91 + 0.01 * sumFE);
        }
    }

    /**
     *
     * Método para realizar un conjunto de llamados a otros métodos: Se sumará
     * el total de las valoraciones de cada factor Calculara el factor de escala
     * a base de la suma total de los factores
     *
     */
    public void actualizarFE5() {
        if (pfModel == null) {
            System.out.println("************ actualizarFE5() pfModel es null *********");
            throw new NullPointerException("pfModel tiene datos nulos o es nulo");
        }
        sumaCalifiacionesFE();
        if (sumFE < 0.0 || sumFE > 31.62) {
            throw new NumberFormatException("La suma de los factores debe ser mayor a cero y menor a 31.62");
        } else {
            factorEscala();
        }
    }

// PAS0 05 
    @Getter
    @Setter
    private double rely = 1.0;//Seguridad requerida
    @Getter
    @Setter
    private double data = 1.0;//Tamaño de base de datos
    @Getter
    @Setter
    private double docu = 1.0;//Documentación adaptada de ciclo de vida
    @Getter
    @Setter
    private double cplx = 1.0;//Complejidad
    @Getter
    @Setter
    private double ruse = 1.0;//Reutilización requerida
    @Getter
    @Setter
    private double time = 1.0;//Tiempo de ejecución requerido
    @Getter
    @Setter
    private double stor = 1.0;//Almacenamiento principal requerido
    @Getter
    @Setter
    private double pvol = 1.0;//Volatilidad de la plataforma
    @Getter
    @Setter
    private double acap = 1.0;//Capacidad del analista
    @Getter
    @Setter
    private double aexp = 1.0;//Experiencia del analista
    @Getter
    @Setter
    private double pcap = 1.0;//Capacidad del programador
    @Getter
    @Setter
    private double pexp = 1.0;//Experiencia en la plataforma de S.O
    @Getter
    @Setter
    private double ltex = 1.0;//Experiencia en lenguaje y herramienta
    @Getter
    @Setter
    private double pcon = 1.0;//Continuidad del personal
    @Getter
    @Setter
    private double tool = 1.0;//Uso de herramientas de software
    @Getter
    @Setter
    private double site = 1.0;//Desarrollo multitarea
    @Getter
    @Setter
    private double sced = 1.0;//Esquema de desarrollo programado
    
    @Getter
    @Setter
    private double fm = 1.0;//Factor Multiplicativo (Multiplicador de Esfuerzo)

    /**
     *
     * Método para calcular la multiplicación total de todos los factores
     * compuestos
     *
     */
    public void factorMultiplicativo() {
        if (rely<=0.0&data<=0.0&docu<=0.0&cplx<=0.0&ruse<=0.0&time<=0.0&stor<=0.0&pvol<=0.0&acap<=0.0&aexp<=0.0&pcap<=0.0&pexp<=0.0&ltex<=0.0&pcon<=0.0&tool<=0.0&site<=0.0&sced<=0.0) {
            throw new NumberFormatException("Los Factores de Esfuerzo Compuesto deben ser mayor a cero!");
        }else{
            fm = (util.redondear2Decimales(rely * data * docu * cplx * ruse * time * stor * pvol * acap * aexp * pcap * pexp * ltex * pcon * tool * site * sced));
        }
    }

    /**
     *
     * Método para realizar un conjunto de llamados a otros métodos: Calculo del
     * factor Multiplicativo (Multiplicador de Esfuerzo)
     *
     */
    public void actualizarFEC() {
        if (pfModel == null) {
            System.out.println("************ actualizarFEC() pfModel es null *********");
            throw new NullPointerException("pfModel tiene datos nulos o es nulo");
        }
        factorMultiplicativo();
        if (fm <= 0 || fm >150) {
            throw new NumberFormatException("El factor multiplicativo debe ser mayor a cero y menor a 149.38");
        }
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
     *
     * Método para realizar un conjunto de llamados a otros métodos: Calculara
     * el KLOC Calculamos el Esfuerzo, Duración, Personas y Costo total
     *
     */
    public void actualizarED() {
        if (pfModel == null) {
            System.out.println("************ actualizarED() pfModel es null *********");
            throw new NullPointerException("pfModel tiene datos nulos o es nulo");
        } else {
            slocTOkloc();
            if (kLoC <= 0.0) {
                throw new NumberFormatException("KLOC debe ser mayor a cero!");
            } else {
                effort = util.redondear2Decimales(2.94 * Math.pow(kLoC, feB) * fm);
                duration = util.redondear2Decimales(3.67 * Math.pow(effort, (0.28 + 0.002 * sumFE)));
                personas = (int) Math.ceil(effort / duration);
                imprevistos = util.redondear2Decimales((sueldo * ((duration * 1.25) * (personas)) * 0.1));
                costoTotal = util.redondear2Decimales((sueldo * ((duration * 1.25) * (personas)) + imprevistos));
            }
        }
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
        actualizarPFNA(); // minimop valor posibles de PFNA es 3. Y buscar el valor maximo posible
        actualizarPFA(); // max Valor es 70 y minimo 0
        actualizarLC(); //minimo 14 y maximo 119 de LOC x PF.
        actualizarFE5(); // max valor 35.x
        actualizarFEC();
        actualizarED();
    }

//Generación PDF    
    /** 
     * Método para general PDF
     * @throws IOException Excepciones varias
     */
    public void generarPDF() throws IOException {
        util.plantillaParaPDF(pfModel.getPFA(), sLoC);
    }
}