package Models;

import android.net.Uri;

import androidx.annotation.NonNull;

public class Persona {
    private String nombres;
    private String apellidos;
    private String sexo;
    private String ciudad;
    private int edad;
    private String dni;
    private double peso;
    private double altura;
    private byte[] foto;

    public Persona(String nombres, String apellidos, String sexo,String ciudad,
                   int edad, String dni, double peso, double altura, byte[] foto) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.ciudad = ciudad;
        this.edad = edad;
        this.dni = dni;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getNombreCompleto(){
        return apellidos+", "+nombres;
    }
    public String getTipoPeso(){
        String[] tipoPeso ={"debajo de ideal","ideal","sobre ideal"};
        return tipoPeso[calcularIMC()+1];
    }
    public String getTipoPersona(){
        return esMayorEdad()?"mayor de edad":"menor de edad";
    }
    public String getSexo(){
        return sexo;
    }
    public String getCiudad(){
        return ciudad;
    }

    public int getEdad() {
        return edad;
    }

    public double getPeso() {
        return peso;
    }

    public double getAltura() {
        return altura;
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
