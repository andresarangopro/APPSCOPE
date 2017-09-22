package com.example.felipearango.appscope;

import java.util.ArrayList;

/**
 * Created by Felipe Arango on 22/09/2017.
 */

public class UsuarioCorriente {

    //////////////////////////
    //Variables
    /////////////////////////

    private String nombre;
    private String apellido;
    private String ocupacion;
    private String fechaNacimiento;
    private String universidad;
    private String celular;
    private String correo;
    private String direccion;
    private String foto;
    private String frase;
    private String idiomas;
    private String hobbies;
    private String conocimientosInformaticos;
    private ArrayList<String> anexos;
    private ArrayList<String> experienciasProfesionales;
    private ArrayList<String> referenciasEmpleo;
    private ArrayList<String> formacion;


    //////////////////////////
    //Constructor
    /////////////////////////

    public UsuarioCorriente(String nombre, String apellido, String ocupacion,
                            String fechaNacimiento, String universidad, String celular,
                            String correo, String direccion, String foto, String frase,
                            String idiomas, String hobbies, String conocimientosInformaticos,
                            ArrayList<String> anexos, ArrayList<String> experienciasProfesionales,
                            ArrayList<String> referenciasEmpleo, ArrayList<String> formacion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ocupacion = ocupacion;
        this.fechaNacimiento = fechaNacimiento;
        this.universidad = universidad;
        this.celular = celular;
        this.correo = correo;
        this.direccion = direccion;
        this.foto = foto;
        this.frase = frase;
        this.idiomas = idiomas;
        this.hobbies = hobbies;
        this.conocimientosInformaticos = conocimientosInformaticos;
        this.anexos = anexos;
        this.experienciasProfesionales = experienciasProfesionales;
        this.referenciasEmpleo = referenciasEmpleo;
        this.formacion = formacion;
    }

    //////////////////////////
    //Getters and Setters
    /////////////////////////

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getConocimientosInformaticos() {
        return conocimientosInformaticos;
    }

    public void setConocimientosInformaticos(String conocimientosInformaticos) {
        this.conocimientosInformaticos = conocimientosInformaticos;
    }

    public ArrayList<String> getAnexos() {
        return anexos;
    }

    public void setAnexos(ArrayList<String> anexos) {
        this.anexos = anexos;
    }

    public ArrayList<String> getExperienciasProfesionales() {
        return experienciasProfesionales;
    }

    public void setExperienciasProfesionales(ArrayList<String> experienciasProfesionales) {
        this.experienciasProfesionales = experienciasProfesionales;
    }

    public ArrayList<String> getReferenciasEmpleo() {
        return referenciasEmpleo;
    }

    public void setReferenciasEmpleo(ArrayList<String> referenciasEmpleo) {
        this.referenciasEmpleo = referenciasEmpleo;
    }

    public ArrayList<String> getFormacion() {
        return formacion;
    }

    public void setFormacion(ArrayList<String> formacion) {
        this.formacion = formacion;
    }
}
