package com.naval.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "lugares")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Lugar{
    @Id
    private String id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String direccion;
    private Double latitud;
    private Double longitud;
    private  String imagenUrl;

}
