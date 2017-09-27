package com.example.felipearango.appscope;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Felipe Arango on 22/09/2017.
 */

public class UsuarioCorriente {

    //////////////////////////
    //Variables
    /////////////////////////

    private String id;
    private String nombre;
    private String apellido;
    private String ocupacion;
    private String fechaNacimiento;
    private String universidad;
    private String celular;
    private String correo;
    private String foto;
    private String frase;
    private String hobbies;
    private String conocimientosInformaticos;
    private String estadoCuenta;
    private ArrayList<String> anexos;
    private ArrayList<String> idiomas;
    private ArrayList<String> experienciasProfesionales;
    private ArrayList<String> referenciasEmpleo;
    private ArrayList<String> formacion;


    //////////////////////////
    //Constructor
    /////////////////////////

    public UsuarioCorriente(String id, String nombre, String apellido, String ocupacion,
                            String fechaNacimiento, String universidad, String celular,
                            String correo, String foto, String frase, String hobbies,
                            String conocimientosInformaticos, String estadoCuenta,
                            String anexos, String idiomas,
                            String experienciasProfesionales,
                            String referenciasEmpleo, String formacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ocupacion = ocupacion;
        this.fechaNacimiento = fechaNacimiento;
        this.universidad = universidad;
        this.celular = celular;
        this.correo = correo;
        this.foto = foto;
        this.frase = frase;
        this.hobbies = hobbies;
        this.conocimientosInformaticos =  conocimientosInformaticos;
        this.estadoCuenta = estadoCuenta;
        this.anexos = new ArrayList<String>(Arrays.asList(anexos.split(" , ")));
        this.idiomas = new ArrayList<String>(Arrays.asList(idiomas.split(" , ")));
        this.experienciasProfesionales = new ArrayList<String>(Arrays.asList(experienciasProfesionales.split(" , ")));
        this.referenciasEmpleo = new ArrayList<String>(Arrays.asList(referenciasEmpleo.split(" , ")));
        this.formacion = new ArrayList<String>(Arrays.asList(formacion.split(" , ")));
    }

    public UsuarioCorriente() {

    }

    //////////////////////////
    //Getters and Setters
    /////////////////////////


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(String estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public ArrayList<String> getAnexos() {
        return anexos;
    }

    public void setAnexos(ArrayList<String> anexos) {
        this.anexos = anexos;
    }

    public ArrayList<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(ArrayList<String> idiomas) {
        this.idiomas = idiomas;
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