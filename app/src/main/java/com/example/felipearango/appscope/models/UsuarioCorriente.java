package com.example.felipearango.appscope.models;

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
    private float rating;
    private int tipoUser;
    private int votos;
    private String anexos;
    private ArrayList<String> idiomas;
    private ArrayList<String> experienciasProfesionales;
    private ArrayList<String> referenciasEmpleo;
    private ArrayList<String> formacion;
    private ArrayList<String> etiquetas;

    //////////////////////////
    //Constructor
    /////////////////////////

    public UsuarioCorriente(String id, String nombre, String apellido, String ocupacion,
                            String fechaNacimiento, String universidad, String celular,
                            String correo, String foto, String frase, String hobbies,
                            String conocimientosInformaticos, String estadoCuenta,
                            float rating, int tipoUser,int votos, String anexos, String idiomas, String
                                    experienciasProfesionales,String referenciasEmpleo, String formacion,String etiquetas) {
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
        this.conocimientosInformaticos = conocimientosInformaticos;
        this.estadoCuenta = estadoCuenta;
        this.rating = rating;
        this.tipoUser = tipoUser;
        this.votos = votos;
        this.anexos = anexos;
        this.idiomas = new ArrayList<String>(Arrays.asList(idiomas.split(",")));
        this.experienciasProfesionales = new ArrayList<String>(Arrays.asList(experienciasProfesionales.split(" , ")));
        this.referenciasEmpleo = new ArrayList<String>(Arrays.asList(referenciasEmpleo.split(" , ")));
        this.formacion = new ArrayList<String>(Arrays.asList(formacion.split(",")));
        this.etiquetas = new ArrayList<String>(Arrays.asList(etiquetas.split(",")));
    }



    public UsuarioCorriente() {

    }

    //////////////////////////
    //Getters and Setters
    /////////////////////////


    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getAnexos() {
        return anexos;
    }

    public void setAnexos(String anexos) {
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

    public ArrayList<String> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ArrayList<String> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public int getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(int tipoUser) {
        this.tipoUser = tipoUser;
    }
}