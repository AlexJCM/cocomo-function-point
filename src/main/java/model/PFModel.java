package model;

/*
Esta clase encapsulará los datos principales del modelo: 
    -Punto Funcion No-Ajustados
    -Punto Funcion Ajustados
    -Factor de Ajuste
    -Esfuerzo ???
    -Duracion ???
Asi como los distintos metodos para interactuar con los mismos.
 */
public class PFModel {

    private int PFNA; // Valor final Punto Funcion No-Ajustados
    private double factorDeAjuste; //  Valor de Factor de Ajuste (se calcula multiplicando la suma de las preguntas por 0.01 + 0.65)
    private double PFA; //Valor final Punto Funcion Ajustados (resulta al multiplica: factorDeAjuste * PFNA)

    public int getPFNA() {
        return PFNA;
    }

    public void setPFNA(int PFNA) {
        this.PFNA = PFNA;
    }

    public double getFactorDeAjuste() {
        return factorDeAjuste;
    }

    public void setFactorDeAjuste(double factorDeAjuste) {
        this.factorDeAjuste = factorDeAjuste;
    }

    public double getPFA() {
        return PFA;
    }

    public void setPFA(double PFA) {
        this.PFA = PFA;
    }

}
