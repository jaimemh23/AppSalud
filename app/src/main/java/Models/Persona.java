package Models;

import androidx.annotation.NonNull;

public class Persona {
    private String nombres;
    private String apellidos;
    private int edad;
    private String dni;
    private double peso;
    private double altura;

    public Persona(String nombres, String apellidos, int edad, String dni, double peso, double altura) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
    }
    public int calcularIMC(){
        double par=peso/Math.pow(altura,2);
        if(par<20)
            return -1;
        else if(par>=20 && par<=25)
            return 0;
        else
            return 1;
    }
    public boolean esMayorEdad(){
        if(edad>=18)
            return true;
        else
            return false;
    }
    public boolean verificarDNI(){
        if(dni.length()==8)
            return true;
        else
            return false;
    }

    @NonNull
    @Override
    public String toString() {
        String[] tipoPeso ={"debajo de ideal","ideal","sobre ideal"};
        return apellidos+", "+nombres+" tiene peso "+tipoPeso[calcularIMC()+1]+
                " y es "+(esMayorEdad()?"mayor de edad":"menor de edad");
    }
}
