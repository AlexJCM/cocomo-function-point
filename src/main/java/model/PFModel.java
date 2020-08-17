package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase encapsulará los datos principales del modelo: 
 *  Punto Funcion No-Ajustados
 *  Punto Funcion Ajustados
 *  Factor de Ajuste
 * 
 * @author: Alex Chamba, Diego Merino, Anthony Ortega
 * 
 * @version: 16/08/2020/PF
 * 
 * @Getter.- Genera todos los getters de los respectivos atributos de la clase
 * 
 * @Setter.- Genera todos los setters de los respectivos atributos de la clase
 * 
 */
@Getter
@Setter
public class PFModel {
    
    private int PFNA; // Valor final Punto Funcion No-Ajustados
    private double factorDeAjuste; //  Valor de Factor de Ajuste (se calcula multiplicando la suma de las preguntas por 0.01 + 0.65)
    private double PFA; //Valor final Punto Funcion Ajustados (resulta al multiplica: factorDeAjuste * PFNA)

}
