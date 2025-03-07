package Models;

public class PersonaAPI extends Persona{
    private int idPersona;
    private String foto;
    private String ruta;
    public PersonaAPI(int idPersona,String nombres, String apellidos, String sexo,String ciudad,
                      int edad, String dni, double peso, double altura,String foto, String ruta){
        super(nombres,apellidos,sexo,ciudad,edad,dni,peso,altura);
        this.idPersona=idPersona;
        this.foto=foto;
        this.ruta=ruta;
    }
    public String getFoto() {
        return foto;
    }
    public int getIdPersona() {
        return idPersona;
    }

    public String getRuta() {
        return ruta;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }
}
