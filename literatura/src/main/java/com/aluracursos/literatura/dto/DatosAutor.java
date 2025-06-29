package com.aluracursos.literatura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("birth_year")Integer fechaDeNacimiento,
        @JsonAlias("death_year")Integer fechaDeFallecimiento,
        @JsonAlias("name")String nombre
) {
}
