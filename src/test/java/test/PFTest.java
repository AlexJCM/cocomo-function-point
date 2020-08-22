package test;

import model.PFModel;
import controller.PFController;
import javax.validation.Validation;
import org.junit.Before;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static com.google.code.beanmatchers.BeanMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Referencias: https://github.com/orien/bean-matchers
 *
 * @author Alex
 */
public class PFTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    private PFModel pfModel;
    private PFController pf;

    /**
     * Creamos un Bean Validator. Para ello crearemos una instancia de
     * ValidatorFactory que nos proporcionará un Validator. Se ejecuta antes de
     * todas las pruebas en la clase.
     */
    @BeforeClass
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /**
     * Se ejecuta después de todas las pruebas en la clase. En este caso
     * eliminamos validatorFactory para liberar recursos.
     */
    @AfterClass
    public static void close() {
        validatorFactory.close();
    }

    /**
     * Se ejecuta antes de cada test
     */
    @Before
    public void init() {
        pf = new PFController();
        pf.getPfModel().setPFNA(45);
        pf.getPfModel().setPFA(15.33);
        pf.getPfModel().setPFNA(11);

        pfModel = new PFModel();
    }

    /**
     * Probar el constructor No-Args asegura que un bean tenga un constructor
     * sin argumentos que funcione.
     *
     * Probar todos los Getters y Setters (excepto los de la propiedad
     * factorAjuste) para garantizar que lo que se almacena con un setter es lo
     * que se obtiene con el getter relacionado. Nos asegúrese de que todas las
     * propiedades del bean tengan getters y setters que funcionen.
     */
    @Test
    public void testBeanPFController() {
        assertThat(PFController.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSettersExcluding("factorAjuste", "PFNA", "PFA")
        // hasValidGettersAndSetters()              
        ));
    }

    /**
     * Asegúrese de que la propiedad "eeSimple" tenga un getter y setter que
     * funcionen.
     */
//    @Test
//    public void gettersAndSettersShouldWorkForXXXProperty() {
//        assertThat(PFController.class, hasValidGettersAndSettersFor("eeSimple"));
//    }
    //***************** ACTUALIZAR PFNA ***************************
    //*************************************************************
    /**
     * Test para verificar que el valor de PFNA es negativo
     */
    @Test(expected = NumberFormatException.class)
    public void testActualizarPFNATieneValorNegativo() {
        //PFNA con valor negativo
        pfModel.setPFNA(-6);
        pf.setPfModel(pfModel);
        pf.actualizarPFNA();
    }

    /**
     * Test para verificar cuaNdo el objto pfModel es nulo
     */
    @Test(expected = NullPointerException.class)
    public void testPModelConActualizarPFNAEsNulo() {
        PFModel pfModel = null;
        pf.setPfModel(pfModel);
        pf.actualizarPFNA();
    }

    //***************** ACTUALIZAR PFA ***************************
    //*************************************************************
    /**
     * Test para verificar que el valor de PFA es negativo
     */
    @Test(expected = NumberFormatException.class)
    public void testActualizarPFATieneValorNegativo() {
        //PFA con valor negativo
        pfModel.setPFA(-111);
        pf.setPfModel(pfModel);
        pf.actualizarPFA();
    }

    /**
     * Test para verificar cuando el objeto pfModel es nulo
     */
    @Test(expected = NullPointerException.class)
    public void testPModelConActualizarPFAEsNulo() {
        PFModel pfModel = null;
        pf.setPfModel(pfModel);
        pf.actualizarPFA();
    }

    /**
     * Test para verificar que el valor de PFA es mayor a 70
     */
    @Test(expected = NumberFormatException.class)
    public void testSumaCalificacionesEsMayorASetenta() {
        int valorLimiteSuperior = 71;
        pf.setP1Califiacion(valorLimiteSuperior);
        pf.actualizarPFA(); 
    }
    
     /**
     * Test para verificar que al momento del calculo el valor de PFA no debe ser menor a 1.95
     */
    @Test(expected = NumberFormatException.class)
    public void testTotalPFAesMenorAUnoPuntoNoventa() {
       // double valorLimiteInferior = 1.95;
      //  pfModel.setPFA(valorLimiteInferior);
        pfModel.setPFNA(2);//Guardar en PFNA un valor >= 3 incide en que el PFA sea >= 1.95
        pf.setPfModel(pfModel);
        pf.actualizarPFA();
    }
    
    //***************** ACTUALIZAR LOC ***************************
    //*************************************************************
        /**
     * Test para verificar que el valor de LC es menor a 14
     */
    @Test(expected = NumberFormatException.class)
    public void testActualizarLCTieneValorMenorAlLimite() {
        //LC con valor menor a 14  
        pf.setLC(10);
        pf.actualizarLC();
    }
    
       /**
     * Test para verificar que el valor de LC es mayor a 209
     */
    @Test(expected = NumberFormatException.class)
    public void testActualizarLCTieneValorMayorAlLimite() {
        //LC con valor mayor a 209
        pf.setLC(211);
        pf.actualizarLC();
    }

    /**
     * Test para verificar cuando el objeto pfModel con actualizarLC es nulo
     */
    @Test(expected = NullPointerException.class)
    public void testPModelConActualizarLCesNulo() {
        PFModel pfModel = null;
        pf.setPfModel(pfModel);
        pf.actualizarLC();
    }
}
