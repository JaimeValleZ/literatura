package com.aluracursos.literatura.modelo;

import com.aluracursos.literatura.dto.DatosLibro;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne
    private Autor autor;
    private String idioma;
    private Double numeroDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.autor = autor;
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDescargas = datosLibro.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor(Autor autor) {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return  '\n' +"-------LIBRO ------- " + '\n' +
                " Titulo: " + titulo + '\n' +
                " Idioma: " + idioma + '\n' +
                " Autor: " + autor.getNombre() + '\n' +
                " Numero de descargas: " + numeroDescargas + '\n' +
                "-------------------"
                ;
    }
}
