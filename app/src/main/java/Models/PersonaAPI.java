package Models;

public class PersonaAPI extends Persona{
    private int idPersona;
    private String foto;
    public PersonaAPI(String nombres, String apellidos, String sexo,String ciudad,
                      int edad, String dni, double peso, double altura,String foto){
        super(nombres,apellidos,sexo,ciudad,edad,dni,peso,altura);
        this.foto=foto;
    }
    public String getFoto() {
        return foto;
    }
    public int getIdPersona() {
        return idPersona;
    }
    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
}
