package com.aluracursos.literalura.models;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(unique = true)
    private String nombre;
    private String fechaNacimiento;
    private String fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(String fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l ->l.setAutor(this));
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "\n--------------------AUTOR-----------------" +
                "\nNombre: " + nombre +
                "\nFecha de nacimiento: " + fechaNacimiento +
                "\nFecha de fallecimiento: " + fechaFallecimiento +
                "\nLibros del autor: " + libros.stream().map(Libro::getTitulo).collect(Collectors.toList());
    }
}