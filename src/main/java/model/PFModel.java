package model;

import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase encapsulará los datos principales del modelo: 
 *  Punto Función No-Ajustados
 *  Punto Función Ajustados
 *  Factor de Ajuste
 *
 */
@Getter//Genera todos los getters de los respectivos atributos de la clase
@Setter//Genera todos los setters de los respectivos atributos de la clase
public class PFModel {
    
    private int PFNA; // Valor final Punto Función No-Ajustados
    private double factorDeAjuste; // Valor de Factor de Ajuste (se calcula multiplicando la suma de las preguntas por 0.01 + 0.65)
    private double PFA; //Valor final Punto Función Ajustados (resulta al multiplica: factorDeAjuste * PFNA)

}
