package model;

import lombok.Getter;
import lombok.Setter;

/*
Esta clase encapsulará los datos principales del modelo: 
    -Punto Funcion No-Ajustados
    -Punto Funcion Ajustados
    -Factor de Ajuste
    -Esfuerzo ???
    -Duracion ???
Asi como los distintos metodos para interactuar con los mismos.
 */
@Getter
@Setter
public class PFModel {
    
    private int PFNA; // Valor final Punto Funcion No-Ajustados
    private double factorDeAjuste; //  Valor de Factor de Ajuste (se calcula multiplicando la suma de las preguntas por 0.01 + 0.65)
    private double PFA; //Valor final Punto Funcion Ajustados (resulta al multiplica: factorDeAjuste * PFNA)

}
