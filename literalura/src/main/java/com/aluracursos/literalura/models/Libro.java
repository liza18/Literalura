package com.aluracursos.literalura.models;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double cantidadDescargas;
    
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibros datosLibros) {
        this.titulo = datosLibros.titulo();
        this.idioma = datosLibros.idioma().get(0);
        this.cantidadDescargas = datosLibros.cantidadDescargas();
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Double cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "\n----------------LIBRO------------------" +
                "\nTitulo: " + titulo+
                "\nAutor: " + autor.getNombre()+
                "\nIdioma: " + idioma+
                "\nDescargas del libro: "+ cantidadDescargas;
    }
}
