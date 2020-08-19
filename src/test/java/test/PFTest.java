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
        pfModel = new PFModel();
    }

    // One big test
    @Test
    public void testBeanController() {
        assertThat(PFController.class, allOf(
                hasValidBeanConstructor()
               // hasValidGettersAndSetters()
        ));
    }

    /**
     * Probar el constructor No-Args asegura que un bean tenga un constructor
     * sin argumentos que funcione.
     */
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(PFController.class, hasValidBeanConstructor());
    }

    /**
     * Probar Getters y Setters Matchers para garantizar que lo que se
     * almacena44 con un setter es lo que se obtiene con el getter relacionado.
     * Nos asegúrese de que todas las propiedades del bean tengan getters y
     * setters que funcionen.
     */
//    @Test
//    public void gettersAndSettersShouldWorkForEachProperty() {
//        assertThat(PFController.class, hasValidGettersAndSetters());     
//    }

    /**
     * Asegúrese de que la propiedad "testOnlyThisProperty" tenga un getter y
     * setter que funcionen.
     */
    @Test
    public void gettersAndSettersShouldWorkForXXXProperty() {
        assertThat(PFController.class, hasValidGettersAndSettersFor("eeSimple"));
    }

    /**
     * Asegúrese de que todas las propiedades del bean, excepto la propiedad
     * denominada "dontTestPropertyWithThisName", tengan getters y setters que
     * funcionen.
     */
//     @Test
//    public void gettersAndSettersShouldWorkForNotXXXProperty() {
//        assertThat(PFController.class, hasValidGettersAndSettersExcluding("PFA"));
//    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
